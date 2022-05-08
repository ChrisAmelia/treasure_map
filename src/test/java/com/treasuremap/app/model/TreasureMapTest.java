package com.treasuremap.app.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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

}
