package com.wwwf.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wwwf.game.Entity;

public class HudInputProcessor extends InputAdapter implements GestureDetector.GestureListener {
    private BottomHud hud;
    private boolean hudPan;

    public HudInputProcessor(BottomHud hud) {
        super();
        this.hud = hud;
        hudPan = false;
    }

    //button is left or right click
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (hud.isSelected()) {
            Vector3 worldCoord = hud.screen.cam.unproject(new Vector3(x, y, 0));
            if (button == Input.Buttons.LEFT) {
                if (hud.hudStage.hit(worldCoord.x, worldCoord.y, true) != null) {
                    //TODO implement buttons
                    System.out.println("something");
                } else {
                    hud.screen.selectedEntities.clear();
                }
            } else if (button == Input.Buttons.RIGHT) {
                for (Entity ent : hud.screen.selectedEntities) {
                    //move units
//                    MessageManager.getInstance().dispatchMessage(0, null,
//                            ent, Constants.TELEGRAM_MOVETO, new Vector2(worldCoord.x,worldCoord.y), false);

                }
            }
            if(hud.screen.selectRectFixCoord == null) {
                hud.screen.selectRectFixCoord = hud.screen.cam.unproject(new Vector3(x, y, 0));
            }
            return true;
        }
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        return false;
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
        System.out.println(y);
        if (y < Gdx.graphics.getHeight() / 3) {
            hudPan = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        if (hudPan) {
            hudPan = false;
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
