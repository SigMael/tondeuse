package com.tondeuse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class Lawn {

	private ArrayList<Mower> mowers;

    public Point getAreaSize() {
        return areaSize;
    }

    private Point areaSize ;

    public Lawn() {}

    public Lawn(Point areaSize){
		this.areaSize = areaSize;
		this.mowers = new ArrayList<>();
	}

	public Lawn (Point areaSize, ArrayList<Mower> mowers) {
		this(areaSize);
		this.mowers = mowers;
	}

	private final String orientations[] = new String[] { "N", "E", "S", "W" };

	public String mow() {
		String result = "";
        boolean isNextLine = false;

		if(mowers.size() == 0) {
			return "Rien a tondre!";
		}

		for (Mower mower : mowers) {
		    MowerPoint currentPosition = mower.getPosition();
			int x = (int) currentPosition.getX();
			int y = (int) currentPosition.getY();
			String orientation = currentPosition.getOrientation();
            MowerPoint newPosition;
			for (String move : mower.getMoves()) {
				switch (move) {
				case "A":
                    newPosition = getNextMoveForwardPosition(x, y, orientation);
                    x = (int) newPosition.getX();
                    y = (int) newPosition.getY();
                    break;
				case "D":
					orientation = getNextOrientationRight(orientation);
					break;
				case "G":
					orientation = getNextOrientationLeft(orientation);
                    break;
				default:
					break;
				}
			}
            result = getOutput(result, isNextLine, x, y, orientation);
			isNextLine = true;
		}
		return result;
	}

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

    private String getNextOrientationRight(String currentOrientation) {
		int currentIndexOrientation = Arrays.asList(orientations).indexOf(currentOrientation);
		int nextIndex = currentIndexOrientation + 1;
		if (nextIndex >= orientations.length) {
			return orientations[0];
		} else
			return orientations[nextIndex];
	}

	private String getNextOrientationLeft(String currentOrientation) {
		int currentIndexOrientation = Arrays.asList(orientations).indexOf(currentOrientation);
		int nextIndex = currentIndexOrientation - 1;
		if (nextIndex < 0) {
			return orientations[orientations.length - 1];
		} else
			return orientations[nextIndex];
	}

	public ArrayList<Mower> getMowers() {
		return this.mowers;
	}
}
