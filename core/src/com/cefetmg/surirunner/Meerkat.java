/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cefetmg.surirunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.cefetmg.surirunner.collision.Collidable;
import com.cefetmg.surirunner.collision.Collision;

/**
 *
 * @author joaop
 */
public class Meerkat implements Collidable{
    private final Texture runningTexture;
    private final Sprite sprite;
    TextureRegion[][] quadrosDaAnimacao;
    float tempoDaAnimacao;
    private final Animation<TextureRegion> move;
    private final int HEIGHT = 40;
    private final int WIDTH = 32;
  
    
    int direction = 1;
    
    public Meerkat() {
        this.runningTexture = new Texture("timon_running_sprite3.png");
        this.sprite = new Sprite(runningTexture);
        this.sprite.setPosition(30, 10);
        this.quadrosDaAnimacao = TextureRegion.split(runningTexture, 39, 48); //39x48 e 39x80
        
        move = new Animation<>(0.075f, new TextureRegion[]{
        		quadrosDaAnimacao[0][0],
        		quadrosDaAnimacao[0][1],
        		quadrosDaAnimacao[0][2],
        		quadrosDaAnimacao[0][3],
        		quadrosDaAnimacao[0][4],
                        quadrosDaAnimacao[0][5],
        		quadrosDaAnimacao[0][6],
                        quadrosDaAnimacao[0][7],
        		quadrosDaAnimacao[0][8],
        });
        
        move.setPlayMode(Animation.PlayMode.LOOP);
    }
    
    public void update() {
        tempoDaAnimacao += Gdx.graphics.getDeltaTime();
        
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (sprite.getY() < Gdx.graphics.getHeight() - HEIGHT ) {
                sprite.setPosition(sprite.getX(), sprite.getY() + 3);
            }       
        }     
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (sprite.getY() > 0) {
                sprite.setPosition(sprite.getX(), sprite.getY() - 3);
            }
        } 
         if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (sprite.getX() > 0) {
                sprite.setPosition(sprite.getX() - 2, sprite.getY());
            }
        }  
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (sprite.getX() < Gdx.graphics.getWidth()/4) {
                sprite.setPosition(sprite.getX() + 2, sprite.getY());
            }
        }
    }
    
    public void render(ShapeRenderer renderer) {
        renderer.identity();
        renderer.setColor(Color.RED);
        renderer.box(sprite.getBoundingRectangle().x, sprite.getBoundingRectangle().y, 0,
                WIDTH, HEIGHT, 0);
        
    }
    
    public void render(Batch batch) {
        
        switch (direction) {
            case 1:
                batch.draw((TextureRegion)move.getKeyFrame(tempoDaAnimacao), sprite.getX(), sprite.getY(), WIDTH, HEIGHT);
                break;
            case 2:
                break;
            default:
                break;
        }
    }   

    @Override
    public boolean collidesWith(Collidable other) {
        if (other instanceof Obstacles) {
            return Collision.rectsOverlap(sprite.getBoundingRectangle(), other.getMinimumBoundingRectangle());
        } else {
            return false;
        }
    }

    @Override
    public boolean isOutOfBounds(Rectangle area) {
        return false;
    }

    @Override
    public Rectangle getMinimumBoundingRectangle() {
        Rectangle rect = new Rectangle(sprite.getBoundingRectangle().x,
        sprite.getBoundingRectangle().y, WIDTH, HEIGHT);
        return rect;
    }
}
