package com.treasuremap.app.model;

import static org.junit.Assert.assertEquals;
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
	 */
	@Test
	public void new_tile_coordinates_should_be_zero() {
		Tile tile = new Tile();
		assertEquals(0, tile.getX());
		assertEquals(0, tile.getY());
	}

	/**
	 * Testing constructor {@link #TileTest(int,int)}.
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
	 * - {@link Adventurer#isPlayerPresent()}
	 * - {@link Adventurer#setX(int)}
	 * - {@link Adventurer#setY(int)}
	 * - {@link Adventurer#setType(TileType)}
	 * - {@link Adventurer#setTreasures(int)}
	 * - {@link Adventurer#setPlayerPresent(boolean)}
	 */
	@Test
	public void modified_tile_should_return_modified_values() {
		Tile tile = new Tile();
		tile.setX(10);
		tile.setY(20);
		tile.setType(TileType.MOUNTAIN);
		tile.setTreasures(32767);
		tile.setPlayerPresent(true);

		assertEquals(10, tile.getX());
		assertEquals(20, tile.getY());
		assertEquals(TileType.MOUNTAIN, tile.getType());
		assertEquals(32767, tile.getTreasures());
		assertEquals(true, tile.isPlayerPresent());
	}
	
}
