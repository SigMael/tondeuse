package com.tondeuse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Main entity describing mowers in Mower App.
 * A Mower know it's own position & moves.
 *
 * @author Mael Sigaroudi
 *
 */
@Component
public class Mower {

	/**
	 * Field describing a mower position
	 */
	@Autowired
	private MowerPoint position;

	/**
	 * Field describing all moves of a mower
	 */
	private String[] moves;

	/**
	 * Constructor which take both startPosition Point & moves List parameters to initalize them.
	 *
	 * @param startPosition : starting position of the mower
	 * @param moves : moves list of the mower
	 */
	public Mower(MowerPoint startPosition, String[] moves) {
		this.position = startPosition;
		this.moves = moves;
	}

	/**
	 * Getter for position field
	 * @return position of a mower (MowerPoint type)
	 */
    public MowerPoint getPosition() {
		return position;
	}

	/**
	 * Getter for moves field
	 * @return all moves of a mower (String[] type)
	 */
	public String[] getMoves() {
		return moves;
	}

}