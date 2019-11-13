package com.tondeuse;

import java.io.BufferedReader;
import java.io.File;gitgit chec
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MowerParser {
    public List<Mower> parse(File mockFile) {
        ArrayList<String> fileLines = getLines(mockFile);

        if(fileLines.size() == 0)
            return new ArrayList<Mower>();
        return null;
    }

    private ArrayList<String> getLines(File mockFile) {
        ArrayList<String> fileLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(mockFile))) {
            while (br.ready()) {
                fileLines.add(br.readLine());
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return fileLines;
    }
}
