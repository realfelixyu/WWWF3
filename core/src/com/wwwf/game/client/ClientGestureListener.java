package com.wwwf.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.wwwf.game.Entity;

public class ClientGestureListener implements GestureDetector.GestureListener {
    ClientScreen screen;


    public ClientGestureListener(ClientScreen screen){
        this.screen = screen;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        Vector3 currentCoord = screen.cam.unproject(new Vector3(Gdx.input.getX()  ,Gdx.input.getY()  ,0));
        screen.selectRect = new Rectangle(screen.selectRectFixCoord.x, screen.selectRectFixCoord.y, 0,0);
        screen.selectRect.merge(currentCoord.x, currentCoord.y);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        for (Entity e : screen.world.ents) {
            if (screen.selectRect != null && ClientUtils.hitbox(e, screen.time).overlaps(screen.selectRect)) {
                //TODO make it so that only ally units can get pan selected
                screen.selectedEntities.add(e);
            }
        }
        screen.selectRect = null;
        screen.selectRectFixCoord = null;

        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
