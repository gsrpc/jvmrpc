package com.gsrpc;

/**
 * Unknown service exception
 */
public class UnknownServiceException extends Exception {

    private final String serviceName;

    public UnknownServiceException(String serviceName) {
        super(String.format("unknown service %s",serviceName));
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
