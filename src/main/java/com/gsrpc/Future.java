package com.gsrpc;

/**
 * gsrpc java async call instrument
 */
public interface Future<V> {

    /**
     * set success listener
     * @param listener see {@link SuccessListener<V>}
     */
    void then(SuccessListener<V> listener);

    /**
     * set error listener
     * @param listener see {@link ErrorListener<V>}
     */
    void error(ErrorListener<V> listener);

    /**
     * check if the promise is complete
     * @return
     */
    boolean isReady();

    /**
     * wait util rpc received response
     * @return V
     */
    V util() throws Exception;
}
