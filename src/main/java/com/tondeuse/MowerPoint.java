package com.tondeuse;

import org.springframework.stereotype.Component;

import java.awt.Point;

@Component
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

	@Override
	public String toString() {
		return this.x + " " + this.y + " " +this.orientation;
	}
}
