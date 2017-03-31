package com.vooda.world.eventbus;

/**
 * Created by leijiaxq
 * Data       2016/12/20 11:07
 * Describe
 */
public class EventObject {

    public int    id;
    public Object obj;

    public EventObject(){}

    public EventObject(int id, Object obj) {
        this.id = id;
        this.obj = obj;
    }
}
