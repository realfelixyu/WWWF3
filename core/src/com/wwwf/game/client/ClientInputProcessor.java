package com.wwwf.game.client;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.wwwf.game.Entity;

public class ClientInputProcessor extends InputAdapter {
    ClientScreen client;
    static int sideScrollDist = 10;
    static float zoomSpeed = 4f;
    private int zoomPoints = 0;

    ClientInputProcessor(ClientScreen client){
        super();
        this.client = client;
    }
    public boolean mouseMoved (int x, int y) {
        Vector2 dir = new Vector2(0,0);
        if(x < sideScrollDist) {
            dir.add(Constants.LEFT);
        }else if( x >  client.widthPixels - sideScrollDist) {
            dir.add(Constants.RIGHT);
        }
        if( y < sideScrollDist){
            dir.add(Constants.UP);
        }else if( y > client.heightPixels - sideScrollDist){
            dir.add(Constants.DOWN);
        }
        if(dir.x != 0.0f || dir.y != 0.0f){
            dir.scl(dir.len());
        }
        client.camDir = dir;
        return false;
    }
    public boolean scrolled (int amount) {
        zoomPoints += amount;
        client.cam.zoom = (float)Math.exp(zoomPoints /zoomSpeed);
        return false;
    }
    public boolean touchDown (int x, int y, int pointer, int button) {
        Vector3 worldCoord = client.cam.unproject(new Vector3(x,y,0));
        if (button == Input.Buttons.LEFT) {
            for (Entity ent : client.world.ents) {
                if (ent.hit(worldCoord.x, worldCoord.y)) {
                    client.selectedEntities.add(ent);
                } else {
                    client.selectedEntities.clear();
                }
            }
        }
//        if(button == Input.Buttons.RIGHT) {
//            for (Entity entity : client.selectedEntities) {
//                MessageManager.getInstance().dispatchMessage(0, null,
//                        server.world, Constants.TELEGRAM_MOVETO, new Vector2(worldCoord.x,worldCoord.y), false);
//            }
//        }

        if(client.fixedCoord == null) {
            client.fixedCoord = client.cam.unproject(new Vector3(x, y, 0));
        }
        return false;
    }
    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        client.fixedCoord = null;
        return false;
    }

}

