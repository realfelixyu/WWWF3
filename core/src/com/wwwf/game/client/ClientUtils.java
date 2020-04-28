package com.wwwf.game.client;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.wwwf.game.Entity;

public class ClientUtils {
    public static Rectangle hitbox(Entity e) {
        Vector2 pivotRatio = AnimationLoader.getAnimation(e.type, e.anim).getPivotRatio();
        return new Rectangle(e.pivotPos.x - pivotRatio.x * e.baseWidth,
                e.pivotPos.y - pivotRatio.y * e.baseHeight, e.baseWidth, e.baseHeight);
    }
}
