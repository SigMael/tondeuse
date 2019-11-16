package com.tondeuse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.*;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MowerApplication.class)
public class MowAreaShould {
	private Lawn lawn;
	
	@InjectMocks
	private Mower mockedMower;
	
	@InjectMocks
	private MowerPoint mockedAreaSize;
	
	@Test
	public void return_nothing_to_do_if_mowers_empty() {
		//given
		lawn = new Lawn(new MowerPoint());

		//when & then
		assertEquals("Rien a tondre!", lawn.mow());
	}
	
	@Test
	public void return_correct_final_position_1_0_E_if_mower_has_1_move_forward_from_0_0_E() {
		//given
		mockedMower = mock(Mower.class);
		when(mockedMower.getMoves()).thenReturn(new String[] {"A"});
		when(mockedMower.getPosition()).thenReturn(new MowerPoint(0, 0, "E"));

		mockedAreaSize = mock(MowerPoint.class);
		when(mockedAreaSize.getX()).thenReturn((double) 5);
		when(mockedAreaSize.getY()).thenReturn((double) 5);

		ArrayList mowers = new ArrayList<Mower>();
		mowers.add(mockedMower);
		lawn = new Lawn( new Point(10,10), mowers);

		//when
		String finalPositionMover = lawn.mow();

		//then
		assertEquals("1 0 E", finalPositionMover);
	}
	
	@Test
	public void return_Correct_Rotation_0_0_N_if_mower_has_1_move_G_from_0_0_E() {
		//given
		mockedMower = mock(Mower.class);
		when(mockedMower.getMoves()).thenReturn(new String[] {"G"});
		when(mockedMower.getPosition()).thenReturn(new MowerPoint(0, 0, "E"));

		mockedAreaSize = mock(MowerPoint.class);
		when(mockedAreaSize.getX()).thenReturn((double) 5);
		when(mockedAreaSize.getY()).thenReturn((double) 5);

		ArrayList mowers = new ArrayList<Mower>();
		mowers.add(mockedMower);
		lawn = new Lawn( new Point(10,10), mowers);

		//when
		String finalPositionMover = lawn.mow();

		// then
		assertEquals("0 0 N", finalPositionMover);
	}

	@Test
	public void return_0_0_W_if_mower_has_3_moves_G_G_A_from_0_0_E() {
		//given
		mockedMower = mock(Mower.class);
		when(mockedMower.getMoves()).thenReturn(new String[] {"G", "G", "A"});
		when(mockedMower.getPosition()).thenReturn(new MowerPoint(0, 0, "E"));

		mockedAreaSize = mock(MowerPoint.class);
		when(mockedAreaSize.getX()).thenReturn((double) 5);
		when(mockedAreaSize.getY()).thenReturn((double) 5);

		ArrayList mowers = new ArrayList<Mower>();
		mowers.add(mockedMower);
		lawn = new Lawn( new Point(10,10), mowers);

		//when
		String finalPositionMover = lawn.mow();

		// then
		assertEquals("0 0 W", finalPositionMover);
	}

	@Test
	public void return_6_5_E_if_mower_has_1_move_A_from_5_5_E() {
		//given
		mockedMower = mock(Mower.class);
		when(mockedMower.getMoves()).thenReturn(new String[] {"A"});
		when(mockedMower.getPosition()).thenReturn(new MowerPoint(5, 5, "E"));

		mockedAreaSize = mock(MowerPoint.class);
		when(mockedAreaSize.getX()).thenReturn((double) 5);
		when(mockedAreaSize.getY()).thenReturn((double) 5);

		ArrayList mowers = new ArrayList<Mower>();
		mowers.add(mockedMower);
		lawn = new Lawn( new Point(10,10), mowers);

		//when
		Object finalPositionMover = lawn.mow();

		// then
		assertEquals("6 5 E", finalPositionMover);
	}

	@Test
	public void return_1_3_N_if_mower_has_test_moves() {
		//given
		mockedMower = mock(Mower.class);
		when(mockedMower.getMoves()).thenReturn(new String[] {"G", "A", "G", "A", "G", "A", "G", "A", "A"});
		when(mockedMower.getPosition()).thenReturn(new MowerPoint(1, 2, "N"));

		mockedAreaSize = mock(MowerPoint.class);
		when(mockedAreaSize.getX()).thenReturn((double) 5);
		when(mockedAreaSize.getY()).thenReturn((double) 5);

		ArrayList mowers = new ArrayList<Mower>();
		mowers.add(mockedMower);
		lawn = new Lawn( new Point(10,10), mowers);

		//when
		Object finalPositionMover = lawn.mow();

		// then
		assertEquals("1 3 N", finalPositionMover);
	}

	@Test
	public void return_2_mowers_good_final_positions() {
		//given
		mockedMower = mock(Mower.class);
		when(mockedMower.getMoves()).thenReturn(new String[] {"G", "A", "G", "A", "G", "A", "G", "A", "A"}).thenReturn(new String[] {"A", "A", "D", "A", "A", "D", "A", "D", "D", "A"});
		when(mockedMower.getPosition()).thenReturn(new MowerPoint(1, 2, "N")).thenReturn(new MowerPoint(3, 3, "E"));

		mockedAreaSize = mock(MowerPoint.class);
		when(mockedAreaSize.getX()).thenReturn((double) 5);
		when(mockedAreaSize.getY()).thenReturn((double) 5);

		ArrayList mowers = new ArrayList<Mower>();
		mowers.add(mockedMower);
		mowers.add(mockedMower);
		lawn = new Lawn( new Point(5,5), mowers);

		//when
		Object finalPositionMover = lawn.mow();

		// then
		assertEquals("1 3 N\n5 1 E", finalPositionMover);
	}
    
}