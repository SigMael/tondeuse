package com.tondeuse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Entity which execute the mow of a Lawn using mowers.
 * The main public method is mow which return every final mowers positions using String format)
 *
 * @author Mael Sigaroudi
 *
 */

public class Lawn {

	/**
	 * Class logger field
	 */
	private static final Logger LOG = LogManager.getLogger(Lawn.class);
	/**
	 * The mowers list of the applications which will mow the Lawn
	 */
	private ArrayList<Mower> mowers;

	/**
	 * The size of the Lawn area
	 */
    private Point areaSize ;

	/**
	 * List of possible orientation which can take a mower
	 */
	private final String orientations[] = new String[] { "N", "E", "S", "W" };

	/**
	 * Constructor which take only a areaSize parameter and initalize mowers with en empty Array
	 *
	 * @param areaSize : the area size to initialize
	 */
    public Lawn(Point areaSize){
		this.areaSize = areaSize;
		this.mowers = new ArrayList<>();
	}

	/**
	 * Constructor which take both areaSize & mowers list parameters to initalize them.
	 *
	 * @param areaSize : the area size to initialize
	 * @param mowers : the mowers list to initialize
	 */
	public Lawn (Point areaSize, ArrayList<Mower> mowers) {
		this(areaSize);
		this.mowers = mowers;
	}

	/**
	 * Main public method of Lawn class. It moves the mowers on the lawn and return their final positions.
	 *
	 * @return String : the final mowers positions separated by line break.
	 */
	public String mow() {
		String outputString = "";
        boolean isNewLine = false;

		if(mowers.size() == 0) {
			return "Nothing to mow!";
		}

		for (Mower mower : mowers) {
			LOG.info("Starting to mow the Lawn!");
		    MowerPoint currentPosition = mower.getPosition();
			int x = (int) currentPosition.getX();
			int y = (int) currentPosition.getY();
			LOG.debug("Lawn.mow : current mower position=[{},{}]", x, y);
			String orientation = currentPosition.getOrientation();
            MowerPoint newPosition;
			for (String move : mower.getMoves()) {
				switch (move) {
				case "A":
            		LOG.debug("Lawn.mow : move={} Moving mower forward", move);
                    newPosition = getNextMoveForwardPosition(x, y, orientation);
                    x = (int) newPosition.getX();
                    y = (int) newPosition.getY();
					LOG.debug("Lawn.mow : new mower position=[{},{}]", x, y);
                    break;
				case "D":
					LOG.debug("Lawn.mow : move={} rotating mower right", move);
					orientation = getNextOrientationRight(orientation);
					LOG.debug("Lawn.mow : new mower orientation={}", orientation);
					break;
				case "G":
					LOG.debug("Lawn.mow : move={} rotating mower left", move);
					orientation = getNextOrientationLeft(orientation);
					LOG.debug("Lawn.mow : new mower orientation={}", orientation);
                    break;
				default:
					LOG.error("Lawn.mow : move={} case unknown", move);
					break;
				}
			}
            outputString = getOutput(outputString, isNewLine, x, y, orientation);
			isNewLine = true;
		}
		LOG.info("The Lawn has been mowed ! Final mower(s) position(s) Output:\n"+outputString);
		return outputString;
	}

	/**
	 * Method which construct the final String mowers positions
	 * @param result : the final String
	 * @param isNextLine : boolean which describe if it is necessary to add a line break or not
	 * @param x : x mower position
	 * @param y : y mower position
	 * @param orientation : mower orientation
	 * @return the final String mowers positions
	 */
    private String getOutput(String result, boolean isNextLine, int x, int y, String orientation) {
        if(isNextLine){
            result += "\n";
            result += x + " " + y + " " + orientation;
        }
        else{
            result += x + " " + y + " " + orientation;
        }
        return result;
    }

	/**
	 * Method which change the x or y value in order to make a mower move
	 * @param x : x current mower position
	 * @param y : y current mower position
	 * @param orientation : current mower orientation
	 * @return a new MowerPoint entity wich describe the new mower position
	 */
    private MowerPoint getNextMoveForwardPosition(int x, int y, String orientation) {
        switch (orientation) {
            case "N":
                if (y + 1 <= areaSize.getY()) {
                    y++;
                }
                break;
            case "E":
                if (x + 1 <= areaSize.getX()) {
                    x++;
                }
                break;
            case "W":
                if (x - 1 >= 0) {
                    x--;
                }
                break;
            case "S":
                if (y - 1 >= 0) {
                    y--;
                }
                break;
			default:
                break;
        }
        return new MowerPoint(x, y, orientation);
    }

	/**
	 * Method which change a mower orientation to the right
	 *
	 * @param currentOrientation : the current mower orientation
	 * @return The next mower orientation after rotating right
	 */
	private String getNextOrientationRight(String currentOrientation) {
		int currentIndexOrientation = Arrays.asList(orientations).indexOf(currentOrientation);
		int nextIndex = currentIndexOrientation + 1;
		if (nextIndex >= orientations.length) {
			return orientations[0];
		} else
			return orientations[nextIndex];
	}

	/**
	 * Method which change a mower orientation to the left
	 *
	 * @param currentOrientation : the current mower orientation
	 * @return The next mower orientation after rotating left
	 */
	private String getNextOrientationLeft(String currentOrientation) {
		int currentIndexOrientation = Arrays.asList(orientations).indexOf(currentOrientation);
		int nextIndex = currentIndexOrientation - 1;
		if (nextIndex < 0) {
			return orientations[orientations.length - 1];
		} else
			return orientations[nextIndex];
	}

	/**
	 * Getter method for mowers list field
	 * @return list of mowers
	 */
	public ArrayList<Mower> getMowers() {
		return this.mowers;
	}

	/**
	 * Getter method for areaSize Point field
	 * @return Point which describe the Lawn area sizes
	 */
	public Point getAreaSize() {
		return areaSize;
	}
}
