package com.treasuremap.app.model;

import java.util.ArrayList;
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
		adventurer.setX(x);
		adventurer.setY(y);

		return true;
	}

	/**
	 * Returns the adventurers present on the map.
	 *
	 * @return the adventurers present on the map.
	 */
	public List<Adventurer> getAdventurers() {
		List<Adventurer> adventurers = new ArrayList<>();

		Tile[][] tiles = getTiles();

		for (int i = 0 ; i < tiles.length ; i++) {
			for (int j = 0 ; j < tiles[i].length ; j++) {
				if (tiles[i][j].isAdventurerPresent()) {
					adventurers.add(tiles[i][j].getAdventurer());
				}
			}
		}

		return adventurers;
	}

	/**
	 * Makes an adventurer move forward based on their orientation:
	 * - if NORTH then moves up, 
	 * - if SOUTH then moves down,
	 * - if EAST then moves right,
	 * - if WEST then moves left.
	 *
	 *  Here is what to expect. The header being the orientation of the adventurer:
	 *
	 *                           N               S               E               W
	 * +---+---+---+       +---+---+---+   +---+---+---+   +---+---+---+   +---+---+---+
	 * |   |   |   |       |   | A |   |   |   |   |   |   |   |   |   |   |   |   |   |
	 * +---+---+---+       +---+---+---+   +---+---+---+   +---+---+---+   +---+---+---+
	 * |   | A |   | moves |   |   |   |   |   |   |   |   |   |   | A |   | A |   |   |
	 * +---+---+---+       +---+---+---+   +---+---+---+   +---+---+---+   +---+---+---+
	 * |   |   |   |       |   |   |   |   |   | A |   |   |   |   |   |   |   |   |   |
	 * +---+---+---+       +---+---+---+   +---+---+---+   +---+---+---+   +---+---+---+
	 */
	public void moveAdventurer(Adventurer adventurer) {
		int x = adventurer.getX();
		int y = adventurer.getY();

		if (adventurer.isFacing(Orientation.NORTH)) {
			addAdventurer(adventurer, x - 1, y);
			removeAdventurer(x, y);
		} else if (adventurer.isFacing(Orientation.SOUTH)) {
			addAdventurer(adventurer, x + 1, y);
			removeAdventurer(x, y);
		} else if (adventurer.isFacing(Orientation.EAST)) {
			addAdventurer(adventurer, x, y + 1);
			removeAdventurer(x, y);
		} else if (adventurer.isFacing(Orientation.WEST)) {
			addAdventurer(adventurer, x, y - 1);
			removeAdventurer(x, y);
		}
	}

	/**
	 * Removes an adventurer given by its coordinates.
	 *
	 * @param x the abscissa.
	 * @param y the ordinate.
	 */
	public void removeAdventurer(int x, int y) {
		Tile[][] tiles = getTiles();
		Tile tile = tiles[x][y];
		tile.setAdventurer(null);
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
