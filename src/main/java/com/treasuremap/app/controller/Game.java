package com.treasuremap.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.treasuremap.app.model.Adventurer;
import com.treasuremap.app.model.GameUtils;
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
	 * Duration of the game in seconds.
	 */
	private int duration = 0;

	/**
	 * Timer to measures elapsed time.
	 */
	private Timer durationTimer = new Timer();

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
		initTimer();
		gameThreads = new Thread[adventurers.size()];

		for (int i = 0 ; i < gameThreads.length ; i++) {
			GameRunnable runnable = new GameRunnable(this, adventurers.get(i));
			gameThreads[i] = new Thread(runnable);
			gameThreads[i].run();
		}

		try {
			for (int i = 0 ; i < gameThreads.length ;i++) {
				gameThreads[i].join();
			}
		}  catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the timer to measure the duration of the game.
	 */
	private void initTimer() {
		durationTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				duration++;

				if (hasFinished()) {
					durationTimer.cancel();
				}
			}
		}, 0, 1000);
	}

	/**
	 * Executes given adventurer's current step then increases their pointer by 1.
	 * If the pointer is greater than the path's length then does nothing.
	 *
	 * Here is an example: the adventurer 'A' is on first tile (0,0) and the path is 'AADADAGA' facing south.
	 * The pointer is initially 0, after adventurer moves, the pointer is then 1.
	 *
	 *   ↓                                               ↓
	 *   AADADAGA                                       AADADAGA
	 *
	 * +---+---+---+                                  +---+---+---+
	 * | A |   |   |                                  |   |   |   |
	 * +---+---+---+  first step 'A' (move forward)   +---+---+---+
	 * |   |   |   |   -------------------------->    | A |   |   |
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

	/**
	 * Returns true if the game has finished, else false.
	 * A game is said to have finished if every adventurer has executed every step of their path,
	 * i.e. the pointer >= path's length.
	 *
	 * @return true if the game has finished, else false.
	 */
	public boolean hasFinished() {
		for (Adventurer adventurer : adventurers) {
			if (!adventurer.hasCompletedPath()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Prints to standard output the state of the game every second.
	 */
	public void print() {
		Timer timer = new Timer();
		Game game = this;

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println("\033[H\033[2J"); // Clear screen
				System.out.println(game.toString());

				if (game.hasFinished()) {
					timer.cancel();

					try {
						GameUtils.writeGameStateToFile(game);
						System.out.println("Result written to file 'result.txt'");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}, 0, 1000);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Duration: " + duration + "s");
		builder.append("\n");
		builder.append("Game: " + (hasFinished() ? "finished" : "ongoing"));
		builder.append("\n");
		builder.append("\n");
		builder.append(map.toString());
		builder.append("\n\n");
		builder.append(adventurersInfo());
		builder.append("\n");
		builder.append(mountainsInfo());
		builder.append("\n");
		builder.append(treasuresInfo());
		builder.append("\n");

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
			builder.append(builderAdventurerInfo(adventurer));
			builder.append("\n");
		}

		return builder.toString();
	}

	/**
	 * Returns one line describing the adventurer's current state.
	 *
	 * @param adventurer The adventurer to describe.
	 * @return string containing the state of the adventurer.
	 */
	private String builderAdventurerInfo(Adventurer adventurer) {
		StringBuilder builder = new StringBuilder();

		int spaces = 0;

		// John: (2,1), treasures=0, path=AADADAGA, orientation=EAST
		spaces += adventurer.getName().length();
		spaces += 3; // to reach the first parenthesis
		spaces += String.valueOf( adventurer.getX() ).length();
		spaces += 1; // comma between the coordinates
		spaces += String.valueOf( adventurer.getY() ).length();
		spaces += 3; // closing parenthesis, comma then space
		spaces += "treasures".length();
		spaces += 1; // equal sign
		spaces += String.valueOf( adventurer.getTreasures() ).length();
		spaces += 2; // comma then space
		spaces += "path".length();
		spaces += adventurer.getPointer();

		for (int i = 0 ; i < spaces ; i++) {
			builder.append(" ");
		}

		builder.append("↓");

		builder.append("\n");
		builder.append(adventurer.getName());
		builder.append(": ");
		builder.append("(" + (adventurer.getX() + 1) + "," + (adventurer.getY() + 1) + "), ");
		builder.append("treasures=" + adventurer.getTreasures() + ", ");
		builder.append("path=" + adventurer.getPath()+ ", ");
		builder.append("orientation=" + adventurer.getOrientation().name());

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
