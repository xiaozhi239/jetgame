package com.fatcat.jet.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
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
    private Texture coin;
    private int pace;
    private Random random;

    private TriggerHandler handler;

    public CoinStrategy(TriggerHandler handler) {
        this.handler = handler;
    }

    public void init() {
        coin = new Texture("coin.png");
        locations = new ArrayList<>();
        pace = 0;
        random = new Random();
    }

    public void play(SpriteBatch batch, Rectangle hero) {
        if (pace < COIN_INTERVAL) {
            pace++;
        } else {
            pace = 0;
            makeCoin();
        }
        draw(batch, hero);
    }

    private void draw(SpriteBatch batch, Rectangle hero) {
        Set<Location> removed = new HashSet<>();
        for (Location location : locations) {
            batch.draw(coin, location.getX(), location.getY());
            Rectangle rectangle = new Rectangle(
                    location.getX(), location.getY(), coin.getWidth(), coin.getHeight());
            if (Intersector.overlaps(hero, rectangle)) {
                removed.add(location);
                handler.handle();
                continue;
            }
            int newX = location.getX() - COIN_SPEED;
            if (newX > -coin.getWidth()) {
                location.update(newX, location.getY());
            } else {
                removed.add(location);  // moved outside of the screen entirely
            }
        }
        locations.removeAll(removed);
    }

    private void makeCoin() {
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        locations.add(new Location(Gdx.graphics.getWidth(), (int)height));
    }
}
