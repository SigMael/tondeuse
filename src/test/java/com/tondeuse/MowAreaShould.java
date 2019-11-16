package com.tondeuse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
	
	@Autowired
	private Runner mowerRunner;
	
	@InjectMocks
	private Mower mockedMower;
	
	@InjectMocks
	private MowerPoint mockedAreaSize;
	
	@Test
	public void return_nothing_to_do_if_mowers_empty() {
		//given
		ArrayList<Mower> mowers = new ArrayList<Mower>();

		//when & then
		assertEquals("Rien a tondre!", mowerRunner.mowArea(mowers, mockedAreaSize));
	}
	
	@Test
	public void return_correct_move_forward_1_0_E_if_mower_has_1_move_A_from_0_0_E() {
		//given
		ArrayList<Mower> mowers = new ArrayList<Mower>();
		
		mockedMower = mock(Mower.class);
		when(mockedMower.getMoves()).thenReturn(new String[] {"A"});
		when(mockedMower.getStartPosition()).thenReturn(new MowerPoint(0, 0, "E"));
		
		mockedAreaSize = mock(MowerPoint.class);
		when(mockedAreaSize.getX()).thenReturn((double) 5);
		when(mockedAreaSize.getY()).thenReturn((double) 5);
		
		mowers.add(mockedMower);
		//when & then
		assertEquals("1 0 E", mowerRunner.mowArea(mowers, mockedAreaSize));
	}
	
	@Test
	public void return_Correct_Rotation_0_0_N_if_mower_has_1_move_G_from_0_0_E() {
		//given
		ArrayList<Mower> mowers = new ArrayList<Mower>();
		
		mockedMower = mock(Mower.class);
		when(mockedMower.getMoves()).thenReturn(new String[] {"G"});
		when(mockedMower.getStartPosition()).thenReturn(new MowerPoint(0, 0, "E"));
		
		mockedAreaSize = mock(MowerPoint.class);
		when(mockedAreaSize.getX()).thenReturn((double) 5);
		when(mockedAreaSize.getY()).thenReturn((double) 5);
		
		mowers.add(mockedMower);
		//when & then
		assertEquals("0 0 N", mowerRunner.mowArea(mowers, mockedAreaSize));
	}
	
	@Test
	public void return_0_0_W_if_mower_has_3_moves_G_G_A_from_0_0_E() {
		//given
		ArrayList<Mower> mowers = new ArrayList<Mower>();
		
		mockedMower = mock(Mower.class);
		when(mockedMower.getMoves()).thenReturn(new String[] {"G", "G", "A"});
		when(mockedMower.getStartPosition()).thenReturn(new MowerPoint(0, 0, "E"));
		
		mockedAreaSize = mock(MowerPoint.class);
		when(mockedAreaSize.getX()).thenReturn((double) 5);
		when(mockedAreaSize.getY()).thenReturn((double) 5);
		
		mowers.add(mockedMower);
		
		//when & then
		assertEquals("0 0 W", mowerRunner.mowArea(mowers, mockedAreaSize));
	}
	
	@Test
	public void return_5_5_E_if_mower_has_1_move_A_from_5_5_E() {
		//given
		ArrayList<Mower> mowers = new ArrayList<Mower>();
		
		mockedMower = mock(Mower.class);
		when(mockedMower.getMoves()).thenReturn(new String[] {"A"});
		when(mockedMower.getStartPosition()).thenReturn(new MowerPoint(5, 5, "E"));
		
		mockedAreaSize = mock(MowerPoint.class);
		when(mockedAreaSize.getX()).thenReturn((double) 5);
		when(mockedAreaSize.getY()).thenReturn((double) 5);
		
		mowers.add(mockedMower);
		
		//when & then
		assertEquals("5 5 E", mowerRunner.mowArea(mowers, mockedAreaSize));
	}
	
	@Test
	public void return_1_3_N_if_mower_has_test_moves() {
		//given
		ArrayList<Mower> mowers = new ArrayList<Mower>();
		
		mockedMower = mock(Mower.class);
		when(mockedMower.getMoves()).thenReturn(new String[] {"G", "A", "G", "A", "G", "A", "G", "A", "A"});
		when(mockedMower.getStartPosition()).thenReturn(new MowerPoint(1, 2, "N"));
		
		mockedAreaSize = mock(MowerPoint.class);
		when(mockedAreaSize.getX()).thenReturn((double) 5);
		when(mockedAreaSize.getY()).thenReturn((double) 5);
		
		mowers.add(mockedMower);
		
		//when & then
		assertEquals("1 3 N", mowerRunner.mowArea(mowers, mockedAreaSize));
	}
    
}