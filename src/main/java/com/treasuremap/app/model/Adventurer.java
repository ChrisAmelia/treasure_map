package com.treasuremap.app.model;

import lombok.Data;

/**
 * Adventurer
 */
@Data
public class Adventurer {
	/**
	 * The adventurer's name.
	 *
	 * @param name The name to set.
	 * @return the adventurer's name.
	 */
	private String name = "A";

	/**
	 * The number of treasures the adventurer has.
	 *
	 * @return the number of treasures.
	 */
	private int treasures;

	/**
	 * The orientation.
	 *
	 * @param orientation the orientation to set.
	 * @return the orientation.
	 */
	private Orientation orientation;

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

	/**
	 * Adds the given treasures to the current ones.
	 *
	 * @parma treasures the number of gained treasures.
	 */
	public void gainTreasures(int treasures) {
		setTreasures(getTreasures() + treasures);
	}

	@Override
	public String toString() {
		return name.substring(0, 1);
	}
}
