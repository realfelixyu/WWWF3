package com.wwwf.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.Array;

/**  Entities represent units, buildings and potentially more. Entity probably should not store a pointer to the
 * world. Components to entity MUST be added to entity using addComponent. And animations MUST be set using
 * setAnimation(). Feel free to add fields relevant to the simulation here, BUT don't add too many, keep as many
 * things in the components as possible. Messages can be dispatched to the components using dispatcher.dispatchMessage()*/
public class Entity {
    public enum Type{SCOUT};
    public Vector2 pivotPos;
    public float baseWidth;
    public Type type;
    private String action;
    private String direction;
    public String anim;
    public int id;
    public int playerId;
    public float health = 1f;
    public Vector2 vel;
    public Array<Component> comps;
    public MessageDispatcher dispatcher;

    Entity(Type type, float x, float y, int id, int playerId, GameWorld world) {
        this.pivotPos = new Vector2(x, y);
        this.type = type;
        this.comps = new Array<>();
        this.id = id;
        this.playerId = playerId;
        this.comps = new Array<Component>();
        this.vel = Utils.ZERO;
        this.dispatcher = new MessageDispatcher();


        switch (type) {
            case SCOUT:
                setAnimation("atk", "right");
                baseWidth = 0.5f;
                // TODO eventually store shape for each unit type instead of making a new one.
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(baseWidth/6);
                addComponent(new PhysicsComponent(this, circleShape, BodyDef.BodyType.DynamicBody, world.physicsWorld));
                circleShape.dispose();
                addComponent(new BasicMoveComponent(this));
                break;
            default:
                Gdx.app.log("ERROR", "Type " + type + " does not exist");
        }
    }
    public void addComponent(Component component) {
        comps.add(component);
        int[] codes = component.teleCodes();
        for (int i = 0; i < codes.length; i++) {
            dispatcher.addListener(component, codes[i]);
        }

    }

    public void setAnimation(String action, String dir) {
        this.direction = (dir == null) ? this.direction :  dir;
        this.action = (action == null) ? this.action : action;
        anim = this.action + "_" + this.direction ;
    }
}



