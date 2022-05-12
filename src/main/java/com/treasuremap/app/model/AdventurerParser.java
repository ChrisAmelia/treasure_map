package com.treasuremap.app.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * AdventurerParser
 */
public class AdventurerParser {
	/**
	 * The parser.
	 */
	private static AdventurerParser parser;

	/**
	 * Constructs a new object AdventurerParser.
	 */
	private AdventurerParser() {}

	/**
	 * Returns a list of adventurers for given file's name.
	 * Each line is expected to follow this format: name, position, orientation and the path separated by a space.
	 *
	 *   (0)  (1)      (2)       (3)
	 * 'NAME  X-Y  ORIENTATION  PATH'.
	 *
	 * @param fileName File to parse.
	 * @return a list of adventurers for given file's name.
	 */
	public List<Adventurer> parseFile(String fileName) throws IOException {
		List<Adventurer> adventurers = new ArrayList<>();

		Path path = Paths.get(fileName);

		for (String line : Files.readAllLines(path)) {
			adventurers.add(produceAdventurer(line));
		}

		return adventurers;
	}

	/**
	 * Returns an instance of adventurer's parser.
	 *
	 * @return an instance of adventurer's parser.
	 */
	public static AdventurerParser getInstance() {
		if (parser == null) {
			parser = new AdventurerParser();
		}

		return parser;
	}

	/**
	 * Parses the given line and returns the matching object {@link Adventurer}.
	 *
	 * @see #parseFile()
	 * @param line the adventurer.
	 * @return an adventurer.
	 */
	private Adventurer produceAdventurer(String line) {
		Adventurer adventurer = new Adventurer();

		String[] content = line.split(" ");
		String name      = content[0];
		String position  = content[1];
		String direction = content[2];
		String path      = content[3];

		// Retrieve the coordinates
		int x = Integer.valueOf( position.split("-")[0] ).intValue();
		int y = Integer.valueOf( position.split("-")[1] ).intValue();
		Orientation orientation = getOrientation(direction);

		adventurer.setName(name);
		adventurer.setX(x);
		adventurer.setY(y);
		adventurer.setOrientation(orientation);
		adventurer.setPath(path);

		return adventurer;
	}

	/**
	 * Returns the matching orientation for given string,
	 * returns null if it is not expected.
	 *
	 * @param value String value of the orientation.
	 * @return {@link Orientation} or null if value is not expected.
	 */
	private Orientation getOrientation(String value) {
		if ("N".equals(value)) return Orientation.NORTH;
		if ("E".equals(value)) return Orientation.EAST;
		if ("S".equals(value)) return Orientation.SOUTH;
		if ("O".equals(value) || "W".equals(value)) return Orientation.WEST;

		return null;
	}
}
