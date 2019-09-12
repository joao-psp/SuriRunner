package com.cefetmg.surirunner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SuriRunner extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        private Meerkat timon;

	
	@Override
	public void create () {
            batch = new SpriteBatch();
            timon = new Meerkat(new Texture("timon_jump_sprite4.png"));       
            Gdx.gl.glClearColor(1, 1, 1, 1);        

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(Gdx.graphics.getDeltaTime());
                batch.begin();
                timon.render(batch);
 		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
        
        public void update(float delta) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                Gdx.app.exit();
            } 
            timon.update();
        }
}
