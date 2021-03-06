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
	 * @param width the width of the map.
	 * @param height the height of the map.
	 */
	public TreasureMap(int width, int height) {
		tiles = new Tile[height][width];

		for (int i = 0 ; i < tiles.length ; i++) {
			for (int j = 0 ; j < tiles[i].length ; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}
	}

	/**
	 * Returns the width of the map.
	 */
	public int getWidth() {
		return tiles[0].length;
	}

	/**
	 * Returns the height of the map.
	 */
	public int getHeight() {
		return tiles.length;
	}

	/**
	 * Adds the given adventurer to the given coordinates and returns true, else false.
	 * Use this method to initialize the adventurers at the beginning.
	 *
	 * @see   #canAdventurerMoveToTile(int, int)
	 * @param adventurer The adventurer to add.
	 * @param abscissa   The abscissa of the adventurer.
	 * @param ordinate   The ordinate of the adventurer.
	 * @return true if the adventurer is added, else false.
	 */
	public synchronized boolean addAdventurer(Adventurer adventurer, int abscissa, int ordinate) {
		if (!canAdventurerMoveToTile(abscissa, ordinate)) {
			return false;
		}

		Tile tile = getTiles()[ordinate][abscissa];

		if (tile.hasTreasures()) {
			adventurer.gainTreasures(tile);
		}

		tile.setAdventurer(adventurer);
		adventurer.setX(abscissa);
		adventurer.setY(ordinate);

		return true;
	}

	/**
	 * Returns true if an adventurer can move to given coordinates, else false; reasons for that to occur are:
	 * - the tile is a mountain,
	 * - the tile is already occupied by another adventurer.
	 *
	 * @param abscissa the abscissa to test.
	 * @param ordinate the ordinate to test.
	 * @return true if an adventurer can move to given coordinates, else false.
	 */
	private boolean canAdventurerMoveToTile(int abscissa, int ordinate) {
		if (!areCoordinatesWithinBounds(abscissa, ordinate)) {
			return false;
		}

		Tile tile = getTiles()[ordinate][abscissa];

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
		int currentX = adventurer.getX();
		int currentY = adventurer.getY();
		boolean hasAdventurerMoved = false;

		if (adventurer.isFacing(Orientation.NORTH)) {
			hasAdventurerMoved = addAdventurer(adventurer, currentX, currentY - 1);
		} else if (adventurer.isFacing(Orientation.SOUTH)) {
			hasAdventurerMoved = addAdventurer(adventurer, currentX, currentY + 1);
		} else if (adventurer.isFacing(Orientation.EAST)) {
			hasAdventurerMoved = addAdventurer(adventurer, currentX + 1, currentY);
		} else if (adventurer.isFacing(Orientation.WEST)) {
			hasAdventurerMoved = addAdventurer(adventurer, currentX - 1, currentY);
		}

		if (hasAdventurerMoved) {
			removeAdventurer(currentX, currentY);
		}
	}

	/**
	 * Returns true if the given coordinates are within the map's bounds, else false.
	 *
	 * @param abscissa The abscissa.
	 * @param ordinate the ordinate.
	 * @return true if the given coordinates are within the map's bounds, else false.
	 */
	private boolean areCoordinatesWithinBounds(int abscissa, int ordinate) {
		if (ordinate < 0) return false;
		if (ordinate > getTiles().length - 1)  return false;
		if (abscissa < 0) return false;
		if (abscissa > tiles[0].length - 1) return false;
		return true;
	}

	/**
	 * Removes an adventurer given by its coordinates.
	 *
	 * @param abscissa the abscissa.
	 * @param ordinate the ordinate.
	 */
	public void removeAdventurer(int abscissa, int ordinate) {
		Tile[][] tiles = getTiles();
		Tile tile = tiles[ordinate][abscissa];
		tile.setAdventurer(null);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		int width = getWidth();
		int spaces = 3;

		// First row index: initial indent
		for (int i = 0 ; i <= spaces ; i++) {
			builder.append(" ");
		}

		// First row index: column index
		for (int i = 0 ; i < width; i++) {
			for (int k = 0 ; k < spaces;  k++) {
				if (k == (spaces / 2)) {
					builder.append(i + 1);
				} else {
					builder.append(" ");
				}
			}

			builder.append(" ");
		}

		builder.append("\n");

		addSeparator(builder, spaces, width);

		for (int i = 0 ; i < tiles.length ; i++) {
			// Row index
			for (int k = 0; k < spaces; k++) {
				if (k == (spaces / 2)) {
					builder.append(i + 1);
				} else {
					builder.append(" ");
				}
			}

			for (int j = 0 ; j < tiles[i].length ; j++) {
				builder.append("|");

				for (int k = 0 ; k < spaces ; k++) {
					if (k == (spaces / 2)) {
						builder.append(tiles[i][j].toString());
					} else {
						builder.append(" ");
					}
				}
			}

			builder.append("|");
			builder.append("\n");
			addSeparator(builder, spaces, width);
		}

		return builder.toString();
	}

	/**
	 * Adds ASCII separator line.
	 *
	 * @param builder The buffer.
	 * @param spaces  The indent.
	 * @param width   The matrix's width.
	 */
	private static void addSeparator(StringBuilder builder, int spaces, int width) {
		for (int k = 0 ; k < spaces ; k++) {
			builder.append(" ");
		}

		for (int i = 0 ; i < width ; i++) {
			builder.append("+");

			for (int k = 0 ; k < spaces ; k++) {
				builder.append("-");
			}

			if (i == width - 1) {
				builder.append("+");
			}
		}

		builder.append("\n");
	}

}
