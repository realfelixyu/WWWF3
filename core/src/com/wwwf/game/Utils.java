package com.wwwf.game;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;

public class Utils {
    public static final Vector2 UP = new Vector2(0,1);
    public static final Vector2 DOWN = new Vector2(0,-1);
    public static final Vector2 LEFT = new Vector2(-1,0);
    public static final Vector2 RIGHT = new Vector2(1,0);
    public static final Vector2 ZERO = new Vector2(0,0 );
    public static final float UPS = 1/60f;

    public static void message(int delay, Telegraph recipient, int type, Object extraInfo) {
        MessageManager.getInstance().dispatchMessage(delay, null, recipient, type, extraInfo);
    }
}
