package com.treasuremap.app.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * AdventurerTest
 */
public class AdventurerTest {

	/**
	 * Testing the getters and setters.
	 *
	 * - {@link Adventurer#getName()}
	 * - {@link Adventurer#getOrientation()}
	 * - {@link Adventurer#getTreasures()}
	 * - {@link Adventurer#getPath()}
	 * - {@link Adventurer#setTreasures(int)}
	 * - {@link Adventurer#setName(String)}
	 * - {@link Adventurer#setOrientation(Orientation)}
	 * - {@link Adventurer#setPath(String)}
	 */
	@Test
	public void modified_adventurer_should_return_modified_values() {
		Adventurer adventurer = new Adventurer();
		adventurer.setTreasures(32767);
		adventurer.setName("John");
		adventurer.setOrientation(Orientation.WEST);
		adventurer.setPath("AADADAGA");

		assertEquals("John", adventurer.getName());
		assertEquals(32767, adventurer.getTreasures());
		assertEquals(Orientation.WEST, adventurer.getOrientation());
		assertEquals("AADADAGA", adventurer.getPath());
	}

	/**
	 * The method {@link Adventurer#toString()} should return the first letter of the adventurer's name.
	 * If no name is initialized or if it's empty, then returns by default the string "A".
	 *
	 * Testing method {@link Adventurer#toString()}.
	 */
	@Test
	public void to_string_should_return_the_first_letter_of_the_name() {
		Adventurer adventurer = new Adventurer();

		// No name is initialized, expecting default name "A"
		assertEquals("A", adventurer.toString());

		adventurer.setName("John");

		// Name is now John, the first letter is 'J'
		assertEquals("J", adventurer.toString());
	}

	/**
	 * The number of treasures must be a positive number.
	 * If a negative number is provided then nothing happens.
	 *
	 * Testing method {@link Adventurer#setTreasures(int)}.
	 */
	@Test
	public void setting_treasures_should_be_positive() {
		Adventurer adventurer = new Adventurer();
		adventurer.setTreasures(-32767);

		assertEquals(0, adventurer.getTreasures());
	}

	/**
	 * If an adventurer has 3 treasures and gains 2,
	 * then the total number of treasures should be 5.
	 *
	 * Testing method {@link Adventurer#gainTreasures(Tile)}.
	 */
	@Test
	public void gained_treasures_should_be_added_to_the_current_ones() {
		Adventurer adventurer = new Adventurer();
		adventurer.setTreasures(3);

		Tile tile = new Tile();
		tile.setTreasures(2);

		adventurer.gainTreasures(tile);

		assertEquals(5, adventurer.getTreasures());
	}

	/**
	 * Given the conventional clockwise directions (North, East, South, West),
	 * turning to the right means moving one direction ahead clockwise.
	 *
	 * Here is an example where the cursor point to the current direction:
	 *
	 *    ↓                          rotating right             ↓
	 * (NORTH, EAST, SOUTH, WEST)  ------------------> (NORTH, EAST, SOUTH, WEST)
	 *
	 * When reaching WEST and rotating again, the cursor comes back at the beginning, in this case, to EAST.
	 */
	@Test
	public void adventurer_rotating_right_should_face_the_next_direction_clockwise() {
		Adventurer adventurer = new Adventurer();

		adventurer.setOrientation(Orientation.NORTH);
		adventurer.rotateRight();
		assertEquals(Orientation.EAST, adventurer.getOrientation());

		adventurer.setOrientation(Orientation.EAST);
		adventurer.rotateRight();
		assertEquals(Orientation.SOUTH, adventurer.getOrientation());

		adventurer.setOrientation(Orientation.SOUTH);
		adventurer.rotateRight();
		assertEquals(Orientation.WEST, adventurer.getOrientation());

		adventurer.setOrientation(Orientation.WEST);
		adventurer.rotateRight();
		assertEquals(Orientation.NORTH, adventurer.getOrientation());
	}

	/**
	 * @see AdventurerTest#adventurer_rotating_right_should_face_the_next_direction_clockwise()
	 */
	@Test
	public void adventurer_rotating_left_should_face_the_next_direction_counterclockwise() {
		Adventurer adventurer = new Adventurer();

		adventurer.setOrientation(Orientation.NORTH);
		adventurer.rotateLeft();
		assertEquals(Orientation.WEST, adventurer.getOrientation());

		adventurer.setOrientation(Orientation.WEST);
		adventurer.rotateLeft();
		assertEquals(Orientation.SOUTH, adventurer.getOrientation());

		adventurer.setOrientation(Orientation.SOUTH);
		adventurer.rotateLeft();
		assertEquals(Orientation.EAST, adventurer.getOrientation());

		adventurer.setOrientation(Orientation.EAST);
		adventurer.rotateLeft();
		assertEquals(Orientation.NORTH, adventurer.getOrientation());
	}
	
}
