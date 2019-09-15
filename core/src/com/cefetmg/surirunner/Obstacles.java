/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cefetmg.surirunner;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cefetmg.surirunner.collision.Collidable;
import com.cefetmg.surirunner.collision.Collision;

/**
 *
 * @author joaop
 */
public class Obstacles implements Collidable {
    private ObstaclesEnum obType;
    private Texture obTexture;
    private Sprite sprite;
    TextureRegion[][] quadrosDaAnimacao;
    float tempoDaAnimacao;
    private Animation<TextureRegion> move;

    private final Vector2 position;
    private final Rectangle area;
    private float speed;
    private final Rectangle bounds;
    
    private int HEIGTH = 20;
    private int WIDTH = 20;





    public Obstacles(ObstaclesEnum obType, Rectangle area) {
        this.obType = obType;
        this.area = area;
        position = new Vector2();
        bounds = new Rectangle(
                position.x - WIDTH / 2f, position.y - HEIGTH / 2f,
                WIDTH, HEIGTH);
        recycle(2);
    }
    
    public void update(float dt) {
        position.x -= speed * dt;
        bounds.x = position.x - WIDTH/2f;
    }
    
    public void render(ShapeRenderer renderer) {
        renderer.setColor(Color.SLATE);
        renderer.identity();
        renderer.translate(position.x, position.y, 0);
        renderer.rect(-WIDTH/2f, -HEIGTH/2f , WIDTH, HEIGTH);
        renderer.identity();
        renderer.setColor(Color.RED);
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);


    }
    
    final void recycle(float minimumY) {
        position.set(
                MathUtils.random(area.width*0.85f, area.width-70),
                MathUtils.random(2, area.height-20));
        bounds.set(position.x - WIDTH/2f, position.y - HEIGTH/2f, WIDTH, HEIGTH);
        speed = MathUtils.random(36f, 100f);
    }

    @Override
    public boolean collidesWith(Collidable other) {
        if (other instanceof Meerkat) {
            return Collision.rectsOverlap(bounds, other.getMinimumBoundingRectangle());
        } else {
            return false;
        }
    }

    @Override
    public boolean isOutOfBounds(Rectangle area) {
        return !area.overlaps(bounds);
    }

    @Override
    public Rectangle getMinimumBoundingRectangle() {
        return bounds;
    }    
}
