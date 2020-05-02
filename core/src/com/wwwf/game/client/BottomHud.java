package com.wwwf.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wwwf.game.GameWorld;

public class BottomHud implements Disposable {
    public static int height = Gdx.graphics.getHeight()/ 3 + 20;
    public static int width = Gdx.graphics.getWidth();
    public Player player;
    public GameWorld world;
    public ClientScreen screen;
    private boolean selected;

    public Stage hudStage;
    private Viewport viewport;

    private Table containerTable;
    private Table centerTable;
    private Table leftTable;
    private Table rightTable;

    ImageButton[][] buttonListRight;

    HudInputProcessor inputProcessor;

    TextureAtlas buttonAtlas;
    TextureRegion testUnitUpR;
    TextureRegion testunitDownR;
    ImageButton.ImageButtonStyle testStyle;

    AssetManager assetManager;
    ProgressBar pBar;

    public BottomHud(GameWorld world, Player player, ClientScreen screen) {
        this.player = player;
        this.world = world;
        this.screen = screen;
        selected = false;
        assetManager = new AssetManager();
        loadAsset(assetManager);

        inputProcessor = new HudInputProcessor(this);

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        hudStage = new Stage(viewport);

        containerTable = new Table();
        containerTable.align(Align.bottomRight);
        containerTable.setSize(width, height);
        Texture backgroundTexture = new Texture(Gdx.files.internal("hud/paperhud.png"));
        TextureRegionDrawable backgroundRegion = new TextureRegionDrawable(backgroundTexture);
        containerTable.background(backgroundRegion);

        centerTable = new Table();
        centerTable.bottom();
        rightTable = new Table();
        leftTable = new Table();

        containerTable.add(leftTable);
        containerTable.add(centerTable);
        containerTable.add(rightTable);

        configureLeftTable(leftTable);
        configureRightTable(rightTable);
        //configureCenterTable(centerTable);

        containerTable.setDebug(true);
        containerTable.setTouchable(Touchable.disabled);
        hudStage.addActor(containerTable);
    }

    public void configureRightTable(Table rightTable) {
        buttonAtlas = new TextureAtlas("hud/buttontest.txt");
        testUnitUpR = buttonAtlas.findRegion("buttontest_unit1");
        testunitDownR = buttonAtlas.findRegion("buttontest_unit2");

        testStyle = new ImageButton.ImageButtonStyle();
        testStyle.imageUp = new TextureRegionDrawable(testUnitUpR);
        testStyle.imageDown = new TextureRegionDrawable(testunitDownR);
        buttonListRight = new ImageButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonListRight[i][j] = new ImageButton(testStyle);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rightTable.add(buttonListRight[i][j]).width(70).height(50).fillX();
            }
            rightTable.row();
        }
        rightTable.right().padBottom(10);//.expandY().bottom().fill(1f, .25f).right();
        rightTable.setDebug(true);
        rightTable.setTransform(true);
        rightTable.setTouchable(Touchable.childrenOnly);
        buttonListRight[0][0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("button clicked");
            };
        });

    }

    public void configureCenterTable(Table centerTable) {
        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle();
        Texture barBackgroundT = assetManager.get("hud/bar_background.png", Texture.class);
        TextureRegionDrawable barBackgroundTRD = new TextureRegionDrawable(barBackgroundT);
        Texture barFillT = assetManager.get("hud/bar_fill.png", Texture.class);
        TextureRegionDrawable barFillTRD = new TextureRegionDrawable(barFillT);
        barStyle.background = barBackgroundTRD;
        barStyle.knobAfter = getColoredDrawable(0, height, Color.GREEN);
        pBar = new ProgressBar(0,5, 1,true, barStyle);
        //pBar.pack();
        //pBar.setSize(50, 50);
        centerTable.add(pBar).width(Value.percentWidth(0.1f)).height(height/ 10).growX().fillX();
    }

    public static Drawable getColoredDrawable(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

        pixmap.dispose();

        return drawable;
    }

    public void configureLeftTable(Table leftTable) {
        Image testPortrait = new Image(assetManager.get("hud/portrait_unit1.png", Texture.class));
        leftTable.add(testPortrait);
    }

    public void update() {
        if (!screen.selectedEntities.isEmpty()) {
            selected = true;
            containerTable.setTouchable(Touchable.childrenOnly);
            //pBar.act(1);
        } else {
            selected = false;
            containerTable.setTouchable(Touchable.disabled);
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean bool) {
        selected = bool;
    }

    public void render() {
        if (selected) {
            hudStage.draw();
        }
    }

    public void loadAsset(AssetManager assetManager) {
        assetManager.load("hud/portrait_unit1.png", Texture.class);
        assetManager.load("hud/bar_background.png", Texture.class);
        assetManager.load("hud/bar_fill.png", Texture.class);
        assetManager.finishLoading();
    }

    @Override
    public void dispose() {
        hudStage.dispose();
    }
}
