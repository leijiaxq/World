package com.vooda.world.eventbus;


import org.greenrobot.eventbus.EventBus;

/**
 * Created by leijiaxq
 * Data       2016/12/20 11:07
 * Describe
 */
public class EventBusUtil {

    //注册
    public static void register(Object obj){
        EventBus.getDefault().register(obj);
    }

    //注销
    public static void unregister(Object obj){
        EventBus.getDefault().unregister(obj);
    }

    //其他方式
    private static void post(Object cs){
        EventBus.getDefault().post(cs);
    }

    //带bean类的event
    public static void postInfoEvent(int eventId,Object obj){
        EventObject infoEvent = new EventObject(eventId, obj);
        post(infoEvent);
    }

}

