package com.treasuremap.app.model;

import lombok.Data;

/**
 * Adventurer
 */
@Data
public class Adventurer {
	/**
	 * The number of treasures the adventurer has.
	 *
	 * @return the number of treasures.
	 */
	private int treasures;

	/**
	 * Sets the treasures.
	 *
	 * @param treasures the number of treasures to set.
	 */
	public void setTreasures(int treasures) {
		if (treasures >= 0) {
			this.treasures = treasures;
		}
	}
}
