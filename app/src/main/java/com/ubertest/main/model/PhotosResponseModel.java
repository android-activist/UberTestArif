package com.ubertest.main.model;

import java.util.List;

public class PhotosResponseModel {
    private int page;
    private int pages;
    private int perpage;
    private long total;
    private List<PhotoModel> photo = null;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<PhotoModel> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PhotoModel> photo) {
        this.photo = photo;
    }
}