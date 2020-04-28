package com.wwwf.game.client;

import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static Vector2 UP = new Vector2(0,1);
    public static Vector2 DOWN = new Vector2(0,-1);
    public static Vector2 LEFT = new Vector2(-1,0);
    public static Vector2 RIGHT = new Vector2(1,0);

    //bitwise stuff for collision
    public static final int BIT_PLAYER = 1;
    public static final int BIT_BUILDING = 2;

    public static final float STOPPING_RADIUS = 0.3f;
    public static final float ATTACK_MOVE_COOLDOWN = 2f;

    public static boolean DEBUG_MODE = false;

    public static final int TELEGRAM_MOVETO = 2;
    public static final int TELEGRAM_ATTACK = 3;
    public static final int TELEGRAM_DIE = 4;

}
