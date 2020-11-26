package com.fatcat.jet;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.fatcat.jet.components.BombStrategy;
import com.fatcat.jet.components.CoinStrategy;
import com.fatcat.jet.components.HeroStrategy;
import com.fatcat.jet.components.TriggerHandler;

public class JetGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture background;

	private BombStrategy bombStrategy;
	private CoinStrategy coinStrategy;
	private HeroStrategy heroStrategy;

	private BitmapFont scoreBoard;
	private int score;

	private enum GameState {
		INACTIVE, ACTIVE
	}
	private GameState gameState;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.jpg");
		scoreBoard = new BitmapFont();
		scoreBoard.setColor(Color.WHITE);
		scoreBoard.getData().setScale(10);

		TriggerHandler rewardTrigger = new TriggerHandler() {
			@Override
			public void handle() {
				JetGame.this.score++;
			}
		};

		TriggerHandler punishTrigger = new TriggerHandler() {
			@Override
			public void handle() {
				JetGame.this.gameState = GameState.INACTIVE;
			}
		};

		heroStrategy = new HeroStrategy();
		bombStrategy = new BombStrategy(punishTrigger);
		coinStrategy = new CoinStrategy(rewardTrigger);

		reset();
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == GameState.INACTIVE) {
			heroStrategy.dead(batch);
			if (Gdx.input.justTouched()) {
				reset();
			}
		} else {
			Rectangle hero = heroStrategy.play(batch);
			coinStrategy.play(batch, hero);
			bombStrategy.play(batch, hero);
		}
		scoreBoard.draw(batch, String.valueOf(score), 100, 200);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}

	private void reset() {
		bombStrategy.init();
		coinStrategy.init();
		heroStrategy.init();
		score = 0;
		gameState = GameState.ACTIVE;
	}
}
