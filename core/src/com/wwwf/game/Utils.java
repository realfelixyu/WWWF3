package com.wwwf.game;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegraph;

public class Utils {

    public static void message(int delay, Telegraph recipient, int type, Object extraInfo) {
        MessageManager.getInstance().dispatchMessage(delay, null, recipient, type, extraInfo);
    }
}
