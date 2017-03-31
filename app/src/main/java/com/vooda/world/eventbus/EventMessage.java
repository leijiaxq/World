package com.vooda.world.eventbus;

/**
 * Created by leijiaxq
 * Data       2016/12/20 11:07
 * Describe
 */
public class EventMessage  extends EventObject {
    private String msg;

    public EventMessage(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
