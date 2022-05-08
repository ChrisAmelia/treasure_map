package com.treasuremap.app.model;

import java.util.List;
import lombok.Data;

/**
 * TreasureMap
 */
@Data
public class TreasureMap {
	/**
	 * The tiles of the map.
	 *
	 * @param tiles The tiles to set.
	 * @return the tiles of the map.
	 */
	private Tile[][] tiles;

	/**
	 * Constructs a new object TreasureMap.
	 *
	 * @param rows the rows of the map.
	 * @param cols the columns of the map.
	 */
	public TreasureMap(int rows, int cols) {
		tiles = new Tile[rows][cols];

		for (int i = 0 ; i < rows ; i++) {
			for (int j = 0 ; j < cols ; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}
	}

	/**
	 * Returns the width of the map.
	 */
	public int getWidth() {
		return tiles.length;
	}

	/**
	 * Returns the height of the map.
	 */
	public int getHeight() {
		return tiles[0].length;
	}


	/**
	 * Adds the given adventurer to the given coordinates and returns true.
	 * Returns false if the given adventurer cannot be added, reasons for that to occur are:
	 * - the tile contains treasures,
	 * - the tile is a mountain,
	 * - the tile is already occupied by another adventurer.
	 * Use this method to initialize the adventurers at the beginning.
	 *
	 * @param adventurer The adventurer to add.
	 * @param x          The abscissa of the adventurer.
	 * @param y          The ordinate of the adventurer.
	 * @return true if the adventurer is added, else false.
	 */
	public boolean addAdventurer(Adventurer adventurer, int x, int y) {
		Tile tile = getTiles()[x][y];

		if (tile.hasTreasures()) {
			return false;
		}

		if (tile.isMountain()) {
			return false;
		}

		if (tile.isAdventurerPresent()) {
			return false;
		}

		tile.setAdventurer(adventurer);

		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("   ");

		for (int j = 0 ; j < tiles[0].length ; j++) {
			builder.append((j + 1) + " ");
		}

		builder.append("\n");

		for (int i = 0 ; i < tiles.length ; i++) {
			builder.append((i + 1) + " ");
			for (int j = 0 ; j < tiles[i].length ; j++) {
				builder.append("|" + tiles[i][j].toString());
			}

			builder.append("|");
			builder.append("\n");
		}


		return builder.toString();
	}
}
