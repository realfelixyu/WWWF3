package com.wwwf.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.Array;

/**  Entities represent units, buildings and potentially more. Enitity probably should not store a pointer to the
 * world.*/
public class Entity {
    public enum Type{SCOUT};
    public Vector2 pivotPos;
    //public float baseHeight;
    public float baseWidth;
    public Type type;
    private String action;
    private String direction;
    public String anim;
    public int id;
    public int playerId;
    public float health = 1f;
    Array<Component> comps;

    Entity(Type type, float x, float y, int id, int playerId, GameWorld world) {
        this.pivotPos = new Vector2(x, y);
        this.type = type;
        this.comps = new Array<>();
        this.id = id;
        this.playerId = playerId;
        comps = new Array<Component>();
        switch (type) {
            case SCOUT:
                setAnimation("atk", "right");
                baseWidth = 0.5f;
                //baseHeight = 0.50f * 1.38f;
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(baseWidth/6);
                comps.add(new PhysicsComponent(this, circleShape, BodyDef.BodyType.DynamicBody, world.physicsWorld));
                circleShape.dispose();
                break;
            default:
                Gdx.app.log("ERROR", "Type " + type + " does not exist");
        }
    }

    public void setAnimation(String action, String dir) {
        this.direction = (dir == null) ? this.direction :  dir;
        this.action = (action == null) ? this.action : action;
        anim = this.action + "_" + this.direction ;
    }
}



