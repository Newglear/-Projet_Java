package org.database;

import java.util.Arrays;

public class User {

    private int port;
    private String addr;
    private String pseudo;
    private String userInfos;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    int userID;

    public String getAddr() {
        return addr;
    }

    public String UserInfos(){
        return pseudo+"-"+port+"-"+addr;
    }

    public User(String pseudo, int port, String addr){
        this.pseudo = pseudo;
        this.port = port;
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "Nickname: " + this.pseudo + "\nPort: "+ this.port + "\nAdresse: "+ this.addr;
    }
}
