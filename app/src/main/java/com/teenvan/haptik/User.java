package com.teenvan.haptik;


/**
 * Created by navneet on 22/12/16.
 */

public class User {
    private String name;
    private int msgCount = 0 , favmsgCount = 0;


    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public int getFavmsgCount() {
        return favmsgCount;
    }

    public void setFavmsgCount(int favmsgCount) {
        this.favmsgCount = favmsgCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
