package com.wwwf.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.wwwf.game.Entity;
import com.wwwf.game.Utils;

public class ClientInputProcessor extends InputAdapter {
    ClientScreen screen;
    static int sideScrollDist = 50;
    static float zoomSpeed = 4f;
    static float zoomPoints = 0;


    ClientInputProcessor(ClientScreen screen) {
        this.screen = screen;
    }

    public boolean mouseMoved(int x, int y) {
        Vector2 dir = new Vector2(0,0);
        if (x < sideScrollDist && x > 0) {
            dir.add(Utils.LEFT);
        } else if( x > Gdx.graphics.getWidth() - sideScrollDist && x < Gdx.graphics.getWidth()) {
            dir.add(Utils.RIGHT);
        }
        if ( y < sideScrollDist && y > 0){
            dir.add(Utils.UP);
        } else if ( y > Gdx.graphics.getHeight() - sideScrollDist && y < Gdx.graphics.getHeight()) {
            dir.add(Utils.DOWN);
        }
        dir.scl(dir.len());
        screen.camDir = dir;
        return false;
    }
    public boolean touchDown (int x, int y, int pointer, int button) {
        Vector3 worldCoord = screen.cam.unproject(new Vector3(x,y,0));
        if (button == Input.Buttons.LEFT) {
            for (Entity e : screen.world.ents) {
                if (ClientUtils.hitbox(e).contains(worldCoord.x, worldCoord.y)) {
                    screen.selectedEntities.clear();
                    screen.selectedEntities.add(e);
                    break;
                } else {
                    screen.selectedEntities.clear();
                }
            }
        }
        if (button == Input.Buttons.RIGHT) {
            return true;
        }
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

    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        screen.selectRectFixCoord = null;
        return false;
    }
}
