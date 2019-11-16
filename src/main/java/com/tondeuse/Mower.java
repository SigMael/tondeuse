package com.tondeuse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mower {

	@Autowired
	private MowerPoint position;

	private String[] moves;

	public Mower(MowerPoint startPosition, String[] moves) {
		this.position = startPosition;
		this.moves = moves;
	}

    public MowerPoint getPosition() {
		return position;
	}

	public String[] getMoves() {
		return moves;
	}
}