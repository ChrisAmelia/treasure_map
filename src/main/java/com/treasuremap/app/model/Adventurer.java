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
	 * @param treasures the number of treasures to set.
	 * @return the number of treasures.
	 */
	private int treasures;
}
