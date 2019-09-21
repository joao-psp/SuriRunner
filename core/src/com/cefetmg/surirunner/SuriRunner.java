package com.cefetmg.surirunner;

import com.cefetmg.surirruner.movement.Target;
import com.cefetmg.surirruner.movement.behavior.Arrive;
import com.cefetmg.surirruner.movement.behavior.Flee;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class SuriRunner extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture img;
    private Meerkat timon;
    private Texture bkg;


    private Rectangle area;
    private ShapeRenderer shapeRenderer;
    private Array<Obstacles> obstacles;
    private static final int MAX_OBS = 12;

    private Arrive chegar;
    private Flee fugir;

    private float lastDT = (float) 0.0;
    private float nowDT = (float) 0.0;
    private float bckDT = (float) 0.0;
    private boolean setMode = false;

    private Music music;
    private int musicCount = 0;
    private BitmapFont score ;
    private BitmapFont lifes ;
    private int placar = 0;
    private String msg = "Placar: 0";
    private int lifeCount = 3;
    private String lifeMsg = "Vidas: 3";
    
    private boolean pausedState = false;


    @Override
    public void create () {
        batch = new SpriteBatch();
        timon = new Meerkat();  
        bkg = new Texture("background.png");
        Gdx.gl.glClearColor(1, 1, 1, 1);  
        shapeRenderer = new ShapeRenderer();
        score = new BitmapFont();
        lifes = new BitmapFont();

        obstacles = new Array<>();
        area = new Rectangle(0, 0,
            Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for (int i = 0; i < MAX_OBS; i++) {
            obstacles.add(new Obstacles(area, null));
        }
        
        setRandomMusic();
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
        score.setColor(Color.RED);
        lifes.setColor(score.getColor());
        score.draw(batch, msg, 30, Gdx.graphics.getHeight()-4);
        lifes.draw(batch, lifeMsg, 150, Gdx.graphics.getHeight()-4);
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
        music.dispose();
    }

    public void update(float delta) {
        nowDT += Gdx.graphics.getRawDeltaTime();
        verifyIfStartBackgroundMode(nowDT);
        
        if(!pausedState) {
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
                    switch(obs.getObType()) {
                    case INVENCIBLE_UPGRADE:
                        lastDT = nowDT;
                        setMode = true;
                        break;
                    case NOT_ENEMIES:
                        placar += MathUtils.random(4,10);
                        break;
                    case HEART:
                        lifeCount++;
                        break;
                    case ENEMIES:
                        lifeCount--;
                        break;
                }
                    obs.recycle(area.height);
                }
            }
            managerInvencibleMode();
            setScore();        
            if(lifeCount == 0) {
                createNewGame();
            }
            Gdx.graphics.setTitle("SuriRunner (Em Andamento)");
        }else {
            Gdx.graphics.setTitle("SuriRunner (Pausado)");
        }
        
    }

    private void setNormalMode() {
        chegar = new Arrive(100);
        chegar.alvo = new Target(new Vector3(timon.getSprite().getX(),timon.getSprite().getY(),0));
        fugir = new Flee(100);
        fugir.alvo = chegar.alvo;
        for (Obstacles obs : obstacles) {
            obs.setComportament(null);
        }
}

    private void setInvencibleMode() {
        chegar = new Arrive(300);
        chegar.alvo = new Target(new Vector3(timon.getSprite().getX(),timon.getSprite().getY(), 0));
        fugir = new Flee(150);
        fugir.alvo = chegar.alvo;
        for (Obstacles obs : obstacles) {
            switch(obs.getObType()) {
                case ENEMIES:
                    if (obs.pose.position.x < timon.getSprite().getX() + 250){
                        obs.setComportament(fugir);
                    }
                    break;
                default:
                     obs.setComportament(chegar);
            }
        }
    }

    private void createNewGame() {
        placar = 0;
        lifeCount = 3;
        create();
    }

    private void managerInvencibleMode() {
        if((int)lastDT == (int)(nowDT - 6)){
            setNormalMode();
            setMode = false;
        }
        if(setMode){
            setInvencibleMode();
        }
    }

    private void setScore() {
        msg = "Placar: " + placar;
        lifeMsg = "Vidas: "+ lifeCount;
    }

    private void verifyIfStartBackgroundMode(float dt) {
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN)
                || Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bckDT = dt;
            pausedState = false;
            if(!music.isPlaying()) {
                music.play();
            }
        }
        if((nowDT - bckDT) > 10) {
            pausedState = true;
            music.pause();
        }
    }

    private void setRandomMusic() {
        int aux = MathUtils.random(0,1);
        
        if (music != null)
        if (music.isPlaying()) {
            music.stop();
        }

        switch (aux) {
            case 0:
                music = Gdx.audio.newMusic(Gdx.files.internal("hakuna_matata.mp3"));
                music.setVolume(0.8f);
                musicCount = 0;
                break;
            case 1:
                music = Gdx.audio.newMusic(Gdx.files.internal("lion_sleep.mp3"));
                music.setVolume(0.35f);
                musicCount = 1;
                break;
        }
       
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                if (musicCount == 0) {
                    music = Gdx.audio.newMusic(Gdx.files.internal("lion_sleep.mp3"));
                    musicCount = 1;
                } else {
                    music = Gdx.audio.newMusic(Gdx.files.internal("hakuna_matata.mp3"));
                    musicCount = 0;
                }
                music.play();
            }
        });
        music.stop();
        music.play();
    }
}
