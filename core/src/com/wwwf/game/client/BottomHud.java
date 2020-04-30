package com.wwwf.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wwwf.game.GameWorld;

import java.util.HashSet;

public class BottomHud implements Disposable {
    public Player player;
    public GameWorld world;
    public ClientScreen screen;
    private boolean selected;
    public Rectangle hitbox;

    //rendering setup
    public Stage hudStage;
    private Viewport viewport;

    //specific setup for overworld Hud
    private Table containerTable;
    private Table centerTable;
    private Table leftTable;
    private Table rightTable;

    //testing buttons
    ImageButton[][] buttonListRight;

    //input processor
    HudInputProcessor inputProcessor;

    //texture stuff
    TextureAtlas buttonAtlas;
    TextureRegion testUnitUpR;
    TextureRegion testunitDownR;
    ImageButton.ImageButtonStyle testStyle;
    ImageButton.ImageButtonStyle testStyle2;

    public BottomHud(GameWorld world, Player player, ClientScreen screen) {
        this.player = player;
        this.world = world;
        this.screen = screen;
        selected = false;

        //processor
        inputProcessor = new HudInputProcessor(this);

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        hudStage = new Stage(viewport);

        Label testLabel = new Label("Testing", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));

        Container<Table> testContainerTable = new Container<>();
        testContainerTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 3 + 20);
        testContainerTable.fillX();
        containerTable = new Table();
        containerTable.setFillParent(true);
        //containerTable.defaults().pad(10F);
        containerTable.bottom();
        Texture backgroundTexture = new Texture(Gdx.files.internal("hud/paperhud.png"));
        TextureRegionDrawable backgroundRegion = new TextureRegionDrawable(backgroundTexture);
        containerTable.background(backgroundRegion);

        centerTable = new Table();
        centerTable.bottom();
        rightTable = new Table();
        leftTable = new Table();

        //atlas stuff
        configureRightTable(rightTable);
        configureCenterTable(centerTable);

        containerTable.add(leftTable);
        containerTable.add(centerTable);
        containerTable.add(rightTable).align(Align.right);
        containerTable.setDebug(true);
        testContainerTable.setActor(containerTable);
        hudStage.addActor(testContainerTable);
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
        //rightTable.setScaleX(1.5f);
        buttonListRight[0][0].addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                System.out.println("clicked a hud button");
            }
        });
    }

    public void configureCenterTable(Table centerTable) {

    }

    public void configureLeftTable(Table leftTable) {

    }

    public void update() {
        if (!screen.selectedEntities.isEmpty()) {
            selected = true;
        } else {
            selected = false;
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

    @Override
    public void dispose() {
        hudStage.dispose();
    }
}
