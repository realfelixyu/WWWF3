package com.wwwf.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Entity {
    static int idTicker = 0;
    public enum Type{SCOUT};
    public Vector2 pivotPos;
    public float baseHeight;
    public float baseWidth;
    public Type type;
    private String action;
    private String direction;
    public String anim;
    public int id;
    Array<Component> comps;

    Entity(Type type, float x, float y) {
        this.pivotPos = new Vector2(x, y);
        this.id = idTicker++;
        this.type = type;
        this.comps = new Array<>();
        switch (type) {
            case SCOUT:
                setAnimation("swordattack", "right");
                //comps.add();
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

    public void updateComponents() {

    }

    public void setAnimation(String action, String dir) {
        this.direction = (dir == null) ? this.direction :  dir;
        this.action = (action == null) ? this.action : action;
        anim = this.action + " " + this.direction;
    }
}



