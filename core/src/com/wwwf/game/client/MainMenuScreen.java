package com.wwwf.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wwwf.game.Entry;

public class MainMenuScreen implements Screen {
    private Entry entry;
    private ImageButton playButton;
    private Stage stage;
    private OrthographicCamera cam;
    private Table table;
    private Sound buttonSound;

    private static final int PLAY_BUTTON_WIDTH = 600;
    private static final int PLAY_BUTTON_HEIGHT = 100;
    private static final int PLAY_BUTTON_X = 400;
    private static final int PLAY_BUTTON_Y = 225;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;

    public MainMenuScreen (Entry entry){
        //sound
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("sound/buttonsounds/click3.mp3"));

        //camera stuff
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(100 , h/w * 100);
        cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,1);
        this.entry = entry;
        stage = new Stage(new ScreenViewport(cam));
        stage.getViewport().setCamera(cam);
        stage.getBatch().setProjectionMatrix(cam.combined);
        table = new Table();

        //load texture
        playButtonActive = new Texture("buttontexture/play_active.png");
        playButtonInactive = new Texture("buttontexture/play_inactive.png");
        TextureRegionDrawable pbaDrawable = new TextureRegionDrawable(new TextureRegion(playButtonActive));
        TextureRegionDrawable pbiDrawable = new TextureRegionDrawable(new TextureRegion(playButtonInactive));

        //making image style for playbutton with up, down, checked arguments
        Button.ButtonStyle pbStyle = new Button.ButtonStyle(pbiDrawable, pbaDrawable, pbaDrawable);
        ImageButton.ImageButtonStyle pbImageStyle = new ImageButton.ImageButtonStyle(pbStyle);
        playButton = new ImageButton(pbImageStyle);
        playButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                buttonSound.play(1.0f);
                switchScreen();
            }
        });
        //adding stuff to table
        table.add(playButton).size(PLAY_BUTTON_WIDTH,PLAY_BUTTON_HEIGHT);
        table.setPosition(PLAY_BUTTON_X,PLAY_BUTTON_Y);
        table.row();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }
    public void switchScreen(){
        //TODO
//        Gdx.app.postRunnable(new Runnable() {
//            @Override
//            public void run() {
//                entry.setScreen(new ClientScreen(entry.server.world) {
//                });
//            }
//        });
    }

    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(Gdx.graphics.getGL20().GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}

