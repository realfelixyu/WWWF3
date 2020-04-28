package com.wwwf.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.wwwf.game.Entity;
import com.wwwf.game.GameWorld;
import com.wwwf.game.TeleInfo;
import com.wwwf.game.Utils;

import java.util.ArrayList;

public class ClientScreen implements Screen {
    GameWorld world;
    SpriteBatch batch;
    OrthographicCamera cam;
    Vector2 camDir;
    float camSpeed = 0.1f;
    float camWorldWidth = 10;
    Vector3 selectRectFixCoord = null;
    Rectangle selectRect;
    float viewAspRatio;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    ShapeRenderer shapeRenderer;
    float time = 0f;

    //UI stuff
    protected ArrayList<Entity> selectedEntities;
    //for pan feature
    public Vector3 fixedCoord = null;

    public ClientScreen(GameWorld world) {
        AnimationLoader.loadAnimations();
        this.world = world;
        viewAspRatio = ((float) Gdx.graphics.getHeight()) / Gdx.graphics.getWidth();
        cam = new OrthographicCamera(camWorldWidth, camWorldWidth * viewAspRatio);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(world.map.map, world.map.unitScale);
        camDir = new Vector2(0,0);

        InputProcessor inputProcessor = new ClientInputProcessor(this);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessor);
        inputMultiplexer.addProcessor(new GestureDetector(new ClientGestureListener(this)));
        Gdx.input.setInputProcessor(inputMultiplexer);

        Utils.message(0, world, TeleInfo.SPAWN_UNIT, new TeleInfo.SpawnUnit(Entity.Type.SCOUT, 1, 1));
        Utils.message(1, world, TeleInfo.SPAWN_UNIT, new TeleInfo.SpawnUnit(Entity.Type.SCOUT, 2, 1));

        selectedEntities = new ArrayList<>();
    }
    private void update() {
        cam.translate(new Vector2(camDir).scl(camSpeed));
        cam.update();
        time += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for (Entity e : world.ents) {
            Animation2 a = AnimationLoader.getAnimation(e.type, e.anim);
            TextureRegion[] r = a.getKeyframe(time);
            Vector2 pivotRatio = a.getPivotRatio();
            batch.draw(r[0], e.pivotPos.x - pivotRatio.x * e.baseWidth,
                    e.pivotPos.y - pivotRatio.y * e.baseHeight, e.baseWidth, e.baseHeight);
        }
        batch.end();

        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity e : world.ents) {
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(e.pivotPos.x, e.pivotPos.y, 0.04f, 10);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(e.pivotPos.x, e.pivotPos.y, 0.02f, 10);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(e.pivotPos.x, e.pivotPos.y, 0.01f, 10);

        }
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if( selectRect != null) {
            shapeRenderer.rect(selectRect.x, selectRect.y, selectRect.width, selectRect.height);
        }
        shapeRenderer.end();




    }
    @Override
    public void dispose() {
        batch.dispose();
        tiledMapRenderer.dispose();
    }

    @Override
    public void show() {
    }
    @Override
    public void resize(int width, int height) {
        viewAspRatio = ((float) Gdx.graphics.getHeight()) / Gdx.graphics.getWidth();
        cam = new OrthographicCamera(camWorldWidth, camWorldWidth * viewAspRatio);
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

}
