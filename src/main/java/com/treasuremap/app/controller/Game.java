package com.treasuremap.app.controller;

import java.util.List;
import com.treasuremap.app.model.Adventurer;
import com.treasuremap.app.model.Orientation;
import com.treasuremap.app.model.TreasureMap;
import lombok.Getter;
import lombok.Setter;

/**
 * Game
 */
public class Game {
	/**
	 * The adventurer presents on the map.
	 *
	 * @param adventurers The adventurers to set.
	 * @return the adventurers on the map.
	 */
	@Getter @Setter private List<Adventurer> adventurers;

	/**
	 * The treasure map.
	 *
	 * @param map the map to set.
	 * @return the treasure map.
	 */
	@Getter @Setter private TreasureMap map;

	public void play() {
		for (Adventurer adventurer : adventurers) {
			(new Thread() {
				@Override
				public void run() {

				}
			}).start();
		}
	}

	/**
	 * Moves given adventurer on the map depending on the path.
	 *
	 * @see Adventurer#getPath()
	 * @param adventurer adventurer to move.
	 */
	public void executeAdventurerPath(Adventurer adventurer) {
		for (char c : adventurer.getPath().toCharArray()) {
			if (c == 'A') {
				map.moveAdventurerForward(adventurer);
			} else if (c == 'D') {
				adventurer.rotateRight();
			} else if (c == 'G') {
				adventurer.rotateLeft();
			} else {
				System.err.println("Error, symbol '" + c + "' unknown.");
			}
		}
	}
}
