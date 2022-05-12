package com.treasuremap.app.controller;

import static org.junit.Assert.assertEquals;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.treasuremap.app.model.Adventurer;
import com.treasuremap.app.model.Orientation;
import com.treasuremap.app.model.TreasureMap;
import org.junit.Test;

/**
 * GameTest
 */
public class GameTest {

	/**
	 * The initial position of the adventurer is (0,0).
	 * Given the path "AADADAGA" and facing direction EAST, the adventurer should arrive on (2,1).
	 *
	 * +---+---+---+
	 * | → | → | ↓ |
	 * +---+---+---+
	 * |   | ↓ | ← |
	 * +---+---+---+
	 * |   | ↓ |   |
	 * +---+---+---+
	 *
	 * Testing method {@link Game#executeAdventurerPath(Adventurer)}. // TODO signature will change
	 */
	@Test
	public void adventurer_with_finite_path_should_have_a_deterministic_result() {
		Adventurer adventurer = new Adventurer();
		adventurer.setPath("AADADAGA");
		adventurer.setOrientation(Orientation.EAST);

		TreasureMap map = new TreasureMap(3, 3);
		map.addAdventurer(adventurer, 0, 0);

		Game game = new Game();
		game.setMap(map);
		game.setAdventurers(Stream.of(adventurer).collect(Collectors.toList()));
		game.executeAdventurerPath(adventurer);

		assertEquals(1, adventurer.getX());
		assertEquals(2, adventurer.getY());
	}

}
