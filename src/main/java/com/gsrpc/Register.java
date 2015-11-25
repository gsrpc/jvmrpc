package com.gsrpc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The gsRPC service register singleton
 */
public final class Register {

    private static final Register instance  = new Register();

    private ConcurrentHashMap<String,Short> nameIndexer = new ConcurrentHashMap<String,Short>();

    private ConcurrentHashMap<Short,String> indexer = new ConcurrentHashMap<Short,String>();

    private Register(){

    }

    public static Register getInstance() {
        return instance;
    }

    /**
     * update the services register information
     * @param table service name/id pair table
     */
    public synchronized void update(Map<String,Short> table) {
        nameIndexer.clear();
        indexer.clear();

        for (Map.Entry<String,Short> entry : table.entrySet()) {
            nameIndexer.put(entry.getKey(),entry.getValue());
            indexer.put(entry.getValue(),entry.getKey());
        }
    }

    public short getID(String name) throws UnknownServiceException {
        Short val =nameIndexer.get(name);

        if (val == null) {
            throw new UnknownServiceException(name);
        }

        return val;
    }

    public String getName(short id) {
        String name = indexer.get(id);

        if (name == null){
            return String.format("unknown service :%s",id);
        }

        return name;
    }
}
