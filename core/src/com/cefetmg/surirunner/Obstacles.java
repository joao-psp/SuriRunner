/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cefetmg.surirunner;

import br.cefetmg.games.movement.AlgoritmoMovimentacao;
import br.cefetmg.games.movement.Direcionamento;
import br.cefetmg.games.movement.Pose;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import static com.badlogic.gdx.math.MathUtils.random;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

    private final Vector3 position;
    private final Rectangle area;
    private float speed;
    private final Rectangle bounds;
    
    private final int HEIGTH = 30;
    private final int WIDTH = 30;
    
    public Pose pose;
    private AlgoritmoMovimentacao comportamento;

    public Obstacles(Rectangle area, AlgoritmoMovimentacao comp) {
        this.area = area;
        this.comportamento = comp;
        position = new Vector3();
        this.pose = new Pose(position, 12f);
        bounds = new Rectangle(
                position.x - WIDTH / 2f, position.y - HEIGTH / 2f,
                WIDTH, HEIGTH);

        recycle(2);
    }
    
    public void update(float dt) {
        position.x -= speed * dt;
        bounds.x = position.x - WIDTH/2f;
        tempoDaAnimacao += Gdx.graphics.getDeltaTime();
        
        if (comportamento != null) {
            // pergunta ao algoritmo de movimento (comportamento) 
            // para onde devemos ir
            Direcionamento direcionamento = comportamento.guiar(this.pose);
            // faz a simulação física usando novo estado da entidade cinemática
            pose.atualiza(direcionamento, dt);
            bounds.x = pose.posicao.x; 
            bounds.y = pose.posicao.y; 
        }
    }
    
    public void render(Batch batch) {
        batch.draw((TextureRegion)move.getKeyFrame(tempoDaAnimacao), bounds.x - WIDTH/3, bounds.y-WIDTH/3, WIDTH, HEIGTH);
    }
    
    public void render(ShapeRenderer renderer) {
        renderer.setColor(Color.SLATE);
        renderer.identity();
        renderer.identity();
        renderer.setColor(Color.RED);
        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    final void recycle(float minimumY) {
        this.obType = ObstaclesEnum.getRandom();
        setSprite(obType);
        position.set(
                MathUtils.random(area.width, area.width+10),
                MathUtils.random(0, area.height-HEIGTH), 0);
        sprite.setPosition(position.x, position.y);
        bounds.set(position.x + WIDTH/2f, position.y + HEIGTH/2f, WIDTH/2, HEIGTH/2);
        speed = MathUtils.random(50f, 100f);
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

    private void setSprite(ObstaclesEnum obType) {  
        switch(obType) {
            case ENEMIES:
                loadEnemy();
                break;
            case NOT_ENEMIES:
                loadNotEnemy();
                break;
            case INVENCIBLE_UPGRADE:
                loadUpgrade();
                break;    
            case HEART:
                loadHeart();
                break;
                
        }
    }
    
    private void loadUpgrade() {
        this.obTexture = new Texture("cajado_rafiki3.png");
        this.sprite = new Sprite(obTexture);
        this.quadrosDaAnimacao = TextureRegion.split(obTexture, 60, 60);
        move = new Animation<>(0.1f, new TextureRegion[]{
        		quadrosDaAnimacao[0][0]
        });
        move.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }   
    
    private void loadHeart() {
        this.obTexture = new Texture("heart_img.png");
        this.sprite = new Sprite(obTexture);
        this.quadrosDaAnimacao = TextureRegion.split(obTexture, 640, 640);
        move = new Animation<>(0.1f, new TextureRegion[]{
        		quadrosDaAnimacao[0][0]
        });
        
        move.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }
    
    private void loadEnemy() {
        this.obTexture = new Texture("eagle_spritesheet.png");
        this.sprite = new Sprite(obTexture);
        this.quadrosDaAnimacao = TextureRegion.split(obTexture, 34, 25);
        
        move = new Animation<>(0.1f, new TextureRegion[]{
        		quadrosDaAnimacao[0][0],
        		quadrosDaAnimacao[0][1],
        		quadrosDaAnimacao[0][2],
        });
        
        move.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    private void loadNotEnemy() {
        this.obTexture = new Texture("butterfly_spritesheets.png");
        this.sprite = new Sprite(obTexture);
        this.quadrosDaAnimacao = TextureRegion.split(obTexture, 70, 70); //39x48 e 39x80
        
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
                        quadrosDaAnimacao[0][9],
        		quadrosDaAnimacao[0][10],
        		quadrosDaAnimacao[0][11],
        		quadrosDaAnimacao[0][12],
        		quadrosDaAnimacao[0][13],
        });
        
        move.setPlayMode(Animation.PlayMode.LOOP);
    }
    
    public ObstaclesEnum getObType() {
        return this.obType;
    }
    
    public void setComportament(AlgoritmoMovimentacao alg){
        this.comportamento = alg;
    }
}
