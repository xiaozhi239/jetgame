package com.fatcat.jet.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.fatcat.jet.utils.Location;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class BombStrategy {

    private static final int BOMB_INTERVAL = 600;
    private static final int BOMB_SPEED = 8;

    private List<Location> locations;
    private Texture bomb;
    private int pace;
    private Random random;

    private TriggerHandler handler;

    public BombStrategy(TriggerHandler handler) {
        this.handler = handler;
    }


    public void init() {
        bomb = new Texture("bomb.png");
        locations = new ArrayList<>();
        pace = 0;
        random = new Random();
    }

    public void play(SpriteBatch batch, Rectangle hero) {
        if (pace < BOMB_INTERVAL) {
            pace++;
        } else {
            pace = 0;
            makeBomb();
        }
        draw(batch, hero);
    }

    private boolean draw(SpriteBatch batch, Rectangle hero) {
        Set<Location> removed = new HashSet<>();
        for (Location location : locations) {
            batch.draw(bomb, location.getX(), location.getY());
            Rectangle rectangle = new Rectangle(
                    location.getX(), location.getY(), bomb.getWidth(), bomb.getHeight());
            int newX = location.getX() - BOMB_SPEED;
            if (newX > -bomb.getWidth()) {
                location.update(newX, location.getY());
            } else {
                removed.add(location);
            }
            if (Intersector.overlaps(hero, rectangle)) {
                handler.handle();
            }
        }
        locations.removeAll(removed);
        return true;
    }

    private void makeBomb() {
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        locations.add(new Location(Gdx.graphics.getWidth(), (int)height));
    }
}
