package com.treasuremap.app.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.treasuremap.app.controller.Game;

/**
 * GameUtils
 */
public class GameUtils {

	/**
	 * Writes the given game's state to file 'result.txt'.
	 *
	 * @param game The game.
	 * @throws IOException
	 */
	public static void writeGameStateToFile(Game game) throws IOException {
		Path path = Paths.get("result.txt");
		Files.writeString(path, game.toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
	}
}
