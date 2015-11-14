package com.gsrpc;

/**
 * The DispatcherChannel interface combine {@see MessageChannel} and {@see Dispatcher} interface
 */
public interface DispatcherChannel extends Channel,Dispatcher {
    void addService(short id,Dispatcher dispatcher);
    void addService(NamedDispatcher dispatcher) throws UnknownServiceException;
    void removeService(short id,Dispatcher dispatcher);
    void removeService(NamedDispatcher dispatcher) throws UnknownServiceException;
}
