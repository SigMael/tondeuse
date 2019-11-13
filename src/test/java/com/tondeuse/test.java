package com.tondeuse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TondeuseApplication.class)
public class test {

    @Test
    public void return_empty_list_when_file_empty(){
        //given
        File mockFile = new File("");

        //when
        MowerParser mowerParser = new MowerParser();
        List<Mower> fileLines = mowerParser.parse(mockFile);

        //then
        Assert.assertTrue(fileLines.isEmpty());
    }

}
