package com.wwwf.game.client;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.wwwf.game.Entity;

public class ClientUtils {
    public static Rectangle hitbox(Entity e, float time) {
        Animation2 a = AnimationLoader.getAnimation(e.type, e.anim);
        TextureRegion r0 = AnimationLoader.getAnimation(e.type, "idle_down").getKeyframe(0)[0];
        TextureRegion[] r = a.getKeyframe(time);
        float animAspectRatioX = (float) r[0].getRegionWidth()/r0.getRegionWidth();
        float animAspectRatioY = (float) r[0].getRegionHeight()/r0.getRegionHeight();
        float baseHeight = (float) r0.getRegionHeight()/r0.getRegionWidth() * e.baseWidth;
        Vector2 pivotRatio = a.getPivotRatio(time);
        float aspRatio = (float) r[0].getRegionHeight()/r[0].getRegionHeight();
        float inWidth = e.baseWidth * animAspectRatioX;
        float inHeight = baseHeight * animAspectRatioY;
        return new Rectangle(e.pivotPos.x - inWidth * pivotRatio.x, e.pivotPos.y - inHeight * pivotRatio.y,
                inWidth, inHeight);
    }
}
