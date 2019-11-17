package com.tondeuse;

import org.springframework.stereotype.Component;

import java.awt.Point;

/**
 * This class describe a specified position for mowers in Mower App.
 * It extends java.awt.Point class adding orientation field.
 *
 * Please refer to {@link java.awt.Point} for more detail on Point.
 *
 * @author Mael Sigaroudi
 *
 */
@Component
public class MowerPoint extends Point {

	private static final long serialVersionUID = 1L;

	/**
	 * This field describe the orientation in which a mower is looking
	 */
	private String orientation;

	/**
	 * Default constructor of the class
	 */
	public MowerPoint() {}

	/**
	 * Constructor initializing all the field of the class MowerPoint
	 * @param x - new x point position to be initialize
	 * @param y - new y point position to be initialize
	 * @param orientation - new point orientation to be initialize
	 */
	public MowerPoint(int x, int y, String orientation) {
		super.x = x;
		super.y = y;
		this.orientation = orientation;
	}

	/**
	 * Getter for the orientation field
	 * @return orientation
	 */
	public String getOrientation() {
		return orientation;
	}

	/**
	 * An overrided version of toString in order to print a point the intedend way
	 * @return String - Concatenation of every fields in MowerPoint
	 */
	@Override
	public String toString() {
		return this.x + " " + this.y + " " +this.orientation;
	}
}
