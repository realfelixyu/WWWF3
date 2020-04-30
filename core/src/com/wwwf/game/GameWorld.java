package com.wwwf.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

import java.util.ArrayList;
import java.util.List;

/** Hold all the information related to the simulation. Can receive messages. */

public class GameWorld implements Telegraph {
    World physicsWorld;
    public Map map;
    public Array<Entity> ents;
    public int idTicker;
    public float timeCount;

    GameWorld() {
        physicsWorld = new World(new Vector2(0, 0.0f), true);
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
        }
        return false;
    }

    public ArrayList<Vector2> findPath(Vector2 orig, Vector2 dest) {
        Node origNode = new Node(getNodeCoordinatesX(orig.x), getNodeCoordinatesY(orig.y));
        Node destNode = new Node(getNodeCoordinatesX(dest.x), getNodeCoordinatesY(dest.y));

        AStar aStar = new AStar( map.mapWidthInTiles, map.mapHeightInTiles,
                origNode, destNode);
        List<Node> path = aStar.findPath();
        ArrayList<Vector2> outPath = new ArrayList<Vector2>();
        for(Node node: path){
            outPath.add(new Vector2(node.getRow() + 0.5f,node.getCol() + 0.5f));
        }
        return outPath;
    }

    private int getNodeCoordinatesX(float x) {
        return (int) Math.min( map.mapWidthInTiles, Math.max(0 ,( x / map.metersPerTile) ));
    }
    private int getNodeCoordinatesY(float y) {
        return (int) Math.min( map.mapHeightInTiles, Math.max( 0  ,( y / map.metersPerTile) ));
    }
}
