package com.wwwf.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class HudInputGestureListener implements GestureDetector.GestureListener {
    BottomHud hud;
    ClientScreen screen;
    private boolean initialSelected;

    public HudInputGestureListener(BottomHud hud, ClientScreen screen) {
        this.hud = hud;
        this.screen = screen;
        initialSelected = false;
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
        if (hud.isSelected()) {
            if (initialSelected) {
                return true;
            } else if (y > Gdx.graphics.getHeight() - BottomHud.height) {
                initialSelected = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        if (hud.isSelected()) {
            initialSelected = false;
            return true;
        }
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
