package com.technologies.cleo.cleochat.lib;

import android.util.Log;

/**
 * Created by Pepe on 10/13/2016.
 */

public class GreenRobotEventBus implements EventBus {

    org.greenrobot.eventbus.EventBus eventBus;

    private static class SingletonHolder {
        private static final GreenRobotEventBus INSTANCE = new GreenRobotEventBus();
    }

    public static GreenRobotEventBus getInstance() {
        //Log.e(TAG, "getInstance");
        return SingletonHolder.INSTANCE;
    }

    public GreenRobotEventBus() {
        //Log.e(TAG, "GreenRobotEventBus");
        this.eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Override
    public void register(Object suscriber) {
        //Log.e(TAG, "register");
        eventBus.register(suscriber);
    }

    @Override
    public void unregister(Object suscriber) {
        eventBus.unregister(suscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}
