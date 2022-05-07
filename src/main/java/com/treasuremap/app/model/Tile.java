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
	 * True if a present player is on this tile, else false.
	 *
	 * @param playerPresent true if player present, else false.
	 */
	private boolean playerPresent = false;

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
}
