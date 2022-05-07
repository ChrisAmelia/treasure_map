package com.treasuremap.app.model;

import lombok.Data;

/**
 * The main component of the map.<br />
 * A tile has a type: it can be a prairie or a mountain, see {@link TileType}, by default, a tile is a prairie.<br />
 * A tile may contain treasures.
 */
@Data
public class Tile {
	/**
	 * The abscissa.
	 *
	 * @param x the abscissa to set.
	 * @return the abscissa.
	 */
	private int x;

	/**
	 * The ordinate.
	 *
	 * @param y the ordinate to set.
	 * @return the ordinate.
	 */
	private int y;

	/**
	 * Either a prairie or a mountain.
	 *
	 * @param type the type to set.
	 * @return the type.
	 */
	private TileType type = TileType.PRAIRIE;

	/**
	 * The number of treasures this tile has.
	 *
	 * @param treasures the number of treasures to set.
	 * @return the number of treasures.
	 */
	private int treasures;

	/**
	 * Adventurer on this tile, is null if there is no adventurer.
	 *
	 * @param adventurer the adventurer to set on this tile.
	 * @return the adventurer on this tile.
	 */
	private Adventurer adventurer;

	/**
	 * Constructs a new object Tile.
	 */
	public Tile() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructs a new object Tile.
	 *
	 * @param x The abscissa.
	 * @param y The ordinate.
	 */
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns true if an adventurer is present on this tile,
	 * else false.
	 *
	 * @return true if adventurer is present, else false.
	 */
	public boolean isAdventurerPresent() {
		return adventurer != null;
	}

	/**
	 * Returns a string representation of the current tile.
	 * By order of importance: check if there are treasures, then if there is an adventurer,
	 * in the last case, the tile is a plain prairie or mountain.
	 */
	@Override
	public String toString() {
		if (treasures > 0) {
			return String.valueOf(treasures);
		}

		if (adventurer != null) {
			return adventurer.toString();
		}

		return type.toString();
	}
}
