package com.gsrpc.net;

import java.net.InetSocketAddress;

/**
 *
 */
public class Remote {

    private final InetSocketAddress address;
    private final DHKey dhKey;

    public Remote(InetSocketAddress address, DHKey dhKey) {
        this.address = address;
        this.dhKey = dhKey;
    }

    /**
     * encryption DH-KEY to connect gschat service
     * @return DH-KEY
     */
    public DHKey getDhKey() {
        return dhKey;
    }

    /**
     * get remote gschat service's socket address
     * @return
     */
    public InetSocketAddress getAddress() {
        return address;
    }
}