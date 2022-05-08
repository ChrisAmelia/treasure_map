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
	 * Adds the given adventurer to the given coordinates and returns true, else false.
	 * Use this method to initialize the adventurers at the beginning.
	 *
	 * @see   #canAdventurerMoveToTile(int, int)
	 * @param adventurer The adventurer to add.
	 * @param x          The abscissa of the adventurer.
	 * @param y          The ordinate of the adventurer.
	 * @return true if the adventurer is added, else false.
	 */
	public synchronized boolean addAdventurer(Adventurer adventurer, int x, int y) {
		if (!canAdventurerMoveToTile(x, y)) {
			return false;
		}

		Tile tile = getTiles()[x][y];

		if (tile.hasTreasures()) {
			adventurer.gainTreasures(tile);
		}

		tile.setAdventurer(adventurer);
		adventurer.setX(x);
		adventurer.setY(y);

		return true;
	}

	/**
	 * Returns true if an adventurer can move to given coordinates, else false; reasons for that to occur are:
	 * - the tile is a mountain,
	 * - the tile is already occupied by another adventurer.
	 *
	 * @param x the abscissa to test.
	 * @param y the ordinate to test.
	 * @return true if an adventurer can move to given coordinates, else false.
	 */
	private boolean canAdventurerMoveToTile(int x, int y) {
		if (!areCoordinatesWithinBounds(x, y)) {
			return false;
		}

		Tile tile = getTiles()[x][y];

		if (tile.isMountain()) {
			return false;
		}

		if (tile.isAdventurerPresent()) {
			return false;
		}

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
	public void moveAdventurerForward(Adventurer adventurer) {
		int oldX = adventurer.getX();
		int oldY = adventurer.getY();
		boolean hasAdventurerMoved = false;

		if (adventurer.isFacing(Orientation.NORTH)) {
			hasAdventurerMoved = addAdventurer(adventurer, oldX - 1, oldY);
		} else if (adventurer.isFacing(Orientation.SOUTH)) {
			hasAdventurerMoved = addAdventurer(adventurer, oldX + 1, oldY);
		} else if (adventurer.isFacing(Orientation.EAST)) {
			hasAdventurerMoved = addAdventurer(adventurer, oldX, oldY + 1);
		} else if (adventurer.isFacing(Orientation.WEST)) {
			hasAdventurerMoved = addAdventurer(adventurer, oldX, oldY - 1);
		}

		if (hasAdventurerMoved) {
			removeAdventurer(oldX, oldY);
		}
	}

	/**
	 * Returns true if the given coordinates are within the map's bounds, else false.
	 *
	 * @param x The abscissa.
	 * @param y the ordinate.
	 * @return true if the given coordinates are within the map's bounds, else false.
	 */
	private boolean areCoordinatesWithinBounds(int x, int y) {
		if (x < 0) return false;
		if (x > getTiles().length - 1)  return false;
		if (y < 0) return false;
		if (y > tiles[0].length - 1) return false;
		return true;
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
