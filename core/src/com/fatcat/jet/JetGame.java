package com.fatcat.jet;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fatcat.jet.components.BombStrategy;
import com.fatcat.jet.components.CoinStrategy;

public class JetGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture background;

	private BombStrategy bombStrategy;
	private CoinStrategy coinStrategy;

	private enum GameState {
		INACTIVE, ACTIVE
	}
	private GameState gameState = GameState.INACTIVE;

	private void reset() {
		bombStrategy.init();
		coinStrategy.init();
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.jpg");

		bombStrategy = new BombStrategy();
		coinStrategy = new CoinStrategy();

		reset();
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		coinStrategy.play(batch);
		bombStrategy.play(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
