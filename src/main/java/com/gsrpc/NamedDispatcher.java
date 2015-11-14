package com.gsrpc;

/**
 * Named Dispatcher extend the Dispatcher interface with name method
 */
public interface NamedDispatcher extends Dispatcher {
    /**
     * the dispatcher's name
     * @return the name string
     */
    String name();
}
