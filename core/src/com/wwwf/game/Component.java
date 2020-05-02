package com.wwwf.game;

import com.badlogic.gdx.ai.msg.Telegraph;

/** Represents an addon to an entity. TeleCodes returns an array of TeleCodes that this component can receive */
public interface Component extends Telegraph {
    public void update(float dt);
    public void free();
    public int[] teleCodes();
}
