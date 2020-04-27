package com.wwwf.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.wwwf.game.Entity;

import java.util.HashMap;

public class AnimationLoader {
        static HashMap<Entity.Type, HashMap<String, Animation2>> typeToAnimations;
        static Animation2 MISSING_ANIMATION;

        public static Animation2 getAnimation(Entity.Type type, String movement) {
            if (!typeToAnimations.containsKey(type)) {
                Gdx.app.log("ERROR", "no animation map of this type " + type.name());
                return MISSING_ANIMATION;
            }
            HashMap<String, Animation2> anims = typeToAnimations.get(type);
            if (!anims.containsKey(movement)) {
                Gdx.app.log("ERROR", "no animation of this movement " + movement);
                return MISSING_ANIMATION;
            }
            return anims.get(movement);
        }

        public static void loadAnimations() {
            Texture mImg = new Texture("animations/missing.png");
            TextureRegion[] temp = new TextureRegion[1];
            temp[0] = new TextureRegion(mImg, 0 , 0, mImg.getWidth(), mImg.getHeight());
            MISSING_ANIMATION = new Animation2(new float[]{1}, temp, temp);

            typeToAnimations = new HashMap<Entity.Type, HashMap<String, Animation2>>();
            typeToAnimations.put(Entity.Type.SCOUT, loadAnimationsFromSheet("animations/scout"));
        }
        public static HashMap<String, Animation2> loadAnimationsFromSheet(String filename) {
            HashMap<String, Animation2> animations = new HashMap<String, Animation2>();

            JsonReader jr = new JsonReader();
            JsonValue jv = jr.parse(Gdx.files.internal(filename + ".json"));
            JsonValue frameJson = jv.child();
            Texture backSheet = new Texture(Gdx.files.internal(filename + ".png"));
            Texture frontSheet = new Texture(Gdx.files.internal(filename + "_cl.png"));
            backSheet.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            frontSheet.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            HashMap<Integer, TextureRegion[]> regions = new HashMap<>();
            HashMap<Integer, Float> millis = new HashMap<>();

            for (JsonValue frame : frameJson.iterator()) {
                float duration = frame.get("duration").asFloat();
                JsonValue frameInfo = frame.get("frame");
                int id = Integer.parseInt(frame.name());
                int x = frameInfo.get("x").asInt();
                int y = frameInfo.get("y").asInt();
                int w = frameInfo.get("w").asInt();
                int h = frameInfo.get("h").asInt();
                TextureRegion[] tRegs = new TextureRegion[2];
                tRegs[0] = new TextureRegion(backSheet, x, y, w, h);
                tRegs[1] = new TextureRegion(frontSheet, x, y, w, h);
                regions.put(id, tRegs);
                millis.put(id, duration);
            }

            JsonValue metaJson = jv.child().next;

            for ( JsonValue anim : metaJson.get("frameTags").iterator()) {
                String name = anim.get("name").asString();
                int from = anim.get("from").asInt();
                int to = anim.get("to").asInt();
                TextureRegion[] back = new TextureRegion[to - from + 1];
                TextureRegion[] front = new TextureRegion[to - from + 1];
                float[] seconds = new float[to - from + 1];
                for (int i = 0; i < back.length; i++) {
                    TextureRegion[] backFront = regions.get(from + i);
                    back[i] = backFront[0];
                    front[i] = backFront[1];
                    seconds[i] = millis.get(from + i)/1000f;
                }
                Animation2 a = new Animation2(seconds, back, front);
                animations.put(name, a);
            }
            return animations;
        }

}

