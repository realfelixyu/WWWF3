package com.wwwf.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wwwf.game.Utils;

public class ClientInputProcessor extends InputAdapter {
    ClientScreen screen;
    static int sideScrollDist = 10;
    static float zoomSpeed = 4f;
    static float zoomPoints = 0;


    ClientInputProcessor(ClientScreen screen) {
        this.screen = screen;
    }

    public boolean mouseMoved(int x, int y) {
        Vector2 dir = new Vector2(0,0);
        if (x < sideScrollDist) {
            dir.add(Utils.LEFT);
        } else if( x > Gdx.graphics.getWidth() - sideScrollDist) {
            dir.add(Utils.RIGHT);
        }
        if ( y < sideScrollDist){
            dir.add(Utils.UP);
        } else if ( y > Gdx.graphics.getHeight() - sideScrollDist) {
            dir.add(Utils.DOWN);
        }
        dir.scl(dir.len());
        screen.camDir = dir;
        return false;
    }
    public boolean touchDown (int x, int y, int pointer, int button) {
        if(screen.selectRectFixCoord == null) {
            screen.selectRectFixCoord = screen.cam.unproject(new Vector3(x, y, 0));
        }
        return false;
    }
    public boolean scrolled (int amount) {
        zoomPoints += amount;
        screen.cam.zoom = (float)Math.exp(zoomPoints / zoomSpeed);
        return false;
    }
}
