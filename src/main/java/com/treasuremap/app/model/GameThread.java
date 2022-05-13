package com.treasuremap.app.model;

import com.treasuremap.app.controller.Game;

import lombok.Getter;
import lombok.Setter;

/**
 * GameThread
 */
public class GameThread implements Runnable {
	@Getter @Setter private Game game;
	@Getter @Setter private Adventurer adventurer;

	public GameThread(Game game, Adventurer adventurer) {
		this.game = game;
		this.adventurer = adventurer;
	}

	@Override
	public void run() {
		game.executeAdventurerPath(adventurer);
	}
}
