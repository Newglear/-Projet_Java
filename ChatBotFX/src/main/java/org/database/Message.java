package org.database;

import java.sql.Date;

public class Message {
    private String Sender;

    private boolean Sent;
    private String msg;
    private Date date;

    public Message(String S, boolean Sent, String m, Date d) {
        this.Sender = S;
        this.Sent = Sent;
        this.msg = m;
        this.date = d;
    }
    public Message(String S, boolean Sent, String m){
        this.Sender = S;
        this.Sent = Sent;
        this.msg  = m ;
    }

    public Message(String S, String m){
        this.Sender = S;
        this.msg = m;
    }
    public boolean isSent() {
        return Sent;
    }

    public void setSent(boolean Sent) {
        this.Sent = Sent;
    }

    public void toggleSent(){
        this.Sent = !Sent;
    }
    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
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
                "\nReceiver: "+ this.Sent +
                "\nContent: "+ this.msg +
                "\nDate: "+ this.date;
    }
}
