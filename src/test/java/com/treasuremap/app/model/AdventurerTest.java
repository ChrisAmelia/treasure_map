package com.treasuremap.app.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * AdventurerTest
 */
public class AdventurerTest {

	/**
	 * Testing the getters and setters.
	 *
	 * - {@link Adventurer#getTreasures()}
	 * - {@link Adventurer#setTreasures(int)()}
	 */
	@Test
	public void modified_adventurer_should_return_modified_values() {
		Adventurer adventurer = new Adventurer();
		adventurer.setTreasures(32767);

		assertEquals(32767, adventurer.getTreasures());
	}

	/**
	 * The number of treasures must be a positive number.
	 * If a negative number is provided then nothing happens.
	 */
	@Test
	public void setting_treasures_should_be_positive() {
		Adventurer adventurer = new Adventurer();
		adventurer.setTreasures(-32767);

		assertEquals(0, adventurer.getTreasures());
	}

	/**
	 * If an adventurer has 3 treasures and gains 2,
	 * then the total number of treasures should be 5.
	 *
	 * Testing method {@link Adventurer#gainTreasures(int)}.
	 */
	@Test
	public void gained_treasures_should_be_added_to_the_current_ones() {
		Adventurer adventurer = new Adventurer();
		adventurer.setTreasures(3);
		adventurer.gainTreasures(2);

		assertEquals(5, adventurer.getTreasures());
	}
	
}
