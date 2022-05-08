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
	 * The path.
	 *
	 * @param path the path to set.
	 * @return the path.
	 */
	private String path;

	/**
	 * The abscissa.
	 *
	 * @param abscissa the abscissa to set.
	 * @return the abscissa.
	 */
	private int x;

	/**
	 * The ordinate.
	 *
	 * @param ordinate the ordinate to set.
	 * @return the ordinate.
	 */
	private int y;

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

	/**
	 * Returns true if given orientation matches adventurer's orientation, else false.
	 *
	 * @return true if given orientation matches adventurer's orientation, else false.
	 */
	public boolean isFacing(Orientation orientation) {
		return this.orientation.equals(orientation);
	}

	@Override
	public String toString() {
		return name.substring(0, 1);
	}
}
