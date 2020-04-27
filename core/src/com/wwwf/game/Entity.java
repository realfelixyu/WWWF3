package com.wwwf.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Entity {
    static int idTicker = 0;
    public enum Type{SCOUT};
    public Vector2 pivotPos;
    public float baseHeight;
    public float baseWidth;
    public Type type;
    public String action;
    public int id;

    Entity(Type type, float x, float y) {
        pivotPos = new Vector2(x, y);
        id = idTicker;
        idTicker++;
        this.type = type;
        switch (type) {
            case SCOUT:
                action = "swordattack left";
                baseWidth = 0.5f;
                baseHeight = 0.5f;
                break;
            default:
                Gdx.app.log("ERROR", "Type " + type + " does not exist");
        }
    }

    public boolean hit(float x, float y) {
        return false;
    }
}



