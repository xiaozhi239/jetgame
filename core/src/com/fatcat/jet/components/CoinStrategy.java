package com.fatcat.jet.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.fatcat.jet.utils.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CoinStrategy {

    private static final int COIN_INTERVAL = 100;
    private static final int COIN_SPEED = 6;

    private List<Location> locations;
    private List<Rectangle> coinRects = new ArrayList<>();
    private Texture coin;
    private int pace;
    private Random random;

    public void init() {
        coin = new Texture("coin.png");
        locations = new ArrayList<>();
        pace = 0;
        random = new Random();
    }

    public void play(SpriteBatch batch) {
        if (pace < COIN_INTERVAL) {
            pace++;
        } else {
            pace = 0;
            makeCoin();
        }
        draw(batch);
    }

    private boolean draw(SpriteBatch batch) {
        coinRects.clear();

        Set<Location> removed = new HashSet<>();
        for (Location location : locations) {
            batch.draw(coin, location.getX(), location.getY());
            coinRects.add(new Rectangle(
                    location.getX(), location.getY(), coin.getWidth(), coin.getHeight()));
            int newX = location.getX() - COIN_SPEED;
            if (newX > -coin.getWidth()) {
                location.update(newX, location.getY());
            } else {
                removed.add(location);
            }
//            if (Intersector.overlaps(manRect, bombRects.get(i))) {
//                Gdx.app.log("Bomb!", "Hit one bomb");
//                gameState = GameState.INACTIVE;
//            }
        }
        locations.removeAll(removed);
        return true;
    }

    private void makeCoin() {
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        locations.add(new Location(Gdx.graphics.getWidth(), (int)height));
    }
}
