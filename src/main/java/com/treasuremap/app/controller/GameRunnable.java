package com.treasuremap.app.controller;

import java.util.Timer;
import java.util.TimerTask;
import com.treasuremap.app.App;
import com.treasuremap.app.model.Adventurer;
import lombok.Getter;
import lombok.Setter;

/**
 * GameThread
 */
public class GameRunnable implements Runnable {
	@Getter @Setter private Game game;
	@Getter @Setter private Adventurer adventurer;

	public GameRunnable(Game game, Adventurer adventurer) {
		this.game = game;
		this.adventurer = adventurer;
	}

	@Override
	public void run() {
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				game.executeAdventurerPath(adventurer);

				if (adventurer.getPointer() >= adventurer.getPath().length()) {
					timer.cancel();
				}
			}
		}, 0, 1000);
	}
}
