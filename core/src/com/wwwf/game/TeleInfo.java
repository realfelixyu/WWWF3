package com.wwwf.game;

import com.badlogic.gdx.math.Vector2;
import org.graalvm.compiler.lir.sparc.SPARCMove;

/** Contains codes for telegraph values and structs to send information using the messaging system. */
public class TeleInfo {

    public static final int
    /**     message id              extraInfo */
            SPAWN_UNIT = 1,         //SpawnUnit
            MOVE_UNITS = 2,
            MOVE_TO = 3,
            FIND_PATH = 4,
            MOVE_ALONG = 5,         //Array<Vector2> path
            HALT_UNIT = 6,
            SET_VELOCITY = 7,       //Vector2 vel
            APPLY_FORCE = 8;        //Vector2 force



    public static class SpawnUnit {
        public Entity.Type type;
        public float x;
        public float y;
        public int playerId;

        public SpawnUnit(Entity.Type type, float x, float y, int playerId) {
            this.type = type;
            this.playerId = playerId;
            this.x = x;
            this.y = y;
        }
    }
    public static class MoveUnit {
        int id;
        float x;
        float y;
        public MoveUnit(int id, float x, float y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

}
