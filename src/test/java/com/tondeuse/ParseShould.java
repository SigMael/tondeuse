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
	public void return_exception_when_file_isnt_readable_or_not_found() throws IOException {
		//given
    	File mockFile = new File("this/is/invalid/path/testFile.txt");
		
    	//when & then
    	mockFile.createNewFile();    	
		mowerParser = new MowerParser();
		mowerParser.parse(mockFile);
		
	}
	
    @Test
    public void return_empty_list_when_file_empty() throws IOException{
        //given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
        //when
        MowerParser mowerParser = new MowerParser();
        List<Mower> mowers = mowerParser.parse(mockFile);

        mockFile.delete();
        
		//then
        Assert.assertEquals(true, mowers.isEmpty());
    }
    
    @Test
    public void return_empty_when_file_has_less_than_3_line() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.close();
    	
        //when
        MowerParser mowerParser = new MowerParser();
        List<Mower> mowers = mowerParser.parse(mockFile);
        
        mockFile.delete();
        
		//then
		Assert.assertEquals(true, mowers.isEmpty());
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
        MowerParser mowerParser = new MowerParser();
        List<Mower> mowers = mowerParser.parse(mockFile);
        
        mockFile.delete();
        
		//then
        Assert.assertEquals(1, mowers.size());
    }
    
    @Test
    public void return_2_mowers_when_file_has_2_mower() throws IOException {
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
        MowerParser mowerParser = new MowerParser();
        List<Mower> mowers = mowerParser.parse(mockFile);
        
        mockFile.delete();
        
		//then
		Assert.assertEquals(2, mowers.size());
    }
    
    @Test
    public void check_areaSize_is_correctly_rode() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.println("1 2 N");
    	writer.println("GAGAGAGAA");
    	writer.close();
    	
        //when
        MowerParser mowerParser = new MowerParser();

        mowerParser.parse(mockFile);
        
        mockFile.delete();
        
        Point mockedAreaSize = new Point(5, 5);
        
		//then
        Assert.assertEquals( mockedAreaSize.getX(), mowerParser.getAreaSize().getX(), 0);
        Assert.assertEquals( mockedAreaSize.getY(), mowerParser.getAreaSize().getY(), 0);
    }
    
    @Test(expected = PatternSyntaxException.class)
    public void check_areaSize_is_wrong() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("55");
    	writer.println("1 2 N");
    	writer.println("GAGAGAGAA");
    	writer.close();
    	
        //when
        MowerParser mowerParser = new MowerParser();
        mowerParser.parse(mockFile);
        
        mockFile.delete();
    }


    @Test
    public void check_mower_startPosition_is_correctly_rode() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.println("1 2 N");
    	writer.println("GAGAGAGAA");
    	writer.close();
    	
        //when
        MowerParser mowerParser = new MowerParser();
        List<Mower> mowers = mowerParser.parse(mockFile);
        
        mockFile.delete();
        
        MowerPoint mockedStartPosition = new MowerPoint(1, 2, "N");
        
		//then
		Assert.assertEquals( mockedStartPosition.getX(), mowers.get(0).getStartPosition().getX(), 0);
		Assert.assertEquals( mockedStartPosition.getY(), mowers.get(0).getStartPosition().getY(), 0);
		Assert.assertEquals( mockedStartPosition.getOrientation(), mowers.get(0).getStartPosition().getOrientation());
    }
    
    @Test
    public void check_mower_moves_is_correctly_rode() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.println("1 2 N");
    	writer.println("GAGAGAGAA");
    	writer.close();
    	
        //when
        MowerParser mowerParser = new MowerParser();
        List<Mower> mowers = mowerParser.parse(mockFile);
        
        mockFile.delete();
        
        String mockedMoves[] = {"G", "A", "G", "A", "G", "A", "G", "A", "A"};
        
		//then
        for (int i = 0; i < mockedMoves.length; i++) {			
        	Assert.assertTrue(mockedMoves[i].equals(mowers.get(0).getMoves()[i]));
		}
    }
    
    @Test
    public void return_1_mowers_when_1_mower_outof_2_has_invalid_startposition() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.println("12 N");
    	writer.println("GAGAGAGAA");
    	writer.println("3 3 E");
    	writer.println("AADAADADDA");
    	writer.close();
    	
        //when
        MowerParser mowerParser = new MowerParser();
        List<Mower> mowers = mowerParser.parse(mockFile);
        
        Point mockedStartPosition = new Point(3, 3);
        String mockedMoves[] = {"A", "A", "D", "A", "A", "D", "A", "D", "D", "A"};
        
        mockFile.delete();
        
		//then
        Assert.assertEquals( mockedStartPosition.getX(), mowers.get(0).getStartPosition().getX(), 0);
		Assert.assertEquals( mockedStartPosition.getY(), mowers.get(0).getStartPosition().getY(), 0);
        for (int i = 0; i < mockedMoves.length; i++) {			
        	Assert.assertTrue(mockedMoves[i].equals(mowers.get(0).getMoves()[i]));
		}
    }
    
    @Test
    public void return_1_mowers_when_1_mower_out_of_2_has_invalid_moves() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.println("1 2 N");
    	writer.println("G1AGAGAGAA");
    	writer.println("3 3 E");
    	writer.println("AADAADADDA");
    	writer.close();
    	
        //when
        MowerParser mowerParser = new MowerParser();
        List<Mower> mowers = mowerParser.parse(mockFile);
        
        Point mockedStartPosition = new Point(3, 3);
        String mockedMoves[] = {"A", "A", "D", "A", "A", "D", "A", "D", "D", "A"};
        
        mockFile.delete();
        
		//then
        Assert.assertEquals( mockedStartPosition.getX(), mowers.get(0).getStartPosition().getX(), 0);
		Assert.assertEquals( mockedStartPosition.getY(), mowers.get(0).getStartPosition().getY(), 0);
        for (int i = 0; i < mockedMoves.length; i++) {			
        	Assert.assertTrue(mockedMoves[i].equals(mowers.get(0).getMoves()[i]));
		}
    }
    
    @Test
    public void return_2_mowers_when_1_mower_out_of_3_has_out_of_range_StartingPosition() throws IOException {
    	//given
    	File mockFile = new File(TESTFILE);
    	mockFile.createNewFile();
    	
    	PrintWriter writer = new PrintWriter(mockFile, "UTF-8");
    	writer.println("5 5");
    	writer.println("6 5 N");
    	writer.println("GAGAGAGAA");
    	writer.println("3 3 E");
    	writer.println("AADAADADDA");
    	writer.println("3 3 E");
    	writer.println("AADAADADDA");
    	writer.close();
    	
        //when
        MowerParser mowerParser = new MowerParser();
        List<Mower> mowers = mowerParser.parse(mockFile);
            
        mockFile.delete();
        
		//then
        Assert.assertEquals(2, mowers.size());
    }
    
}