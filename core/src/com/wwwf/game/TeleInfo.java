package com.wwwf.game;

import com.badlogic.gdx.math.Vector2;

public class TeleInfo {
    public static final int SPAWN_UNIT = 1 ,MOVE_UNITS = 2 ,MOVE_TO = 3, FIND_PATH = 4,
            MOVE_ALONG = 5;


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

}
