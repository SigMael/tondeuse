package com.tondeuse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mower {

	@Autowired
	private MowerPoint startPosition;

	private String[] moves;

	public Mower(MowerPoint startPosition, String[] moves) {
		this.startPosition = startPosition;
		this.moves = moves;
	}

    public MowerPoint getPosition() {
		return startPosition;
	}

	public void setStartPosition(MowerPoint startPosition) {
		this.startPosition = startPosition;
	}

	public String[] getMoves() {
		return moves;
	}

	public void setMoves(String[] moves) {
		this.moves = moves;
	}
}