package com.ubertest.main;

import android.text.TextUtils;

import com.ubertest.common.BaseInteractor;
import com.ubertest.common.network.ApiRequest;
import com.ubertest.common.network.NetworkError;
import com.ubertest.common.network.Request;
import com.ubertest.common.network.ResponseListener;
import com.ubertest.common.network.ServerErrorModel;
import com.ubertest.main.model.FlickrResponseModel;

public class FlickerInteractor extends BaseInteractor {
    private static final String API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736";
    private static final int PER_PAGE_COUNT = 50;

    public void fetchFlickerData(int pageNumber, String text, final Listener listener) {
        ResponseListener<FlickrResponseModel> apiListener = new ResponseListener<FlickrResponseModel>() {
            @Override
            public void onResponse(Request<FlickrResponseModel> request, FlickrResponseModel response) {
                if (listener != null) {
                    listener.onDataFetched(response);
                }
            }

            @Override
            public void onErrorResponse(NetworkError error) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        };

        String url = getFlickerUrl(pageNumber, text);
        new ApiRequest<FlickrResponseModel>(url, apiListener) {
            @Override
            protected FlickrResponseModel parseNetworkResponse(String response) {
                return FlickrResponsHelper.parseResponse(response);
            }
        }.fetch();
    }

    private String getFlickerUrl(int pageNumber, String text) {
        String url = BASE_URL
                + "&api_key=" + API_KEY
                + "&format=json"
                + "&page=" + pageNumber
                + "&per_page=" + PER_PAGE_COUNT;

//        if (!TextUtils.isEmpty(text)) {
            url = url + "&text=" + text;
//        }

        return url;
    }

    public interface Listener {
        void onDataFetched(FlickrResponseModel model);

        void onError(NetworkError error);
    }
}
