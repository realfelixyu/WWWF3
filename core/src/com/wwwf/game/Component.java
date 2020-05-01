package com.wwwf.game;

import com.badlogic.gdx.ai.msg.Telegraph;

public interface Component extends Telegraph {
    public void update(float dt);
    public void free();
    public int[] teleCodes();
}
