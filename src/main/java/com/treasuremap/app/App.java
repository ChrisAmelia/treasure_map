package com.treasuremap.app;

import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.treasuremap.app.controller.Game;
import com.treasuremap.app.model.Adventurer;
import com.treasuremap.app.model.Orientation;
import com.treasuremap.app.model.Tile;
import com.treasuremap.app.model.TileType;
import com.treasuremap.app.model.TreasureMap;

/**
 * Hello world!
 *
 */
public class App {

	public static void clearScreen() {
		System.out.println("\033[H\033[2J");
	}

    public static void main(String[] args) {
		Adventurer adventurer = new Adventurer();
		adventurer.setName("John");
		adventurer.setOrientation(Orientation.EAST);
		adventurer.setPath("AADADAGA");

		TreasureMap map = new TreasureMap(3, 3);
		map.addAdventurer(adventurer, 0, 0);

		Tile tile = map.getTiles()[2][1];
		tile.setTreasures(7);

		Game game = new Game();
		game.setMap(map);
		game.setAdventurers(Stream.of(adventurer).collect(Collectors.toList()));
		System.out.println(game.toString());

		game.play();

		Thread[] threads = game.getGameThreads();

		try {
			for (int i = 0 ; i < threads.length ;i++) {
				threads[i].join();
			}
		}  catch (InterruptedException e) {
			e.printStackTrace();
		}

		Timer timer = new Timer();
		int ms = 250;
		printGame(timer, game, ms);
	}

	/**
	 * Prints the game's state for given duration in milliseconds.
	 *
	 * @param timer Timer.
	 * @param game  Game.
	 * @param ms    Milliseconds.
	 */
	public static void printGame(Timer timer, Game game, int ms) {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				clearScreen();
				System.out.println(game.toString());

				if (game.hasFinished()) {
					timer.cancel();
				}
			}
		}, 0, 1000);
	}

}
