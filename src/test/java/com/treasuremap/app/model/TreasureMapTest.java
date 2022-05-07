package com.treasuremap.app.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * TreasureMapTest
 */
public class TreasureMapTest {

	/**
	 * Testing getters and setters.
	 *
	 * {@link TreasureMap#getWidth()}
	 * {@link TreasureMap#getHeight()()}
	 * {@link TreasureMap#setWidth(int)}
	 * {@link TreasureMap#setHeight(int)}
	 */
	@Test
	public void modified_map_should_return_modified_values() {
		TreasureMap map = new TreasureMap();
		map.setWidth(7);
		map.setHeight(9);

		assertEquals(7, map.getWidth());
		assertEquals(9, map.getHeight());
	}
	
}
