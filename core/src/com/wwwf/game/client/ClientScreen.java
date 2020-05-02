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
import com.badlogic.gdx.utils.Array;
import com.wwwf.game.*;

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
    float time = 0;
    Server server;
    Array<Entity> selectedEntities;

    //work in progress
    public MiniMap minimap;
    public TopHud tophud;
    public BottomHud bottomhud;
    public Player player;
    public static int screenHeight = Gdx.graphics.getHeight();
    public InputMultiplexer inputMultiplexer;

    public ClientScreen(Server server) {
        AnimationLoader.loadAnimations();
        this.world = server.world;
        this.server = server;
        viewAspRatio = ((float) Gdx.graphics.getHeight()) / Gdx.graphics.getWidth();
        cam = new OrthographicCamera(camWorldWidth, camWorldWidth * viewAspRatio);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(world.map.map, world.map.unitScale);
        camDir = new Vector2(0,0);
        selectedEntities = new Array<>();

        player = new Player(1, 2);
        tophud = new TopHud(this);
        bottomhud = new BottomHud(world, player, this);
        //minimap = new MiniMap(world);

        InputProcessor inputProcessor = new ClientInputProcessor(this);
        HudInputGestureListener hudInputGestureListener = new HudInputGestureListener(bottomhud, this);
        HudInputProcessor hudProcessor = new HudInputProcessor(bottomhud);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hudProcessor);
        inputMultiplexer.addProcessor(new GestureDetector(hudInputGestureListener));
        inputMultiplexer.addProcessor(bottomhud.hudStage);
        inputMultiplexer.addProcessor(inputProcessor);
        inputMultiplexer.addProcessor(new GestureDetector(new ClientGestureListener(this)));
        Gdx.input.setInputProcessor(inputMultiplexer);


        Utils.message(0, server, TeleInfo.SPAWN_UNIT, new TeleInfo.SpawnUnit(Entity.Type.SCOUT, 1, 1, 1));
        Utils.message(1, server, TeleInfo.SPAWN_UNIT, new TeleInfo.SpawnUnit(Entity.Type.SCOUT, 2, 1, 2));
    }
    private void update() {
        cam.translate(new Vector2(camDir).scl(camSpeed));
        cam.update();
        time += Gdx.graphics.getDeltaTime();
        tophud.update();
        bottomhud.update();
        if (bottomhud.isSelected()) {
        }
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        for (Entity e : selectedEntities) {
            shapeRenderer.circle(e.pivotPos.x, e.pivotPos.y, e.baseWidth/2, 40);
        }
        shapeRenderer.end();

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for (Entity e : world.ents) {
            Animation2 a = AnimationLoader.getAnimation(e.type, e.anim);
            TextureRegion[] r = a.getKeyframe(time);
            Vector2 pivotRatio = a.getPivotRatio();
            batch.setColor(Color.WHITE);

            batch.draw(r[0], e.pivotPos.x - pivotRatio.x * e.baseWidth,
                    e.pivotPos.y - pivotRatio.y * e.baseHeight, e.baseWidth, e.baseHeight);

            Color c = Player.PLAYER_COLORS[e.playerId];
            c.a = e.health;
            batch.setColor(c);
            batch.draw(r[1], e.pivotPos.x - pivotRatio.x * e.baseWidth,
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
        for (Entity e : world.ents) {
            Rectangle r = ClientUtils.hitbox(e);
            shapeRenderer.rect(r.x, r.y, r.width, r.height);
        }
        if( selectRect != null) {
            shapeRenderer.rect(selectRect.x, selectRect.y, selectRect.width, selectRect.height);
        }
        shapeRenderer.end();

        tophud.getHudStage().draw();
        if (bottomhud.isSelected()) {
            bottomhud.render();
        }
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
