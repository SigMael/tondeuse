package com.tondeuse;

import java.awt.Point;

public class MowerPoint extends Point {
	private static final long serialVersionUID = 1L;
	private String orientation;

	public MowerPoint() {}

	public MowerPoint(int x, int y, String orientation) {
		super.x = x;
		super.y = y;
		this.orientation = orientation;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
}
