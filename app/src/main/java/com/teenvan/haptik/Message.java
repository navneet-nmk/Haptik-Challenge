package com.teenvan.haptik;

import java.sql.Time;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by navneet on 21/12/16.
 */

public class Message extends RealmObject {
    // Declaration of member variables
    private String body;
    private String username, name, imageUrl;
    @PrimaryKey
    private String mssgTime;
    private boolean favorited = false;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMssgTime() {
        return mssgTime;
    }

    public void setMssgTime(String mssgTime) {
        this.mssgTime = mssgTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
}
