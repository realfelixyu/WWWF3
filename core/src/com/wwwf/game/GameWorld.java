package com.wwwf.game;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/** Hold all the information related to the simulation. Can receive messages. */

public class GameWorld implements Telegraph {
    World physicsWorld;
    public Map map;
    public ArrayList<Entity> ents;

    GameWorld() {
        physicsWorld = new World(new Vector2(0, 0.1f), true);
        map = new Map("maps/bigfield/bigfield.tmx");
        ents = new ArrayList<>();
    }
    public void update() {
        physicsWorld.step(1/60f, 6, 2);
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case TeleInfo.SPAWN_UNIT:
                TeleInfo.SpawnUnit info = (TeleInfo.SpawnUnit) msg.extraInfo;
                ents.add(new Entity(info.type, info.x, info.y));
                break;
        }
        return false;
    }
}
