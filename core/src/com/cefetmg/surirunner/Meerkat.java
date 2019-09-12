/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cefetmg.surirunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author joaop
 */
public class Meerkat {
    private final Texture texture;
    private final Sprite sprite;
    TextureRegion[][] quadrosDaAnimacao;
    float tempoDaAnimacao;
    private Animation<TextureRegion> move;
    
    public Meerkat(Texture texture) {
        this.texture = texture;
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(30, 10);
        this.quadrosDaAnimacao = TextureRegion.split(texture, 39, 65); //39x48 e 39x65
        
        move = new Animation<TextureRegion>(0.1f, new TextureRegion[]{
        		quadrosDaAnimacao[0][0],
        		quadrosDaAnimacao[0][1],
        		quadrosDaAnimacao[0][2],
        		quadrosDaAnimacao[0][3],
        		quadrosDaAnimacao[0][4],
                        quadrosDaAnimacao[0][5],
        		quadrosDaAnimacao[0][6],
                        quadrosDaAnimacao[0][7],
        		//quadrosDaAnimacao[0][8],
        });
        
        move.setPlayMode(Animation.PlayMode.LOOP);
    }
    
    public void update() {
        tempoDaAnimacao += Gdx.graphics.getDeltaTime();
    }
    
    public void render(Batch batch) {
         batch.draw((TextureRegion)move.getKeyFrame(tempoDaAnimacao), sprite.getX(), sprite.getY());
    }

    
}
