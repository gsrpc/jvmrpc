package com.gsrpc.net;

import io.netty.util.HashedWheelTimer;

/**
 * The default hashed wheel timer singleton
 */
public class HashedWheelTimerSingleton {

    private static HashedWheelTimer hashedWheelTimer;

    public static synchronized HashedWheelTimer instance() {
        if(hashedWheelTimer == null) {
            hashedWheelTimer = new HashedWheelTimer();
        }

        return hashedWheelTimer;
    }
}
