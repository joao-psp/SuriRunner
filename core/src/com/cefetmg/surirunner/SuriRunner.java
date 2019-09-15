package com.cefetmg.surirunner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class SuriRunner extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        private Meerkat timon;
        
        private Rectangle area;
        private ShapeRenderer shapeRenderer;
        private Array<Obstacles> obstacles;
        private static final int MAX_OBS = 10;


	
	@Override
	public void create () {
            batch = new SpriteBatch();
            timon = new Meerkat();       
            Gdx.gl.glClearColor(1, 1, 1, 1);  
            shapeRenderer = new ShapeRenderer();
            
            obstacles = new Array<Obstacles>();
            area = new Rectangle(0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
        );
            for (int i = 0; i < MAX_OBS; i++) {
                obstacles.add(new Obstacles(ObstaclesEnum.ENEMIES, area));
            }
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(Gdx.graphics.getDeltaTime());
                batch.begin();
                timon.render(batch);
 		batch.end();
                
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                timon.render(shapeRenderer);
                for (Obstacles obs : obstacles) {
                    obs.render(shapeRenderer);
                }
                shapeRenderer.end();
                }
	
	@Override
	public void dispose () {
		batch.dispose();
                shapeRenderer.dispose();
	}
        
        public void update(float delta) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                Gdx.app.exit();
            } 
            timon.update();
            
                   // para todos os asteróides
            for (Obstacles obs : obstacles) {
                obs.update(delta);

                // se tiver saído da tela, recicla-o
                if (obs.isOutOfBounds(area)) {
                    obs.recycle(area.height);
                }

                // verifica se colidiu com nave do jogador e o recicla
                if (obs.collidesWith(timon)) {
                    obs.recycle(area.height);
                }
            }
        }
}
