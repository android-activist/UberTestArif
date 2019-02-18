package com.ubertest.main.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotoModel {
    private String id;
    private String owner;
    private String secret;
    private String server;
    private long farm;
    private String title;
    private boolean isPublic;
    private boolean isFriend;
    private boolean isFamily;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public long getFarm() {
        return farm;
    }

    public void setFarm(long farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(int aPublic) {
        isPublic = aPublic == 1;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(int friend) {
        isFriend = friend == 1;
    }

    public boolean isFamily() {
        return isFamily;
    }

    public void setFamily(int family) {
        isFamily = family == 1;
    }
}
