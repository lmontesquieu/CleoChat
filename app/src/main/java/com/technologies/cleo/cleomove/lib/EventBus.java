package com.technologies.cleo.cleomove.lib;

/**
 * Created by Pepe on 10/13/2016.
 */

public interface EventBus {
    void register(Object suscriber);
    void unregister(Object suscriber);
    void post(Object event);
}
