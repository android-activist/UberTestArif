package com.ubertest.main.model;

public class FlickrResponseModel {
    private PhotosResponseModel photosResponseModel;

    private String stat;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public PhotosResponseModel getPhotosResponseModel() {
        return photosResponseModel;
    }

    public void setPhotosResponseModel(PhotosResponseModel photosResponseModel) {
        this.photosResponseModel = photosResponseModel;
    }
}
