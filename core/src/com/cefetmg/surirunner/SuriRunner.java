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
        private Texture bkg;

        
        private Rectangle area;
        private ShapeRenderer shapeRenderer;
        private Array<Obstacles> obstacles;
        private static final int MAX_OBS = 12;
	
	@Override
	public void create () {
            batch = new SpriteBatch();
            timon = new Meerkat();  
            bkg = new Texture("background.png");
            Gdx.gl.glClearColor(1, 1, 1, 1);  
            shapeRenderer = new ShapeRenderer();
            
            obstacles = new Array<>();
            area = new Rectangle(0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            
            for (int i = 0; i < MAX_OBS; i++) {
                obstacles.add(new Obstacles(area));
            }
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(Gdx.graphics.getDeltaTime());
                batch.begin();
                batch.draw(bkg, 0, 0);
                timon.render(batch);
                for (Obstacles obs : obstacles) {
                    obs.render(batch);
                }
                batch.end();
                
                if (Config.debug){
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    timon.render(shapeRenderer);
                    for (Obstacles obs : obstacles) {
                        obs.render(shapeRenderer);
                    }
                    shapeRenderer.end();
                }
                
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
            if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                Config.debug = !Config.debug;
            }   
            timon.update();
            for (Obstacles obs : obstacles) {
                obs.update(delta);
                if (obs.isOutOfBounds(area)) {
                    obs.recycle(area.height);
                }
                if (obs.collidesWith(timon)) {
                    obs.recycle(area.height);
                }
            }
        }
}
