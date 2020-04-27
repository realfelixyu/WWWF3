package com.wwwf.game;

import com.badlogic.gdx.math.Vector2;

public class TeleInfo {
    public static final int SPAWN_UNIT = 1;

    public static class SpawnUnit {
        public Entity.Type type;
        public float x;
        public float y;
        public SpawnUnit(Entity.Type type, float x, float y) {
            this.type = type;
            this.x = x;
            this.y = y;
        }
    }

}
