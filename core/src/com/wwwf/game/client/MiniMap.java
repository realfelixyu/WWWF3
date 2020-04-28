package com.wwwf.game.client;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.wwwf.game.Entity;
import com.wwwf.game.GameWorld;

public class MiniMap {
    private Pixmap pixmap;
    public Texture texture;
    private GameWorld world;

    MiniMap (GameWorld world) {
        this.world = world;
        pixmap = new Pixmap( 128, 128, Pixmap.Format.RGBA8888 );
        update();


    }
    public void update() {
        pixmap.setColor(Color.FOREST);
        pixmap.fill();
        for (Entity ent : world.ents) {
            //pixmap.setColor();
            pixmap.fillCircle((int) (ent.pivotPos.x / world.map.mapWidthInMeters * pixmap.getWidth()),
                    (int) ((world.map.mapHeightInMeters - ent.pivotPos.y) / world.map.mapHeightInMeters * pixmap.getHeight()), 2);
        }
        if (texture != null) {
            texture.dispose();
        }
        texture = new Texture(pixmap);
    }

    public void draw(SpriteBatch sb) {
        sb.draw(texture, 0, 0, 100, 100);
    }

    public void dispose() {
        texture.dispose();
        pixmap.dispose();
    }
}
