package com.gsrpc.net;

import com.gsrpc.Device;

public interface DHKeyResolver {
    DHKey resolver(Device device) throws Exception;
}
