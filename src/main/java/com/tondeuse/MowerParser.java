package com.tondeuse;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MowerParser {

    private static final Logger LOG = LogManager.getLogger(MowerParser.class);
    public static final int INDEX_AREA_SIZE = 0;

    public final int MINIMUM_ROWS = 3;

    //	@Value("${file.input.separator}")
    private final String patternCoordinateSeparator = "\\u0020";

    private final String patternMovesSeparator = "";

    //	@Value("${pattern.area}")
    private final String PATTERN_AREA_SIZE = "\\d+ \\d+";

    //	@Value("${pattern.startPosition}")
    private final String patternStartPosition = "\\d+ \\d+ [NEWS]{1}";

    private final String patternMoves = "[GDA]*";

    public Lawn parse(File inputFile) throws IOException, PatternSyntaxException {
        if (inputFile.exists()) {
            String areaReadLine = "";
            try {
                ArrayList<String> fileLines = getLines(inputFile);
                if (fileLines.size() >= MINIMUM_ROWS) {
                    areaReadLine = fileLines.remove(INDEX_AREA_SIZE);
                    Point areaSize = readSizeArea(areaReadLine);
                    ArrayList<Mower> mowers = getMowers(fileLines, areaSize);
                    return new Lawn(areaSize, mowers);
                }
            } catch (IOException e) {
                LOG.error("Erreur lors de la lecture du fichier");
                throw e;
            } catch (PatternSyntaxException e) {
                throw new PatternSyntaxException(PATTERN_AREA_SIZE, areaReadLine, -1);
            }
        }
        return null;
    }

    private ArrayList<String> getLines(File mockFile) throws IOException {
        ArrayList<String> fileLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(mockFile))) {
            while (br.ready()) {
                fileLines.add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return fileLines;
    }

    private ArrayList<Mower> getMowers(ArrayList<String> fileLines, Point areaSize) {
        ArrayList<Mower> mowers = new ArrayList<Mower>();
        for (int i = 0; i < fileLines.size(); i += 2) {
            String lineStartPosition = fileLines.get(i);
            String lineMoves = fileLines.get(i + 1);
            Mower mower = buildMowerFromLines(lineStartPosition, lineMoves);
            if (mower != null && isStartingPositionInsideArea(mower.getPosition(), areaSize)) {
                mowers.add(mower);
            }
        }
        return mowers;
    }

    private Mower buildMowerFromLines(String lineStartPosition, String lineMoves) {
        MowerPoint startPosition = readStartingPosition(lineStartPosition);
        if(startPosition == null){
            return  null;
        }
        String[] moves = readMoves(lineMoves);
        if(moves == null){
            return null;
        }
        return new Mower(startPosition, moves);
    }

    private Point readSizeArea(String line) throws PatternSyntaxException {
        int x, y;
        if (Pattern.matches(PATTERN_AREA_SIZE, line)) {
            x = Integer.parseInt(line.split(patternCoordinateSeparator)[0]);
            y = Integer.parseInt(line.split(patternCoordinateSeparator)[1]);
        } else {
            LOG.error("Erreur lors du parsing de la ligne 1 : le format de la zone est incorrect");
            throw new PatternSyntaxException(PATTERN_AREA_SIZE, line, -1);
        }
        return new Point(x, y);
    }

    private MowerPoint readStartingPosition(String line) {
        int x, y;
        String orientation;
        if (Pattern.matches(patternStartPosition, line)) {
            x = Integer.parseInt(line.split(patternCoordinateSeparator)[0]);
            y = Integer.parseInt(line.split(patternCoordinateSeparator)[1]);
            orientation = line.split(patternCoordinateSeparator)[2];
        } else {
            return null;
        }

        return new MowerPoint(x, y, orientation);
    }

    private boolean isStartingPositionInsideArea(Point mowerPosition, Point areaSize) {
        if ((mowerPosition.getX() > areaSize.getX() || mowerPosition.getY() > areaSize.getY())) {
            LOG.info("La tondeuse [{}] [{}] ne sera pas utilisee car sa position ne rentre pas dans la zone de travail", mowerPosition.getX(), mowerPosition.getY());
            return false;
        }
        return true;
    }

    private String[] readMoves(String line) {
        if (Pattern.matches(patternMoves, line)) {
            return line.split(patternMovesSeparator);
        }
        return null;
    }

}