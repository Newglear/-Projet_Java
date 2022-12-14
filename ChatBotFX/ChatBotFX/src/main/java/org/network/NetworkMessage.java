package org.network;

public class NetworkMessage {

    Types.UDPMode mode;

    public Types.UDPMode getMode() {
        return mode;
    }

    public String getObject() {
        return object;
    }

    String object;

    NetworkMessage(Types.UDPMode m, String obj){
        this.object = obj ;
        this.mode = m;
    }




}
