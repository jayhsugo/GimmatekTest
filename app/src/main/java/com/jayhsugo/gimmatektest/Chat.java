package com.jayhsugo.gimmatektest;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Chat implements Serializable{
    private String userName;
    private String uid;
    private String timeDiff;
    private String distance;
    private String body;
    private String photo;
    private String userPhoto;
    private String tag;
    private int numGood;
    private int numBad;

    public Chat(String userName, String uid, String timeDiff, String distance, String body, String photo, String userPhoto, String tag, int numGood, int numBad) {
        this.userName = userName;
        this.uid = uid;
        this.timeDiff = timeDiff;
        this.distance = distance;
        this.body = body;
        this.photo = photo;
        this.userPhoto = userPhoto;
        this.tag = tag;
        this.numGood = numGood;
        this.numBad = numBad;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(String timeDiff) {
        this.timeDiff = timeDiff;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getNumGood() {
        return numGood;
    }

    public void setNumGood(int numGood) {
        this.numGood = numGood;
    }

    public int getNumBad() {
        return numBad;
    }

    public void setNumBad(int numBad) {
        this.numBad = numBad;
    }

}
