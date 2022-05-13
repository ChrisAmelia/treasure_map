package com.treasuremap.app.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * TreasureMapParser
 */
public class TreasureMapParser {
	/**
	 * The parser.
	 */
	private static TreasureMapParser parser;

	/**
	 * Constructs a new object TreasureMapParser.
	 */
	private TreasureMapParser() {}

	/**
	 * Returns a TreasureMap for given file's name.
	 * The first line of the file is expected to start with a 'C' then contains the dimension of the map separated by a space.
	 * The others are either of two types:
	 * - T X-Y n, a tile located at (X,Y) with n treasures;
	 * - M X-Y, a ountain located at (X,Y).
	 *
	 * Here's an example of file:
	 * +----------+
	 * | file.txt |
	 * +----------+
	 * | C 6 5    |
	 * | T 4-2 1  |
	 * | T 1-4 3  |
	 * | M 5-3    |
	 * +----------+
	 *
	 *     1   2   3   4   5   6
	 *   +---+---+---+---+---+---+
	 * 1 |   |   |   |   |   |   |
	 *   +---+---+---+---+---+---+
	 * 2 |   |   |   | 1 |   |   |
	 *   +---+---+---+---+---+---+
	 * 3 |   |   |   |   | * |   |
	 *   +---+---+---+---+---+---+
	 * 4 | 3 |   |   |   |   |   |
	 *   +---+---+---+---+---+---+
	 * 5 |   |   |   |   |   |   |
	 *   +---+---+---+---+---+---+
	 *
	 * @param fileName File to parse.
	 * @return a TreasureMap for given file.
	 * @throws IOException
	 */
	public TreasureMap parseFile(String fileName) throws IOException {
		Path path = Paths.get(fileName);

		List<String> lines = Files.readAllLines(path);

		TreasureMap map = initMap(lines.remove(0));

		for (String line : lines) {
			initTile(map, line);
		}

		return map;
	}

	/**
	 * Returns an instance of TreasureMapParser parser.
	 *
	 * @return an instance of TreasureMapParser parser.
	 */
	public static TreasureMapParser getInstance() {
		if (parser == null) {
			parser = new TreasureMapParser();
		}

		return parser;
	}

	/**
	 * Initializes a treasure map for given value and returns it.
	 *
	 * @see #parseFile(String)
	 * @param value String containing the map's dimensions.
	 * @return a treaure map.
	 */
	private TreasureMap initMap(String firstLine) {
		String[] firstLineContent = firstLine.split(" ");
		int width = Integer.valueOf( firstLineContent[1] ).intValue();
		int height = Integer.valueOf( firstLineContent[2] ).intValue();

		TreasureMap map = new TreasureMap(width, height);

		return map;
	}

	/**
	 * Initializes the map with given line.
	 * Be mindful of indexes.
	 *
	 * @see #parseFile(String)
	 * @param map  The map.
	 * @param line The line containing the tile's informations.
	 */
	private void initTile(TreasureMap map, String line) {
		String[] content = line.split(" ");
		String type = content[0];
		String position = content[1];
		int x = Integer.valueOf( position.split("-")[0] ).intValue();
		int y = Integer.valueOf( position.split("-")[1] ).intValue();

		Tile tile = map.getTiles()[y - 1][x - 1];

		if ("T".equals(type)) {
			int treasures = Integer.valueOf( content[2] ).intValue();
			tile.setTreasures(treasures);
		} else if ("M".equals(type)) {
			tile.setType(TileType.MOUNTAIN);
		}
	}
}
