package com.tondeuse;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MowerApplication.class)
public class ParseShould {
	
	private final static String TESTFILE = "src/main/resources/testFile.txt";
	
	@Autowired
	private MowerParser mowerParser;
	
	@Test(expected = IOException.class )
	public void return_exception_when_file_is_not_readable_or_not_found() throws IOException {
		//given
    	File mockFile = new File("this/is/invalid/path/testFile.txt");
		
    	//when & then
    	mockFile.createNewFile();
		mowerParser.parse(mockFile);
	}
	
    @Test
    public void return_null_list_when_file_empty() throws IOException{
        //given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
        //when
        Lawn lawn= mowerParser.parse(mockFile);
        mockFile.delete();
        
		//then
		Assert.assertNull(lawn);
    }
    
    @Test
    public void return_null_when_file_has_less_than_3_lines() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.close();
    	
        //when
        Lawn lawn = mowerParser.parse(mockFile);
        mockFile.delete();
        
		//then
		Assert.assertNull(lawn);
    }
    
    @Test
    public void return_1_mower_when_file_has_1_mower() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.println("1 2 N");
    	writer.println("GAGAGAGAA");
    	writer.close();
    	
        //when
        Lawn lawn= mowerParser.parse(mockFile);
        mockFile.delete();
        
		//then
        Assert.assertEquals(1, lawn.getMowers().size());
    }
    
    @Test
    public void return_2_mower_when_file_has_2_mower() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.println("1 2 N");
    	writer.println("GAGAGAGAA");
    	writer.println("3 3 E");
    	writer.println("AADAADADDA");
    	writer.close();
    	
        //when
        Lawn lawn= mowerParser.parse(mockFile);
        mockFile.delete();
        
		//then
		Assert.assertEquals(2, lawn.getMowers().size());
    }
    
    @Test
    public void return_valid_areaSize_when_area_size_is_correctly_read() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.println("1 2 N");
    	writer.println("GAGAGAGAA");
    	writer.close();
		Point expectedAreaSize = new Point(5, 5);

		//when
		Lawn lawn = mowerParser.parse(mockFile);
		mockFile.delete();


		//then
        Assert.assertEquals( expectedAreaSize.getX(), lawn.getAreaSize().getX(), 0);
        Assert.assertEquals( expectedAreaSize.getY(), lawn.getAreaSize().getY(), 0);
    }
    
    @Test(expected = PatternSyntaxException.class)
    public void return_exception_when_area_size_is_wrong() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("55");
    	writer.println("1 2 N");
    	writer.println("GAGAGAGAA");
    	writer.close();
    	
        //when
        mowerParser.parse(mockFile);
        
        mockFile.delete();
    }

    @Test
    public void return_correct_mower_start_position_when_start_position_is_correctly_read() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();

		int x = 1;
		int y = 2;
		String orientation = "N";

    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.println(String.format("%d %d %s",x,y,orientation));
    	writer.println("GAGAGAGAA");
    	writer.close();
		MowerPoint expectedPosition = new MowerPoint(x, y, orientation);

        //when
        Lawn lawn = mowerParser.parse(mockFile);
        mockFile.delete();

		//then
		Assert.assertEquals( expectedPosition.getX(), lawn.getMowers().get(0).getPosition().getX(), 0);
		Assert.assertEquals( expectedPosition.getY(), lawn.getMowers().get(0).getPosition().getY(), 0);
		Assert.assertEquals( expectedPosition.getOrientation(), lawn.getMowers().get(0).getPosition().getOrientation());
    }
    
    @Test
    public void return_correct_mower_moves_when_moves_are_correctly_read() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();

    	String moves = "GAGAGAGAA";
        String expectedMoves[] = moves.split("");
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.println("1 2 N");
    	writer.println(moves);
    	writer.close();

        //when
        Lawn lawn= mowerParser.parse(mockFile);
        mockFile.delete();

		//then
        for (int i = 0; i < expectedMoves.length; i++) {
        	Assert.assertTrue(expectedMoves[i].equals(lawn.getMowers().get(0).getMoves()[i]));
		}
    }
    
    @Test
    public void return_1_mower_when_1_mower_out_of_2_has_invalid_start_position() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
		String invalidStartPosition = "12 N";

    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
		writer.println(invalidStartPosition);
    	writer.println("GAGAGAGAA");
		writer.println("3 3 E");
		writer.println("AAAAAAAA");
    	writer.close();

        //when
        Lawn lawn= mowerParser.parse(mockFile);
        mockFile.delete();
        
		//then
        Assert.assertEquals(1, lawn.getMowers().size());
    }
    
    @Test
    public void return_1_mower_when_1_mower_out_of_2_has_invalid_moves() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
		String invalidMoves = "GXAGAGAGAA";

    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.println("1 2 N");
		writer.println(invalidMoves);
    	writer.println("3 3 E");
    	writer.println("AADAADADDA");
    	writer.close();
    	
        //when
        Lawn lawn= mowerParser.parse(mockFile);
        mockFile.delete();
        
		//then
        Assert.assertEquals(1, lawn.getMowers().size());
    }
    
    @Test
    public void return_2_mowers_when_1_mower_out_of_3_has_out_of_range_start_position() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
		String outOfRangeStartPosition = "6 5 N";

    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
		writer.println(outOfRangeStartPosition);
    	writer.println("GAGAGAGAA");
    	writer.println("3 3 E");
    	writer.println("AADAADADDA");
    	writer.println("3 3 E");
    	writer.println("AADAADADDA");
    	writer.close();
    	
        //when
        Lawn lawn= mowerParser.parse(mockFile);
        mockFile.delete();
        
		//then
        Assert.assertEquals(2, lawn.getMowers().size());
    }
    
}