package com.treasuremap.app.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.treasuremap.app.model.Adventurer;
import com.treasuremap.app.model.Orientation;
import com.treasuremap.app.model.TreasureMap;

/**
 * GameTest
 */
public class GameTest {

	/**
	 * The initial position of the adventurer is (0,0).
	 * Given the path "AADADAGA" and facing direction EAST, the adventurer should arrive on (2,3).
	 * Be mindful of indexes.
	 *
	 *     1   2   3
	 *   +---+---+---+
	 * 1 | → | → | ↓ |
	 *   +---+---+---+
	 * 2 |   | ↓ | ← |
	 *   +---+---+---+
	 * 3 |   | ↓ |   |
	 *   +---+---+---+
	 *
	 * Testing method {@link Game#executeAdventurerPath(Adventurer)}.
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

		for (char c : adventurer.getPath().toCharArray()) {
			game.executeAdventurerPath(adventurer);
		}

		assertEquals(1, adventurer.getX());
		assertEquals(2, adventurer.getY());
	}

	/**
	 * See definition of link below.
	 *
	 * Testing method {@link Game#hasFinished()}.
	 */
	@Test
	public void game_is_finished_when_adventurer_has_completed_the_path() {
		Adventurer adventurer = new Adventurer();
		adventurer.setPath("AADADAGA");
		adventurer.setOrientation(Orientation.EAST);

		TreasureMap map = new TreasureMap(3, 3);
		map.addAdventurer(adventurer, 0, 0);

		Game game = new Game();
		game.setMap(map);
		game.setAdventurers(Stream.of(adventurer).collect(Collectors.toList()));

		for (char c : adventurer.getPath().toCharArray()) {
			game.executeAdventurerPath(adventurer);
		}

		assertTrue(game.hasFinished());
	}

	/**
	 * In this case, two adventurers are present on the map but only one of them has completed their path.
	 *
	 * Testing method {@link Game#hasFinished()}.
	 */
	public void game_is_not_finished_if_an_adventurer_has_not_completed_their_path() {
		Adventurer john = new Adventurer();
		john.setName("John");
		john.setPath("A");
		john.setOrientation(Orientation.SOUTH);

		Adventurer maria = new Adventurer();
		maria.setName("Maria");
		maria.setPath("AADADAGA");
		maria.setOrientation(Orientation.SOUTH);

		TreasureMap map = new TreasureMap(3, 3);
		map.addAdventurer(john, 0, 0);
		map.addAdventurer(maria, 1, 1);

		Game game = new Game();
		game.setMap(map);
		game.setAdventurers(Stream.of(john, maria).collect(Collectors.toList()));

		for (char c : john.getPath().toCharArray()) {
			game.executeAdventurerPath(john);
		}

		assertFalse(game.hasFinished());
	}
}
