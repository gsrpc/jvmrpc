package com.gsrpc.events;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * StateEvent
 */
public class Event<T> {
    /**
     *  the event listeners
     */
    private final ConcurrentHashMap<Slot,EventListener<T>> listeners = new ConcurrentHashMap<Slot,EventListener<T>>();

    private final Executor executor;

    public Event(Executor executor) {
        this.executor = executor;
    }

    public Event() {
        this.executor = null;
    }

    public Slot connect(final EventListener<T> listener) {

        final Slot slot = new Slot() {
            @Override
            public void disconnect() {
                listeners.remove(this,listener);
            }
        };

        listeners.put(slot,listener);

        return slot;
    }

    /**
     * raise event with event arg
     * @param param
     */
    public void raise(final T param){

        if(executor == null) {
            for(EventListener<T> listener: listeners.values()) {
                listener.call(param);
            }

            return;
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                for(EventListener<T> listener: listeners.values()) {
                    listener.call(param);
                }
            }
        });
    }
}
