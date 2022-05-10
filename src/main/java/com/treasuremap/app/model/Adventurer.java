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
	 * The path, a string containing the following chars:
	 * - 'A', moving forward;
	 * - 'D', turn to the right;
	 * - 'G', turn to the left.
	 *
	 * Here's an example with 'AADAGA', the initial position being (0,0) (top left corner):
	 *
	 * +---+---+---+
	 * | → | → | ↓ |
	 * +---+---+---+
	 * |   | ↓ | ← |
	 * +---+---+---+
	 * |   | ↓ |   |
	 * +---+---+---+
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
	 * @param tile the tile to take the treasures from.
	 */
	public void gainTreasures(Tile tile) {
		setTreasures(getTreasures() + tile.getTreasures());
		tile.setTreasures(0);
	}

	/**
	 * Returns true if given orientation matches adventurer's orientation, else false.
	 *
	 * @return true if given orientation matches adventurer's orientation, else false.
	 */
	public boolean isFacing(Orientation orientation) {
		return this.orientation.equals(orientation);
	}

	/**
	 * Sets the orientation to the next counterclockwise and returns the new facing direction.
	 *
	 * @return the new facing direction.
	 */
	public Orientation rotateLeft() {
		Orientation currentOrientation = getOrientation();
		Orientation newOrientation = Orientation.NORTH;

		if (currentOrientation.equals(Orientation.NORTH)) {
			newOrientation = Orientation.WEST;
		} else if (currentOrientation.equals(Orientation.WEST)) {
			newOrientation = Orientation.SOUTH;
		} else if (currentOrientation.equals(Orientation.SOUTH)) {
			newOrientation = Orientation.EAST;
		} else if (currentOrientation.equals(Orientation.EAST)) {
			newOrientation = Orientation.NORTH;
		}

		setOrientation(newOrientation);

		return newOrientation;

	}

	/**
	 * Sets the orientation to the next one clockwise and returns the new facing direction.
	 *
	 * @return the new facing direction.
	 */
	public Orientation rotateRight() {
		Orientation currentOrientation = getOrientation();
		Orientation newOrientation = Orientation.NORTH;

		if (currentOrientation.equals(Orientation.NORTH)) {
			newOrientation = Orientation.EAST;
		} else if (currentOrientation.equals(Orientation.EAST)) {
			newOrientation = Orientation.SOUTH;
		} else if (currentOrientation.equals(Orientation.SOUTH)) {
			newOrientation = Orientation.WEST;
		} else if (currentOrientation.equals(Orientation.WEST)) {
			newOrientation = Orientation.NORTH;
		}

		setOrientation(newOrientation);

		return newOrientation;
	}

	@Override
	public String toString() {
		return name.substring(0, 1);
	}
}
