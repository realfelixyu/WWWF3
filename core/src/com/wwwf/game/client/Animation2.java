package com.wwwf.game.client;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.NavigableMap;
import java.util.TreeMap;

/** This class represents an animation that uses 2 layers, a bottom layer, and a top layer. The top layer can be colored
 * to represent custom player colors.
 */

public class Animation2 {
    NavigableMap<Float, TextureRegion[]> timeToTex;
    float highestTime;

    Animation2(float[] times, TextureRegion[] back, TextureRegion[] front) {
        timeToTex = new TreeMap<>();
        float[] cumulative = new float[times.length];
        cumulative[0] = 0;
        for (int i = 1; i < times.length; i++) {
            cumulative[i] = times[i - 1] + cumulative[i - 1];
        }
        highestTime = cumulative[times.length - 1];
        for (int i = 0; i < cumulative.length; i++) {
            TextureRegion[] backFront = new TextureRegion[2];
            backFront[0] = back[i];
            backFront[1] = front[i];
            timeToTex.put(cumulative[i], backFront);
        }
    }
    public Vector2 getPivotRatio() {
        return new Vector2(0.4f, 0.5f);
    }
    public TextureRegion[] getKeyframe(float time) {
        time = time % highestTime;
        return timeToTex.floorEntry(time).getValue();
    }
}
