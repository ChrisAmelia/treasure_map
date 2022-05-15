package com.treasuremap.app;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.treasuremap.app.controller.Game;
import com.treasuremap.app.model.Adventurer;
import com.treasuremap.app.model.AdventurerParser;
import com.treasuremap.app.model.Orientation;
import com.treasuremap.app.model.Tile;
import com.treasuremap.app.model.TileType;
import com.treasuremap.app.model.TreasureMap;
import com.treasuremap.app.model.TreasureMapParser;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length >= 2) {
			String adventurersFile = args[0];
			String mapFile = args[1];

			System.out.println("Adventurers' file: " + adventurersFile);
			System.out.println("Treasure map's file: " + mapFile);

			TreasureMap map = TreasureMapParser.getInstance().parseFile(mapFile);
			List<Adventurer> adventurers = AdventurerParser.getInstance().parseFile(adventurersFile);

			System.out.print("\033[H\033[2J");
			System.out.println("Game initialized. Game starting in 3 seconds.");
			Thread.sleep(3000);

			Game game = new Game();
			game.setMap(map);
			game.setAdventurers(adventurers);
			game.play();
			game.print();
		} else {
			System.out.print("\033[H\033[2J");
			System.out.println("To start a game with your own settings, provide two files: (1) adventurers.txt (2) map.txt");
			System.out.println("The files' name don't have to be exactly as above, but the order must be respected.");
			System.out.println("Starting demo in 5 seconds");
			Thread.sleep(5000);
			playDemo();
		}
	}

	/**
	 * Plays an instance of game.
	 */
	private static void playDemo() {
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
		game.print();
	}

}
