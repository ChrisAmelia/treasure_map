package com.treasuremap.app.model;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Test;

/**
 * TreasureMapParserTest
 */
public class TreasureMapParserTest {

	/**
	 * Parsing file 'treasure_map_test.txt' should produces following map:
	 * - the dimensions should be 6x5,
	 * - 1 treasure at (4,2),
	 * - 3 treasures at (1,4),
	 * - 1 mountain at (5,3).
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
	 * Be mindful of indexes.
	 * Testing method {@link TreasureMapParser#parseFile(String)}.
	 */
	@Test
	public void reading_file_should_produce_correct_treasure_map() throws IOException {
		TreasureMapParser parser = TreasureMapParser.getInstance();

		TreasureMap map = parser.parseFile("src/test/resources/treasure_map_test.txt");

		System.err.println("[ M A P ]");
		System.err.println(map.toString());

		// Dimensions
		assertEquals(6, map.getWidth());
		assertEquals(5, map.getHeight());

		// Checking tile: T 4-2 1
		Tile tile1 = map.getTiles()[1][3];
		assertEquals(1, tile1.getX());
		assertEquals(3, tile1.getY());
		assertEquals(1, tile1.getTreasures());

		// Checking tile: T 1-4 3
		Tile tile2 = map.getTiles()[3][0];
		assertEquals(3, tile2.getX());
		assertEquals(0, tile2.getY());
		assertEquals(3, tile2.getTreasures());

		// Checking tile: M 5-3
		Tile tile3 = map.getTiles()[2][4];
		assertEquals(2, tile3.getX());
		assertEquals(4, tile3.getY());
		assertEquals(TileType.MOUNTAIN, tile3.getType());
	}
	
}
