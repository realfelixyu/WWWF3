package com.wwwf.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.sched.LoadBalancingScheduler;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.IntMap;

import java.util.ArrayList;
import java.util.List;

/** Hold all the information related to the simulation. Can receive messages. Entities must be added using addEntity()
 *  since a HashMap is also maintained to look up entities by id. */

public class GameWorld implements Telegraph {
    public World physicsWorld;
    public Map map;
    public Array<Entity> ents;
    public IntMap<Entity> entById;
    public int idTicker;
    private float timeCount;
    AStar aStar;
    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case TeleInfo.SPAWN_UNIT:
                TeleInfo.SpawnUnit info = (TeleInfo.SpawnUnit) msg.extraInfo;
                addEntity(new Entity(info.type, info.x, info.y, idTicker,  info.playerId, this));
                idTicker++;
                break;
            case TeleInfo.FIND_PATH:
                break;
            case TeleInfo.MOVE_TO:
                TeleInfo.MoveUnit move = (TeleInfo.MoveUnit) msg.extraInfo;
                if (!entById.containsKey(move.id)) {
                    Gdx.app.log("Invalid Arguments", "Unit with this id does not exist");
                    return true;
                }
                Entity e = entById.get(move.id);
                Array<Vector2> path = findPathFromTo(e.pivotPos, new Vector2(move.x, move.y));
                e.dispatcher.dispatchMessage(TeleInfo.MOVE_ALONG, path);
                break;

        }
        return false;
    }

    GameWorld() {
        physicsWorld = new World(new Vector2(0, 0f), true);
        map = new Map("maps/bigfield/bigfield.tmx");
        ents = new Array<>();
        idTicker = 1;
        timeCount = 0;
        aStar = new AStar(map.mapWidthInTiles, map.mapHeightInTiles, new Node(0,0), new Node(0,0));
        entById = new IntMap<>();
    }
    public void update() {
        physicsWorld.step(Utils.UPS, 6, 2);
        for (int i = 0; i < ents.size; i++) {
            Entity e = ents.get(i);
            e.dispatcher.update();
            for (int c = 0; c < e.comps.size; c++) {
                e.comps.get(c).update(Utils.UPS);
            }
        }
        timeCount += Utils.UPS;
    }


    public void addEntity(Entity e) {
        ents.add(e);
        entById.put(e.id, e);
    }

    public Array<Vector2> findPathFromTo(Vector2 from, Vector2 to) {

        aStar = new AStar(map.mapWidthInTiles * Utils.NODE_DENSITY,
                map.mapHeightInTiles * Utils.NODE_DENSITY, nodeFromVector2(from),
                nodeFromVector2(to));
        List<Node> path = aStar.findPath();
        Array<Vector2> a = new Array<Vector2>(path.size());
        for (Node n : path) {
            Vector2 v = new Vector2(n.getRow() * map.metersPerTile, n.getCol() * map.metersPerTile);
            v.scl(1/(float)Utils.NODE_DENSITY);
            a.add(v);
        }
        return a;
    }
    private Node nodeFromVector2(Vector2 v) {
        int x = (int) Math.min( map.mapWidthInTiles, Math.max(0 ,( v.x / (map.metersPerTile )* Utils.NODE_DENSITY)));
        int y = (int) Math.min( map.mapHeightInTiles, Math.max( 0  ,( v.y / (map.metersPerTile )* Utils.NODE_DENSITY)));
        return new Node(x, y);
    }


    public float getTime() {
        return timeCount;
    }
}
