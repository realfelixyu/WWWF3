package com.wwwf.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wwwf.game.Entity;
import com.wwwf.game.GameWorld;
import com.wwwf.game.TeleInfo;
import com.wwwf.game.Utils;
import com.wwwf.game.client.Animation2;
import com.wwwf.game.client.AnimationLoader;

import java.util.ArrayList;

public class ClientScreen implements Screen {
    GameWorld world;
    SpriteBatch batch;
    OrthographicCamera cam;
    float camWorldWidth = 10;
    float viewAspRatio;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    ShapeRenderer shapeRenderer;
    float time = 0f;

    //UI stuff
    public float widthPixels, heightPixels;
    protected Vector2 camDir;
    protected float camSpeed = 5.0f;
    protected ArrayList<Entity> selectedEntities;
    //input stuff
    private ClientInputProcessor inputProcessor;
    private InputMultiplexer inputMultiplexer;
    //for pan feature
    public Rectangle selectRect;
    public Vector3 fixedCoord = null;
    public Box2DDebugRenderer debugRenderer;

    public ClientScreen(GameWorld world) {
        AnimationLoader.loadAnimations();
        this.world = world;
        viewAspRatio = ((float) Gdx.graphics.getHeight()) / Gdx.graphics.getWidth();
        cam = new OrthographicCamera(camWorldWidth, camWorldWidth * viewAspRatio);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(world.map.map, world.map.unitScale);
        Utils.message(0, world, TeleInfo.SPAWN_UNIT, new TeleInfo.SpawnUnit(Entity.Type.SCOUT, 1, 1));
        Utils.message(1, world, TeleInfo.SPAWN_UNIT, new TeleInfo.SpawnUnit(Entity.Type.SCOUT, 2, 1));

        selectedEntities = new ArrayList<>();
        widthPixels = Gdx.graphics.getWidth();
        heightPixels = Gdx.graphics.getHeight();
        inputMultiplexer = new InputMultiplexer();
    }
    private void update() {
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
            Animation2 a = AnimationLoader.getAnimation(e.type, e.action);
            TextureRegion[] r = a.getKeyframe(time);
            batch.draw(r[0], e.pivotPos.x - a.getPivotRatio().x, e.pivotPos.y, e.baseWidth, e.baseHeight);
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
