package com.wwwf.game.client;

import com.badlogic.gdx.graphics.Color;

public class Player {
    public static Color[] PLAYER_COLORS = new Color[]{Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN, Color.BLUE, Color.PURPLE, Color.VIOLET};
    private int id;
    private int gold;

    Player(int id, int gold) {
        this.id = id;
        this.gold = gold;
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public int getGold() {
        return gold;
    }

}
