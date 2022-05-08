package com.treasuremap.app.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.List;
import org.junit.Test;

/**
 * TreasureMapTest
 */
public class TreasureMapTest {

	/**
	 * Check if the every tile of the map is properly initialized.
	 * Check if the map has the proper dimensions.
	 *
	 * Testing: constructor {@link com.treasuremap.app.model.TreasureMap(int,int)}.
	 */
	@Test
	public void new_map_should_have_all_tiles_not_null_and_dimensions_not_null() {
		TreasureMap map = new TreasureMap(5, 6);
		Tile[][] tiles = map.getTiles();

		for (int i = 0 ; i < tiles.length ; i++) {
			for (int j = 0 ; j < tiles[i].length ; j++) {
				if (tiles[i][j] == null) {
					fail();
				}
			}
		}

		assertEquals(5, map.getWidth());
		assertEquals(6, map.getHeight());
	}

	/**
	 * Adding an adventurer on a tile of type mountain should do nothing.
	 *
	 * Testing method {@link TreasureMap#addAdventurer(Adventurer, int, int)}.
	 */
	@Test
	public void an_adventurer_cannot_be_added_on_a_mountain() {
		Adventurer adventurer = new Adventurer();
		TreasureMap map = new TreasureMap(5, 6);
		Tile[][] tiles = map.getTiles();

		int x = 2;
		int y = 2;

		Tile mountainTile = tiles[2][2];
		mountainTile.setType(TileType.MOUNTAIN);

		boolean isAdded = map.addAdventurer(adventurer, 2, 2);

		assertFalse(isAdded);
	}

	/**
	 * An adventurer cannot be added on the top of another adventurer.
	 *
	 * Testing method {@link TreasureMap#addAdventurer(Adventurer, int, int)}.
	 */
	@Test
	public void an_adventurer_cannot_be_added_on_another_tile_occupied_by_an_adventurer() {
		Adventurer foo = new Adventurer();
		foo.setName("Foo");

		Adventurer bar = new Adventurer();
		bar.setName("Bar");

		TreasureMap map = new TreasureMap(5, 6);

		int x = 2;
		int y = 2;

		map.addAdventurer(foo, x, y);

		boolean isAdded = map.addAdventurer(bar, x, y);

		assertFalse(isAdded);
	}

	/**
	 * An adventurer cannot be added on a tile containing treasures.
	 *
	 * Testing method {@link TreasureMap#addAdventurer(Adventurer, int, int)}.
	 */
	@Test
	public void an_adventurer_cannotbe_added_on_a_tile_with_treasures() {
		Adventurer adventurer = new Adventurer();
		TreasureMap map = new TreasureMap(5, 6);

		int x = 2;
		int y = 2;

		Tile[][] tiles = map.getTiles();
		Tile tile = tiles[x][y];

		tile.setTreasures(32767);

		boolean isAdded = map.addAdventurer(adventurer, x, y);

		assertFalse(isAdded);
	}

	/**
	 * If there are 5 adventurers present on the map, then this method
	 * should return a list of 5 adventurers.
	 *
	 * Testing method {@link TreasureMap#getAdventurers()}.
	 */
	@Test
	public void getAdventurers_should_return_the_exact_total_of_adventurers() {
		TreasureMap map = new TreasureMap(7, 7);

		Adventurer adventurer1 = new Adventurer();
		Adventurer adventurer2 = new Adventurer();
		Adventurer adventurer3 = new Adventurer();
		Adventurer adventurer4 = new Adventurer();
		Adventurer adventurer5 = new Adventurer();

		map.addAdventurer(adventurer1, 1, 1);
		map.addAdventurer(adventurer2, 2, 2);
		map.addAdventurer(adventurer3, 3, 3);
		map.addAdventurer(adventurer4, 4, 4);
		map.addAdventurer(adventurer5, 5, 5);

		List<Adventurer> adventurers = map.getAdventurers();

		assertEquals(5, adventurers.size());
	}

	/**
	 * When an adventurer faces north, they should move up.
	 *
	 *                           N      
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   | A |   |
	 * +---+---+---+       +---+---+---+
	 * |   | A |   | moves |   |   |   |
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   |   |   |
	 * +---+---+---+       +---+---+---+
	 *
	 * Testing method {@link TreasureMap#moveAdventurer(Adventurer)}.
	 */
	@Test
	public void an_adventurer_facing_north_should_move_up() {
		TreasureMap map = new TreasureMap(3, 3);
		Adventurer adventurer = new Adventurer();
		adventurer.setOrientation(Orientation.NORTH);

		map.addAdventurer(adventurer, 1, 1);
		map.moveAdventurer(adventurer);

		Tile[][] tiles = map.getTiles();
		Tile tile = tiles[0][1];

		assertTrue(tile.isAdventurerPresent());
	}

	/**
	 * When an adventurer faces south, they should move down.
	 *
	 *                           S      
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   |   |   |
	 * +---+---+---+       +---+---+---+
	 * |   | A |   | moves |   |   |   |
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   | A |   |
	 * +---+---+---+       +---+---+---+
	 *
	 * Testing method {@link TreasureMap#moveAdventurer(Adventurer)}.
	 */
	@Test
	public void an_adventurer_facing_south_should_move_down() {
		TreasureMap map = new TreasureMap(3, 3);
		Adventurer adventurer = new Adventurer();
		adventurer.setOrientation(Orientation.SOUTH);

		map.addAdventurer(adventurer, 1, 1);
		map.moveAdventurer(adventurer);

		Tile[][] tiles = map.getTiles();
		Tile tile = tiles[2][1];

		assertTrue(tile.isAdventurerPresent());
	}

	/**
	 * When an adventurer faces east, they should move right.
	 *
	 *                           E      
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   |   |   |
	 * +---+---+---+       +---+---+---+
	 * |   | A |   | moves |   |   | A |
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   |   |   |
	 * +---+---+---+       +---+---+---+
	 *
	 * Testing method {@link TreasureMap#moveAdventurer(Adventurer)}.
	 */
	@Test
	public void an_adventurer_facing_east_should_move_right() {
		TreasureMap map = new TreasureMap(3, 3);
		Adventurer adventurer = new Adventurer();
		adventurer.setOrientation(Orientation.EAST);

		map.addAdventurer(adventurer, 1, 1);
		map.moveAdventurer(adventurer);

		Tile[][] tiles = map.getTiles();
		Tile tile = tiles[1][2];

		assertTrue(tile.isAdventurerPresent());
	}

	/**
	 * When an adventurer faces west, they should move left.
	 *
	 *                           W      
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   |   |   |
	 * +---+---+---+       +---+---+---+
	 * |   | A |   | moves | W |   |   |
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   |   |   |
	 * +---+---+---+       +---+---+---+

	 */
	@Test
	public void an_adventurer_facing_west_should_move_left() {
		TreasureMap map = new TreasureMap(3, 3);
		Adventurer adventurer = new Adventurer();
		adventurer.setOrientation(Orientation.WEST);

		map.addAdventurer(adventurer, 1, 1);
		map.moveAdventurer(adventurer);

		Tile[][] tiles = map.getTiles();
		Tile tile = tiles[1][0];

		assertTrue(tile.isAdventurerPresent());
	}
}
