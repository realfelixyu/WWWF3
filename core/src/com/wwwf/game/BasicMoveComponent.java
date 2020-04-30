package com.wwwf.game;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BasicMoveComponent implements Component{
    Entity master;
    StateMachine<Entity, MoveState> fsm;
    Array<Vector2> currentPath;

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case TeleInfo.MOVE_ALONG:
                currentPath = (Array<Vector2>) msg.extraInfo;
                break;
        }
        return false;
    }

    enum MoveState implements State<Entity> {

        IDLE() {

        },
        MOVE_TO() {

        },
        GLOBAL_STATE() {

        };


        @Override
        public void enter(Entity entity) {

        }

        @Override
        public void update(Entity entity) {

        }

        @Override
        public void exit(Entity entity) {

        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    }
    BasicMoveComponent(Entity master) {
        this.master = master;
        fsm = new DefaultStateMachine<>();
        fsm.setInitialState(MoveState.IDLE);
        fsm.setGlobalState(MoveState.GLOBAL_STATE);
        currentPath = new Array<>();
    }
    public void update(float dt) {

    }

    @Override
    public void free() {

    }
}
