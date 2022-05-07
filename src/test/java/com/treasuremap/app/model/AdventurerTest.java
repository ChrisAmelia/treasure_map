package com.treasuremap.app.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * AdventurerTest
 */
public class AdventurerTest {

	/**
	 * Testing the getters and setters.
	 */
	@Test
	public void modified_adventurer_should_return_modified_values() {
		Adventurer adventurer = new Adventurer();
		adventurer.setTreasures(32767);

		assertEquals(32767, adventurer.getTreasures());
	}
	
}
