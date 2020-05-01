package com.wwwf.game;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PhysicsComponent implements Component{
    Entity master;
    Body body;
    float maxSpeed = 0.8f;
    PhysicsComponent(Entity master, Shape shape, BodyDef.BodyType bodyType, World physicsWorld) {
        this.master = master;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(master.pivotPos.x, master.pivotPos.y);
        body = physicsWorld.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        body.createFixture(fixtureDef);

    }
    public void update(float dt) {
        if (body != null) {
            master.pivotPos = body.getPosition().cpy();
            master.vel = body.getLinearVelocity().cpy();
            Vector2 vel = body.getLinearVelocity();
            if(vel.len() > maxSpeed) {
                body.setLinearVelocity(body.getLinearVelocity().nor().scl(maxSpeed));
            }
            if (vel.len() > 0) {
                if (Math.abs(vel.y) > Math.abs(vel.x)){
                    master.setAnimation(null, (vel.y < 0) ? "down" : "up");
                } else{
                    master.setAnimation(null, (vel.x < 0) ? "left" : "right");
                }
            }
        }
    }
    public void free() {
        body.getWorld().destroyBody(body);
        body = null;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case TeleInfo.APPLY_FORCE:
                Vector2 force = (Vector2) msg.extraInfo;
                body.applyForceToCenter(force, true);
                break;
                case TeleInfo.SET_VELOCITY:
                    Vector2 vel = (Vector2) msg.extraInfo;
                    body.setLinearVelocity(vel);
                    break;
        }
        return false;
    }

    @Override
    public int[] teleCodes() {
        return new int[]{TeleInfo.APPLY_FORCE, TeleInfo.SET_VELOCITY};
    }
}
