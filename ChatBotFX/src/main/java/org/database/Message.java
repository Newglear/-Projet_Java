package org.database;

import java.sql.Date;

public class Message {
    private String Sender,Receiver;
    private String msg;
    private Date date;

    public Message(String S, String  R, String m, Date d) {
        this.Sender = S;
        this.Receiver = R;
        this.msg = m;
        this.date = d;
    }
    public Message(String S, String  R, String m){
        this.Sender = S;
        this.Receiver = R;
        this.msg  = m ;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Sender: "+ this.Sender +
                "\nReceiver: "+ this.Receiver +
                "\nContent: "+ this.msg +
                "\nDate: "+ this.date;
    }
}
