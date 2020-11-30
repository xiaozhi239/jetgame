package com.fatcat.jet.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.fatcat.jet.utils.Location;

public class HeroStrategy {

    private static final float GRAVITY = 0.5f;
    private static final int ACCELERATE = -15;

    private Texture heroActive;
    private Texture heroDead;
    private Location location;
    private float velocity;
    private Rectangle rect;

    public void init() {
        heroActive = new Texture("helicopter.png");
        heroDead = new Texture("helicopter_grey.png");
        location = new Location(Gdx.graphics.getWidth() / 2 - heroActive.getWidth() / 2,
                (int) (0.25 * Gdx.graphics.getHeight()));
        velocity = 0;
        rect = new Rectangle();
    }

    public Rectangle play(SpriteBatch batch) {
        return draw(batch);
    }

    public void dead(SpriteBatch batch) {
        batch.draw(heroDead, location.getX(), location.getY());
    }

    private Rectangle draw(SpriteBatch batch) {
        if (Gdx.input.justTouched()) {
            velocity = ACCELERATE;
        }
        velocity = velocity + GRAVITY;
        int y = location.getY();
        y -= velocity;
        if (y < 0) {
            y = 0;
        }
        location.update(location.getX(), y);
        batch.draw(heroActive, location.getX(), location.getY());
        rect.set(location.getX(), location.getY(), heroActive.getWidth(), heroActive.getHeight());

        return rect;
    }


}
