package com.treasuremap.app.controller;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.treasuremap.app.model.Adventurer;
import com.treasuremap.app.model.Orientation;
import com.treasuremap.app.model.Tile;
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

	/**
	 * The threads.
	 *
	 * @return the threads.
	 */
	@Getter private Thread[] gameThreads;

	/**
	 * Starts the threads.
	 */
	public void play() {
		gameThreads = new Thread[adventurers.size()];

		for (int i = 0 ; i < gameThreads.length ; i++) {
			GameRunnable runnable = new GameRunnable(this, adventurers.get(i));

			gameThreads[i] = new Thread(runnable);
			gameThreads[i].run();
		}
	}

	/**
	 * Executes given adventurer's step then increases their pointer by 1.
	 * If the pointer is greater than the length of the path then does nothing.
	 *
	 * Here is an example: the adventurer 'A' is on first tile (0,0) and the path is 'AADADAGA'.
	 * The pointer is initially 0, after adventurer moves, the pointer is 1.
	 *
	 *   ↓                                               ↓
	 *   AADADAGA                                       AADADAGA
	 *
	 * +---+---+---+                                  +---+---+---+
	 * | A |   |   |                                  | A |   |   |
	 * +---+---+---+  first step 'A' (move forward)   +---+---+---+
	 * |   |   |   |   -------------------------->    |   |   |   |
	 * +---+---+---+                                  +---+---+---+
	 * |   |   |   |                                  |   |   |   |
	 * +---+---+---+                                  +---+---+---+
	 *
	 * @see Adventurer#getPath()
	 * @param adventurer adventurer to move.
	 */
	public void executeAdventurerPath(Adventurer adventurer) {
		String path = adventurer.getPath();
		int pointer = adventurer.getPointer();

		if (pointer >= path.length()) {
			return;
		}

		char c = path.charAt(pointer);

		if (c == 'A') {
			map.moveAdventurerForward(adventurer);
			adventurer.setPointer(pointer + 1);
		} else if (c == 'D') {
			adventurer.rotateRight();
			adventurer.setPointer(pointer + 1);
		} else if (c == 'G') {
			adventurer.rotateLeft();
			adventurer.setPointer(pointer + 1);
		} else {
			System.err.println("Error, symbol '" + c + "' unknown.");
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(map.toString());
		builder.append("\n\n");
		builder.append(adventurersInfo());
		builder.append("\n");
		builder.append(mountainsInfo());
		builder.append("\n");
		builder.append(treasuresInfo());

		return builder.toString();
	}

	/**
	 * Returns a string containing the current state of the adventurers.
	 *
	 * @return a string containing the current state of the adventurers.
	 */
	private String adventurersInfo() {
		StringBuilder builder = new StringBuilder("[ Adventurers ]\n");

		if (adventurers.isEmpty()) {
			builder.append("No adventurers");

			return builder.toString();
		}

		for (Adventurer adventurer : adventurers) {
			builder.append(adventurer.getName());
			builder.append(": ");
			builder.append("(" + (adventurer.getX() + 1) + "," + (adventurer.getY() + 1) + "), ");
			builder.append("treasures=" + adventurer.getTreasures() + ", ");
			builder.append("path=" + adventurer.getPath()+ ", ");
			builder.append("orientation=" + adventurer.getOrientation().name());
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Returns a string containing the locations of mountains.
	 *
	 * @return a string containing the locations of mountains.
	 */
	private String mountainsInfo() {
		StringBuilder builder = new StringBuilder("[ Mountains ]\n");
		boolean hasMountain = false;

		Tile[][] tiles = map.getTiles();

		for (int i = 0 ; i < tiles.length ; i++) {
			for (int j = 0 ; j < tiles[i].length ; j++) {
				Tile tile = tiles[i][j];

				if (tile.isMountain()) {
					hasMountain = true;

					builder.append("(" + (tile.getX() + 1) + "," + (tile.getY() + 1) + ")");
					builder.append("\n");
				}
			}
		}

		if (!hasMountain) {
			builder.append("No mountains\n");
		}

		return builder.toString();
	}

	/**
	 * Returns a string containing the locations of treasures.
	 *
	 * @return a string containing the locations of treasures.
	 */
	private String treasuresInfo() {
		StringBuilder builder = new StringBuilder("[ Treasures ]\n");
		boolean hasTreasures = false;

		Tile[][] tiles = map.getTiles();

		for (int i = 0 ; i < tiles.length ; i++) {
			for (int j = 0 ; j < tiles[i].length ; j++) {
				Tile tile = tiles[i][j];

				if (tile.hasTreasures()) {
					hasTreasures = true;

					builder.append("(" + (tile.getX() + 1) + "," + (tile.getY() + 1) + "), quantity=" + tile.getTreasures());
					builder.append("\n");
				}
			}
		}

		if (!hasTreasures) {
			builder.append("No treasures\n");
		}

		return builder.toString();
	}
}
