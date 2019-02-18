package com.ubertest.main;

import android.util.Log;

import com.ubertest.main.model.FlickrResponseModel;
import com.ubertest.main.model.PhotoModel;
import com.ubertest.main.model.PhotosResponseModel;
import com.ubertest.main.vm.PhotoVm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FlickrResponsHelper {
    private static final String TAG = FlickrResponsHelper.class.getName();

    private static final String KEY_STAT = "stat";
    private static final String KEY_PHOTOS = "photos";

    private static final String KEY_PAGE = "page";
    private static final String KEY_PAGES = "pages";
    private static final String KEY_PER_PAGE = "perpage";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_PHOTO = "photo";

    private static final String KEY_ID = "id";
    private static final String KEY_OWNER = "owner";
    private static final String KEY_SECRET = "secret";
    private static final String KEY_SERVER = "server";
    private static final String KEY_FARM = "farm";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IS_PUBLIC = "ispublic";
    private static final String KEY_IS_FRIEND = "isfriend";
    private static final String KEY_IS_FAMILY = "isfamily";

    public static FlickrResponseModel parseResponse(String response) {
        FlickrResponseModel model = new FlickrResponseModel();
        try {
            if (response.startsWith("jsonFlickrApi(")) {
                response = response.replace("jsonFlickrApi(", "");
            }

            if (response.endsWith(")")) {
                response = response.substring(0, response.length() - 1);
            }

            JSONObject responseObj = new JSONObject(response);
            if (responseObj.has(KEY_STAT)) {
                model.setStat(responseObj.optString(KEY_STAT));
            }

            if (responseObj.has(KEY_PHOTOS)) {
                final JSONObject photosJsonObj = responseObj.getJSONObject(KEY_PHOTOS);
                model.setPhotosResponseModel(getPhotoResponseModel(photosJsonObj));
            }
        } catch (JSONException e) {
            Log.e(TAG, "parseResponse", e);

            return null;
        }

        return model;
    }

    private static PhotosResponseModel getPhotoResponseModel(JSONObject photosJsonObj) {
        PhotosResponseModel model = new PhotosResponseModel();

        if (photosJsonObj.has(KEY_PAGE)) {
            model.setPage(photosJsonObj.optInt(KEY_PAGE));
        }

        if (photosJsonObj.has(KEY_PAGES)) {
            model.setPages(photosJsonObj.optInt(KEY_PAGES));
        }

        if (photosJsonObj.has(KEY_PER_PAGE)) {
            model.setPerpage(photosJsonObj.optInt(KEY_PER_PAGE));
        }

        if (photosJsonObj.has(KEY_TOTAL)) {
            model.setTotal(photosJsonObj.optLong(KEY_TOTAL));
        }

        if (photosJsonObj.has(KEY_PHOTO)) {
            List<PhotoModel> photosList = parsePhotos(photosJsonObj.optJSONArray(KEY_PHOTO));
            model.setPhoto(photosList);
        }

        return model;
    }

    private static List<PhotoModel> parsePhotos(JSONArray photosJsonArray) {
        List<PhotoModel> list = new ArrayList<>();
        for (int i = 0; i < photosJsonArray.length(); i++) {
            final JSONObject photoJsonObject = photosJsonArray.optJSONObject(i);
            PhotoModel model = parsePhotoModel(photoJsonObject);
            list.add(model);
        }

        return list;
    }

    private static PhotoModel parsePhotoModel(JSONObject photoJsonObject) {
        PhotoModel model = new PhotoModel();
        if (photoJsonObject.has(KEY_ID)) {
            model.setId(photoJsonObject.optString(KEY_ID));
        }

        if (photoJsonObject.has(KEY_OWNER)) {
            model.setOwner(photoJsonObject.optString(KEY_OWNER));
        }

        if (photoJsonObject.has(KEY_SECRET)) {
            model.setSecret(photoJsonObject.optString(KEY_SECRET));
        }

        if (photoJsonObject.has(KEY_SERVER)) {
            model.setServer(photoJsonObject.optString(KEY_SERVER));
        }

        if (photoJsonObject.has(KEY_FARM)) {
            model.setFarm(photoJsonObject.optInt(KEY_FARM));
        }

        if (photoJsonObject.has(KEY_TITLE)) {
            model.setTitle(photoJsonObject.optString(KEY_TITLE));
        }

        if (photoJsonObject.has(KEY_IS_FRIEND)) {
            model.setFriend(photoJsonObject.optInt(KEY_IS_FRIEND));
        }

        if (photoJsonObject.has(KEY_IS_FAMILY)) {
            model.setFamily(photoJsonObject.optInt(KEY_IS_FAMILY));
        }

        if (photoJsonObject.has(KEY_IS_PUBLIC)) {
            model.setPublic(photoJsonObject.optInt(KEY_IS_PUBLIC));
        }

        return model;
    }

    public static String getImageUrl(PhotoModel model) {
        StringBuilder imageUrl = new StringBuilder("http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg");

        replace(imageUrl,"{farm}", String.valueOf(model.getFarm()));
        replace(imageUrl,"{server}", String.valueOf(model.getServer()));
        replace(imageUrl,"{id}", String.valueOf(model.getId()));
        replace(imageUrl,"{secret}", String.valueOf(model.getSecret()));

        return imageUrl.toString();
    }

    private static void replace(StringBuilder builder, String oldString, String newString) {
        int start = builder.indexOf(oldString);
        int end = start + oldString.length();
        builder.replace(start, end, newString);
    }

    public static List<PhotoVm> getImageUrls(List<PhotoModel> photo) {
        if (photo == null) {
            return null;
        }

        List<PhotoVm> list = new ArrayList<>();
        for (PhotoModel model : photo) {
            PhotoVm vm = new PhotoVm();
            vm.imageUrl = getImageUrl(model);
            vm.title = model.getTitle();

            list.add(vm);
        }

        return list;
    }
}
