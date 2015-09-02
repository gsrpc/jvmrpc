package com.gsrpc;


public abstract class Promise<V> extends Callback implements Future<V> {


    private final int timeout;
    /**
     * rpc exception
     */
    private Exception e;

    /**
     * rpc result value;
     */
    private V v;

    /**
     * success listener
     */
    private SuccessListener<V> successListener;

    /**
     * error listener
     */
    private ErrorListener<V> errorListener;

    /**
     * promise state flag
     */
    private volatile boolean raised;


    /**
     * create new promise with rpc timeout arg
     * @param timeout rpc call timeout unit is second
     */
    public Promise(int timeout) {

        this.timeout = timeout;
    }

    /**
     * notify promise result
     * @param e {@link Exception} rpc call exception
     * @param v {@link V} rpc call result value
     */
    public synchronized void Notify(Exception e, V v) {

        this.e = e;

        this.v = v;

        raised = true;

        // rpc error
        if (this.e != null && errorListener != null) {
            errorListener.onError(e);
        }

        if(successListener != null){
            successListener.onSuccess(v);
        }



        notifyAll();
    }

    @Override
    public synchronized void then(SuccessListener<V> listener) {
        if(this.raised && this.e == null) {
            listener.onSuccess(v);
            return;
        }

        this.successListener = listener;
    }

    @Override
    public void error(ErrorListener<V> listener) {

        if(this.raised && this.e != null) {
            listener.onError(e);
            return;
        }

        this.errorListener = listener;
    }

    @Override
    public boolean isReady() {
        return raised;
    }

    @Override
    public synchronized V util() throws Exception{

        while(true){

            if(isReady()) {
                if(this.e != null){
                    throw e;
                }

                return v;
            }

            wait();
        }
    }

    @Override
    public int getTimeout() {
        return timeout;
    }
}
