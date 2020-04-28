package com.wwwf.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TopHud implements Disposable {

    private ClientScreen player;
    private Stage hudStage;
    private Viewport viewport;

    //state variables
    private Integer secondTimer;
    private Integer minuteTimer;
    private int gold;
    //the population number for the amount of units this player has currently
    private int popCount;

    //Specific UI components
    Table table;
    Label goldLabel;
    Label worldTimerLabel;
    Label popCountLabel;

    public TopHud(ClientScreen player) {
        this.player = player;
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        hudStage = new Stage(viewport);

        popCount = 0;
        secondTimer = 0;
        minuteTimer = 0;
        //gold = player.getGold();

        //can pass in skins in label constructor in the future
        worldTimerLabel = new Label(String.format("%02d", minuteTimer) + ":" +String.format("%02d", secondTimer), new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        goldLabel = new Label("Gold" + String.format("%4d", gold), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        popCountLabel = new Label("Pop:" + String.format("%3d", popCount), new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        table = new Table();
        table.top();
        table.setFillParent(true);
        table.add(goldLabel).expandX().padTop(10);
        table.add(worldTimerLabel).expandX().padTop(10);
        table.add(popCountLabel).expandX().padTop(10);

        hudStage.addActor(table);
    }

    public Stage getHudStage() {
        return hudStage;
    }

    public void update(int worldSeconds, int worldMinutes) {
        worldTimerLabel.setText(String.format("%02d", worldMinutes) + ":" + String.format("%02d", worldSeconds));
    }

    @Override
    public void dispose() {

    }
}
