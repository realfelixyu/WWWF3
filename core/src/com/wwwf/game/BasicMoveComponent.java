package com.wwwf.game;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BasicMoveComponent implements Component{
    Entity e;
    StateMachine<BasicMoveComponent, MoveState> fsm;
    public Array<Vector2> currentPath;

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case TeleInfo.MOVE_ALONG:
                e.dispatcher.dispatchMessage(TeleInfo.SET_VELOCITY, Utils.ZERO);
                currentPath = (Array<Vector2>) msg.extraInfo;
                fsm.changeState(MoveState.MOVE_TO);
                break;
        }
        return false;
    }

    enum MoveState implements State<BasicMoveComponent> {

        IDLE() {
            @Override
            public void enter(BasicMoveComponent move) {
                move.e.setAnimation("idle", null);
            }

            @Override
            public void update(BasicMoveComponent move) {

            }

            @Override
            public void exit(BasicMoveComponent move) {

            }

            @Override
            public boolean onMessage(BasicMoveComponent move, Telegram telegram) {
                return false;
            }
        },
        MOVE_TO() {
            @Override
            public void enter(BasicMoveComponent move) {
                move.e.setAnimation("run", null);
            }

            @Override
            public void update(BasicMoveComponent move) {
                if (move.currentPath.isEmpty()) {
                    move.fsm.changeState(IDLE);
                    return;
                }
                Vector2 dir = new Vector2(move.currentPath.get(0)).sub(move.e.pivotPos);
                float dist = dir.len();
                if (dist < Utils.STOPPING_RADIUS) {
                    if (move.currentPath.size == 1) {
                        move.e.dispatcher.dispatchMessage(TeleInfo.SET_VELOCITY, Utils.ZERO);
                    }
                    move.currentPath.removeIndex(0);
                } else {
                    Vector2 force = new Vector2(move.e.vel).scl(-0.01f).scl(-0.01f).add(dir.scl(0.05f));
                    move.e.dispatcher.dispatchMessage(TeleInfo.APPLY_FORCE, force);
                }
            }

            @Override
            public void exit(BasicMoveComponent move) {

            }

            @Override
            public boolean onMessage(BasicMoveComponent move, Telegram telegram) {
                return false;
            }
        },
        GLOBAL_STATE() {
            @Override
            public void enter(BasicMoveComponent move) {

            }

            @Override
            public void update(BasicMoveComponent move) {

            }

            @Override
            public void exit(BasicMoveComponent move) {

            }

            @Override
            public boolean onMessage(BasicMoveComponent move, Telegram telegram) {
                return false;
            }
        };



    }
    BasicMoveComponent(Entity master) {
        this.e = master;
        fsm = new DefaultStateMachine<BasicMoveComponent, MoveState>(this);
        fsm.setInitialState(MoveState.IDLE);
        fsm.setGlobalState(MoveState.GLOBAL_STATE);
        currentPath = new Array<>();

    }
    public void update(float dt) {
        fsm.update();
    }

    @Override
    public void free() {

    }

    @Override
    public int[] teleCodes() {
        return new int[]{TeleInfo.MOVE_ALONG};
    }
}
