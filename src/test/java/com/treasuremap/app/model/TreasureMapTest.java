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
	 * An adventurer added on a tile containing treasures should gain it.
	 *
	 * +------+------+------+       +------+------+------+
	 * |      |      |      |       |      |      |      |
	 * +------+------+------+       +------+------+------+
	 * |      | A(0) |  7   | moves |      |      | A(7) |
	 * +------+------+------+       +------+------+------+
	 * |      |      |      |       |      |      |      |
	 * +------+------+------+       +------+------+------+

	 * Testing method {@link TreasureMap#addAdventurer(Adventurer, int, int)}.
	 */
	@Test
	public void an_adventurer__added_on_a_tile_with_treasures_should_gain_the_treasures() {
		TreasureMap map = new TreasureMap(3, 3);

		Adventurer adventurer = new Adventurer();
		adventurer.setX(1);
		adventurer.setY(1);

		// Tile's coordinates containing the treasures
		int abscissa = 2;
		int ordinate = 1;

		Tile[][] tiles = map.getTiles();
		Tile tile = tiles[ordinate][abscissa];
		tile.setTreasures(7);

		System.err.println(map.toString());

		boolean isAdded = map.addAdventurer(adventurer, abscissa, ordinate);

		assertTrue(isAdded);
		assertEquals(7, adventurer.getTreasures());
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
	 * Testing method {@link TreasureMap#moveAdventurerForward(Adventurer)}.
	 */
	@Test
	public void an_adventurer_facing_north_should_move_up() {
		TreasureMap map = new TreasureMap(3, 3);
		Adventurer adventurer = new Adventurer();
		adventurer.setOrientation(Orientation.NORTH);

		map.addAdventurer(adventurer, 1, 1);
		map.moveAdventurerForward(adventurer);

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
	 * Testing method {@link TreasureMap#moveAdventurerForward(Adventurer)}.
	 */
	@Test
	public void an_adventurer_facing_south_should_move_down() {
		TreasureMap map = new TreasureMap(3, 3);
		Adventurer adventurer = new Adventurer();
		adventurer.setOrientation(Orientation.SOUTH);

		map.addAdventurer(adventurer, 1, 1);
		map.moveAdventurerForward(adventurer);

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
	 * Testing method {@link TreasureMap#moveAdventurerForward(Adventurer)}.
	 */
	@Test
	public void an_adventurer_facing_east_should_move_right() {
		TreasureMap map = new TreasureMap(3, 3);
		Adventurer adventurer = new Adventurer();
		adventurer.setOrientation(Orientation.EAST);

		map.addAdventurer(adventurer, 1, 1);
		map.moveAdventurerForward(adventurer);

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
	 * |   | A |   | moves | A |   |   |
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   |   |   |
	 * +---+---+---+       +---+---+---+
	 *
	 * Testing method {@link TreasureMap#moveAdventurerForward(Adventurer)}.
	 */
	@Test
	public void an_adventurer_facing_west_should_move_left() {
		TreasureMap map = new TreasureMap(3, 3);
		Adventurer adventurer = new Adventurer();
		adventurer.setOrientation(Orientation.WEST);

		map.addAdventurer(adventurer, 1, 1);
		map.moveAdventurerForward(adventurer);

		Tile[][] tiles = map.getTiles();
		Tile tile = tiles[1][0];

		assertTrue(tile.isAdventurerPresent());
	}

	/**
	 * When an adventurer moves to the next tile, the previous tile should be free.
	 *
	 *                           E      
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   |   |   |
	 * +---+---+---+       +---+---+---+
	 * |   | A |   | moves |   |   | A |
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   |   |   |
	 * +---+---+---+       +---+---+---+

	 * Testing method {@link TreasureMap#moveAdventurerForward(Adventurer)}
	 */
	@Test
	public void when_adventurer_moves_previous_tile_should_be_free() {
		TreasureMap map = new TreasureMap(3, 3);
		Adventurer adventurer = new Adventurer();
		adventurer.setOrientation(Orientation.SOUTH);

		map.addAdventurer(adventurer, 1, 1);
		map.moveAdventurerForward(adventurer);

		Tile[][] tiles = map.getTiles();
		Tile tile = tiles[1][1];

		assertFalse(tile.isAdventurerPresent());
	}

	/**
	 * Adventurers cannot moves past the map's bounds, they stay on site if they attempt to do so.
	 * In the example below,
	 * - A(0, 1) is facing north and is attempting to move up, 
	 * - B(1, 0) is facing west and is attempting to move left,
	 * - C(2, 1) is facing south and is attempting to move down,
	 * - D(1, 2) is facing east and is attempting to move right.
	 *
	 * +---+---+---+       +---+---+---+
	 * |   | A |   |       |   | A |   |
	 * +---+---+---+       +---+---+---+
	 * | B |   | D | moves | B |   | D |
	 * +---+---+---+       +---+---+---+
	 * |   | C |   |       |   | C |   |
	 * +---+---+---+       +---+---+---+
	 *
	 * Testing method {@link TreasureMap#moveAdventurerForward(Adventurer)}.
	 */
	@Test
	public void an_adventurer_cannot_move_past_map_bounds() {
		TreasureMap map = new TreasureMap(3, 3);

		Adventurer adventurerA = new Adventurer();
		adventurerA.setName("A");
		adventurerA.setOrientation(Orientation.NORTH);
		int xA = 0;
		int yA = 1;

		Adventurer adventurerB = new Adventurer();
		adventurerB.setName("B");
		adventurerB.setOrientation(Orientation.WEST);
		int xB = 1;
		int yB = 0;

		Adventurer adventurerC = new Adventurer();
		adventurerC.setName("C");
		adventurerC.setOrientation(Orientation.SOUTH);
		int xC = 2;
		int yC = 1;

		Adventurer adventurerD = new Adventurer();
		adventurerD.setName("D");
		adventurerD.setOrientation(Orientation.EAST);
		int xD = 1;
		int yD = 2;

		map.addAdventurer(adventurerA, xA, yA);
		map.addAdventurer(adventurerB, xB, yB);
		map.addAdventurer(adventurerC, xC, yC);
		map.addAdventurer(adventurerD, xD, yD);

		Tile[][] tiles = map.getTiles();
		Tile tileA = tiles[xA][yA];
		Tile tileB = tiles[xB][yB];
		Tile tileC = tiles[xC][yC];
		Tile tileD = tiles[xD][yD];

		assertTrue(tileA.isAdventurerPresent());
		assertTrue(tileB.isAdventurerPresent());
		assertTrue(tileC.isAdventurerPresent());
		assertTrue(tileD.isAdventurerPresent());
	}

	/**
	 * An adventurer cannot move onto a mountain.
	 * In attempting to do so, they should remain on site.
	 *
	 *                           E      
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   |   |   |
	 * +---+---+---+       +---+---+---+
	 * |   | A | * | moves |   | A | * |
	 * +---+---+---+       +---+---+---+
	 * |   |   |   |       |   |   |   |
	 * +---+---+---+       +---+---+---+
	 *
	 * Testing method {@link TreasureMap#moveAdventurerForward(Adventurer)}.
	 */
	@Test
	public void an_adventurer_cannot_move_onto_a_mountain() {
		TreasureMap map = new TreasureMap(3, 3);
		Tile[][] tiles = map.getTiles();
		tiles[1][2].setType(TileType.MOUNTAIN);

		Adventurer adventurer = new Adventurer();

		map.addAdventurer(adventurer, 1, 1);

		Tile mountain = tiles[1][2];
		Tile currentTile = tiles[1][1];

		assertFalse(mountain.isAdventurerPresent());
		assertTrue(currentTile.isAdventurerPresent());
	}
}
