package com.treasuremap.app.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * TileTest
 */
public class TileTest {

	/**
	 * When a new tile is created, the default type should be {@link TileType#PRAIRIE}.
	 */
	@Test
	public void new_tile_should_be_prairie() {
		Tile tile = new Tile();
		assertEquals(TileType.PRAIRIE, tile.getType());
	}

	/**
	 * When a new tile is created, the default coordinates should be (0,0) .
	 *
	 * Testing default constructor {@link com.treasuremap.app.model.Tile()}.
	 */
	@Test
	public void new_tile_coordinates_should_be_zero() {
		Tile tile = new Tile();
		assertEquals(0, tile.getX());
		assertEquals(0, tile.getY());
	}

	/**
	 * Testing constructor {@link com.treasuremap.app.model.Tile(int,int)}.
	 */
	public void create_new_tile_with_coordinates_should_have_given_coordinates() {
		Tile tile = new Tile(4, 3);

		assertEquals(4, tile.getX());
		assertEquals(3, tile.getY());
	}

	/**
	 * Testing the getters and setters.
	 *
	 * - {@link Adventurer#getX()}
	 * - {@link Adventurer#getY()}
	 * - {@link Adventurer#getType()}
	 * - {@link Adventurer#getTreasures()}
	 * - {@link Adventurer#setX(int)}
	 * - {@link Adventurer#setY(int)}
	 * - {@link Adventurer#setType(TileType)}
	 * - {@link Adventurer#setTreasures(int)}
	 */
	@Test
	public void modified_tile_should_return_modified_values() {
		Tile tile = new Tile();
		tile.setX(10);
		tile.setY(20);
		tile.setType(TileType.MOUNTAIN);
		tile.setTreasures(32767);

		assertEquals(10, tile.getX());
		assertEquals(20, tile.getY());
		assertEquals(TileType.MOUNTAIN, tile.getType());
		assertEquals(32767, tile.getTreasures());
	}
	
	/**
	 * Testing methods:
	 * - {@link Tile#setAdventurer(Adventurer)}
	 * - {@link Tile#isAdventurerPresent()}
	 */
	@Test
	public void a_tile_with_an_adventurer_should_return_their_presence() {
		Adventurer adventurer = new Adventurer();
		Tile tile = new Tile();

		// By default, a tile has no adventurer on it
		assertFalse(tile.isAdventurerPresent());

		tile.setAdventurer(adventurer);

		assertTrue(tile.isAdventurerPresent());
	}

	/**
	 * An adventurer cannot walk on a mountain.
	 * Thus setting an adventurer on a tile of type mountain should do nothing.
	 *
	 * Testing method {@link Tile#setAdventurer(Adventurer)}.
	 */
	@Test
	public void an_adventurer_cannot_be_set_on_a_mountain() {
		Adventurer adventurer = new Adventurer();

		Tile tile = new Tile();
		tile.setType(TileType.MOUNTAIN);
		tile.setAdventurer(adventurer);

		assertFalse(tile.isAdventurerPresent());
	}

	/**
	 * An adventurer is representated by the first letter of their name.
	 * A tile of type {@link TileType#PRAIRIE} is representated by a space ' '.
	 * A tile of type {@link TileType#MOUNTAIN} is representated by a plus 'x'.
	 * A tile containing treasures is representated by the number of treasures.
	 *
	 * Testing method {@link Tile#toString()}.
	 */
	@Test
	public void a_tile_representation_should_match_their_type_or_the_adventurer() {
		Tile tile = new Tile();
		Adventurer adventurer = new Adventurer();
		adventurer.setName("John");

		// By default, an adventurer without a name is representated by the letter 'A'
		tile.setAdventurer(adventurer);
		assertEquals("J", tile.toString());

		tile.setAdventurer(null);
		assertEquals(" ", tile.toString());

		tile.setType(TileType.MOUNTAIN);
		assertEquals("x", tile.toString());

		tile.setTreasures(7);
		assertEquals("7", tile.toString());
	}
}
