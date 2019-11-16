package com.tondeuse;

import org.springframework.stereotype.Component;

@Component
public class Mower {
	
	private MowerPoint startPosition;
	private String[] moves;

	public MowerPoint getStartPosition() {
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