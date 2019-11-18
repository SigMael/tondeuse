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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class read an input file in order to initialize data :
 *      - The Lawn's size
 *      - Mowers which will mow the Lawn
 *
 * The main method is parse which return a Lawn entity fully initialized.
 *
 * @author Mael Sigaroudi
 *
 */
@Component
public class MowerParser {

    /**
     * Class logger field
     */
    private static final Logger LOG = LogManager.getLogger(MowerParser.class);

    /**
     * Number of lines which describe a complete mower inside the input file
     */
    @Value("${file.number.lineby.mower}")
    private final int DATA_LINES_BY_MOWER = 2;

    /**
     * Index of the area size inside the input file
     */
    @Value("${file.areasize.index}")
    private final int INDEX_AREA_SIZE = 0;

    /**
     * Strict number of minimum lines necessary in order to load a functional Lawn
     */
    private final int MINIMUM_ROWS = DATA_LINES_BY_MOWER + 1;

    /**
     * Pattern describing separator between coordinate inside input file
     */
    @Value("${file.input.separator}")
    private String PATTERN_COORDINATE_SEPARATOR;

    /**
     * Pattern describing separator between moves inside input file
     */
    @Value("${file.moves.separator}")
    private String PATTERN_MOVES_SEPARATOR;

    /**
     * Pattern describing the area size line format inside input file
     */
    @Value("${file.areasize.pattern}")
    private String PATTERN_AREA_SIZE;

    /**
     * Pattern describing the mowers start position lines format inside input file
     */
    @Value("${file.startPosition.pattern}")
    private String PATTERN_START_POSITION;

    /**
     * Pattern describing the mowers moves lines format inside input file
     */
    @Value("${file.moves.pattern}")
    private String PATTERN_MOVES;

    /**
     * Main public method of MowerParser class which read the input file & initialize a functional Lawn
     *
     * @param inputFile : the file which contain the data to be read
     * @return a Lawn initialized using data read from input file or null if data could not be read
     * @throws IOException : If the file cannot be read
     * @throws PatternSyntaxException : If the a line pattern is not valid in the input file
     */
    public Lawn parse(File inputFile) throws IOException, PatternSyntaxException {
        LOG.info("Parse process start");
        if (inputFile.exists()) {
            String areaReadLine = "";
            try {
                ArrayList<String> fileLines = getLines(inputFile);
                if (fileLines.size() >= MINIMUM_ROWS) {
                    LOG.info("Starting to parse lines read");
                    areaReadLine = fileLines.remove(INDEX_AREA_SIZE).trim();
                    Point areaSize = readSizeArea(areaReadLine);
                    LOG.debug("MowerParser.parse: areaSize read=[{},{}]", areaSize.getX(), areaSize.getY());
                    ArrayList<Mower> mowers = getMowers(fileLines, areaSize);
                    LOG.info("Lines parsing completed");
                    return new Lawn(areaSize, mowers);
                }
                else{
                    LOG.error("Incomplet file : the program will now stop");
                }
            } catch (IOException e) {
                LOG.error("Error reading file {}", inputFile.getPath());
                throw e;
            } catch (PatternSyntaxException e) {
                LOG.error("Error parsing file on area size's line: Incorrect format");
                throw new PatternSyntaxException(PATTERN_AREA_SIZE, areaReadLine, -1);
            }
        }
        return null;
    }


    /**
     * Method which read the input file and return a list of each lines read from the file
     * @param inputFile : the input file object initialized using it's path
     * @return List of each line read from the input file
     * @throws IOException : If the file cannot be read
     */
    private ArrayList<String> getLines(File inputFile) throws IOException {
        LOG.info("Starting to read file {}", inputFile.getPath());
        ArrayList<String> fileLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            while (br.ready()) {
                fileLines.add(br.readLine());
                LOG.debug("MowerParser.getLines: line currently read={}", fileLines.get(fileLines.size()-1));
            }
        } catch (IOException e) {
            LOG.error("MowerParser.getLines: error while reading file");
            e.printStackTrace();
            throw e;
        }
        LOG.info("Read file complete");
        return fileLines;
    }

    /**
     * Method which return a list of mowers fully initialized using lines read from the input file
     * @param fileLines : the list of lines read from the input file
     * @param areaSize : The size of the Lawn area already read from the input file
     * @return List of mowers fully initialized or an empty list if none of the lines has correct format
     */
    private ArrayList<Mower> getMowers(ArrayList<String> fileLines, Point areaSize) {
        ArrayList<Mower> mowers = new ArrayList<>();
        for (int i = 0; i < fileLines.size() - (DATA_LINES_BY_MOWER - 1); i += DATA_LINES_BY_MOWER) {
            String lineStartPosition = fileLines.get(i).trim();
            String lineMoves = fileLines.get(i + 1).trim();
            LOG.debug("MowerParser.getMowers : lineStartPosition={}", lineStartPosition);
            LOG.debug("MowerParser.getMowers : lineMoves={}", lineMoves);
            if(lineStartPosition.length() > 0 && lineMoves.length() > 0) {
                Mower mower = buildMowerFromLines(lineStartPosition, lineMoves);
                if (mower != null && isStartingPositionInsideArea(mower.getPosition(), areaSize)) {
                    mowers.add(mower);
                }
            }
            else{
                LOG.info("This mower will be ignored because it's data are invalid:[{},{}]", lineStartPosition, lineMoves);
            }
        }
        return mowers;
    }

    /**
     * Method which build a mower and return it using the Start position line & moves line of this mower
     *
     * @param lineStartPosition : the line read from the file which describe the starting position of the mower
     * @param lineMoves : the line read from the file which describe all the moves of the mower
     * @return A mower fully initialize using start position line & moves line OR null if one of them has not a valid format
     */
    private Mower buildMowerFromLines(String lineStartPosition, String lineMoves) {
        MowerPoint startPosition = readStartingPosition(lineStartPosition);
        if(startPosition == null){
            LOG.info("This mower will be ignored because it's starting position is invalid:[{},{}]", lineStartPosition, lineMoves);
            return null;
        }
        String[] moves = readMoves(lineMoves);
        if(moves == null){
            LOG.info("This mower will be ignored because it's movers are invalid :[{},{}]", lineStartPosition, lineMoves);
            return null;
        }
        return new Mower(startPosition, moves);
    }

    /**
     * Method which return a Point initialized using the size area line. The line format is verified and must match
     * area size line pattern.
     * @param line : Area size line read from input file.
     * @return A point initialized with the area size.
     * @throws PatternSyntaxException : if the line does not match the pattern of a size area Line.
     */
    private Point readSizeArea(String line) throws PatternSyntaxException {
        int x, y;
        if (isLinePatternValid(line, PATTERN_AREA_SIZE)) {
            x = Integer.parseInt(line.split(PATTERN_COORDINATE_SEPARATOR)[0]);
            y = Integer.parseInt(line.split(PATTERN_COORDINATE_SEPARATOR)[1]);
        } else {
            throw new PatternSyntaxException(PATTERN_AREA_SIZE, line, -1);
        }
        return new Point(x, y);
    }

    /**
     * Method which return a MowerPoint initialized using the start position line. The line format is verified and must match
     * start position line pattern.
     * @param line : start position line read from input file.
     * @return A mower point initialized with the position of a mower OR null if the line does not match the start position line pattern.
     */
    private MowerPoint readStartingPosition(String line) {
        int x, y;
        String orientation;
        if (isLinePatternValid(line, PATTERN_START_POSITION)) {
            x = Integer.parseInt(line.split(PATTERN_COORDINATE_SEPARATOR)[0]);
            y = Integer.parseInt(line.split(PATTERN_COORDINATE_SEPARATOR)[1]);
            orientation = line.split(PATTERN_COORDINATE_SEPARATOR)[2];
        } else {
            return null;
        }
        return new MowerPoint(x, y, orientation);
    }

    /**
     * Method which return a list of moves initialized using the moves line. The line format is verified and must match
     * the moves line pattern.
     * @param line : moves line read from input file.
     * @return A String array of moves initialized with moves of a mower OR null if the line does not match the moves line pattern.
     */
    private String[] readMoves(String line) {
        if (!isLinePatternValid(line, PATTERN_MOVES)) {
            return null;
        }
        return line.split(PATTERN_MOVES_SEPARATOR);
    }

    /**
     * Method which validate if a line match a given pattern.
     * @param line : the line which needs it's pattern to be validate.
     * @param PATTERN_LINE : the pattern to validate the line.
     * @return true if the line match the pattern - False if the line does not match the pattern.
     */
    private boolean isLinePatternValid(String line, String PATTERN_LINE) {
        return Pattern.matches(PATTERN_LINE, line);
    }

    /**
     * Method which validate if the mower position initialized from the file is inside the area size or not.
     * @param mowerPosition : The mower position to validate.
     * @param areaSize : The are size to validate mower position.
     * @return True if the mower position is inside the area - False if the mower position is not inside the area.
     */
    private boolean isStartingPositionInsideArea(Point mowerPosition, Point areaSize) {
        if ((mowerPosition.getX() > areaSize.getX() || mowerPosition.getY() > areaSize.getY())) {
            LOG.info("Mower [{}] [{}] will not be conserved because it's starting position does not fit Area's size", mowerPosition.getX(), mowerPosition.getY());
            return false;
        }
        return true;
    }

}