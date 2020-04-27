package com.wwwf.game;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.msg.MessageManager;

import java.util.Timer;
import java.util.TimerTask;

/** Represents the simulation aspect of the game. Everything in server or used by server should only have information
 * about simulation state and NOT client information. For example unit positions, widths, heights can be fields of
 * objects on the server, however the animations should not since they are solely used to render (which should be
 * handled by the client.
 */
public class Server {
    GameWorld world;
    Server() {
        world = new GameWorld();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GdxAI.getTimepiece().update(1/60f);
                MessageManager.getInstance().update();
                world.update();
            }
        }, 0, 16);
    }
}
