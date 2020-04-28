package com.wwwf.game;

import com.badlogic.gdx.physics.box2d.*;

public class PhysicsComponent implements Component{
    Entity master;
    Body body;
    PhysicsComponent(Entity master, Shape shape, BodyDef.BodyType bodyType, World world) {
        this.master = master;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(master.pivotPos.x, master.pivotPos.y);
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.0f;
        body.createFixture(fixtureDef);

    }
    public void update(float dt) {
        if (body != null) {
            master.pivotPos = body.getPosition().cpy();
        }
    }
    public void free() {
        body.getWorld().destroyBody(body);
    }

}
