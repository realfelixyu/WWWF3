package com.wwwf.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.sched.LoadBalancingScheduler;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

import java.util.ArrayList;

/** Hold all the information related to the simulation. Can receive messages. */

public class GameWorld implements Telegraph {
    public World physicsWorld;
    public Map map;
    public Array<Entity> ents;
    public int idTicker;
    private float timeCount;

    GameWorld() {
        physicsWorld = new World(new Vector2(0, 0f), true);
        map = new Map("maps/bigfield/bigfield.tmx");
        ents = new Array<>();
        idTicker = 1;
        timeCount = 0;
    }
    public void update() {
        physicsWorld.step(Utils.UPS, 6, 2);
        for (int i = 0; i < ents.size; i++) {
            Entity e = ents.get(i);
            for (int c = 0; c < e.comps.size; c++) {
                e.comps.get(c).update(Utils.UPS);
            }
        }
        timeCount += Utils.UPS;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case TeleInfo.SPAWN_UNIT:
                TeleInfo.SpawnUnit info = (TeleInfo.SpawnUnit) msg.extraInfo;
                ents.add(new Entity(info.type, info.x, info.y, idTicker,  info.playerId, this));
                idTicker++;
                break;
                case TeleInfo.FIND_PATH:
                    new Runnable() {
                        @Override
                        public void run() {

                        }
                    };
                    break;

        }
        return false;
    }

    public float getTime() {
        return timeCount;
    }
}
