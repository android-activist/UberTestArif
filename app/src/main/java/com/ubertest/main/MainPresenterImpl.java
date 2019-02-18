package com.ubertest.main;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.ubertest.common.BasePresenter;
import com.ubertest.common.Disposable;
import com.ubertest.common.DisposableObserver;
import com.ubertest.common.network.NetworkError;
import com.ubertest.main.model.FlickrResponseModel;
import com.ubertest.main.model.PhotosResponseModel;
import com.ubertest.main.vm.FooterUpdateVm;
import com.ubertest.main.vm.PhotoVm;
import com.ubertest.main.vm.PhotosListVm;

import java.util.List;

public class MainPresenterImpl extends BasePresenter implements MainPresenter, FlickerInteractor.Listener {
    private static final String PHOTO_LIST_OBSERVER = "photo_list_observer";
    private static final String FOOTER_UPDATE_OBSERVER = "footer_update_observer";

    private static final long QUERY_WAIT_TIME = 500;

    private final Handler handler;

    private int pageNumber = 0;

    private final FlickerInteractor interactor;

    private boolean isLoading = false;
    private boolean hasError = false;

    private String lastText = "";

    private Runnable queryRunnable;


    public MainPresenterImpl(FlickerInteractor interactor) {
        this.interactor = interactor;

        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void start() {
        super.start();

        updateFooterView();
    }

    @Override
    public synchronized void onLoadMore() {
        if (isLoading) {
            return;
        }

        pageNumber++;

        isLoading = true;
        hasError = false;

        updateFooterView();

        interactor.fetchFlickerData(pageNumber, lastText, this);
    }

    @Override
    public Disposable subscribePhotoListObserver(DisposableObserver<PhotosListVm> observer) {
        return subscribe(PHOTO_LIST_OBSERVER, observer);
    }

    @Override
    public Disposable subscribeFooterUpdateObserver(DisposableObserver<FooterUpdateVm> observer) {
        return subscribe(FOOTER_UPDATE_OBSERVER, observer);
    }

    @Override
    public void onRetryClicked() {
        isLoading = true;
        hasError = false;

        updateFooterView();

        interactor.fetchFlickerData(pageNumber, lastText, this);
    }

    @Override
    public void onTextChanged(String query) {
        if (queryRunnable != null) {
            handler.removeCallbacks(queryRunnable);
        }

        queryRunnable = new QueryRunnable(query);
        handler.postDelayed(queryRunnable, QUERY_WAIT_TIME);
    }

    @Override
    public void onDataFetched(FlickrResponseModel model) {
        if (model == null || !"OK".equalsIgnoreCase(model.getStat())) {
            onError(null);
            return;
        }

        final PhotosResponseModel photosResponseModel = model.getPhotosResponseModel();
        final List<PhotoVm> imageUrls = FlickrResponsHelper.getImageUrls(photosResponseModel.getPhoto());
        if (imageUrls == null) {
            onError(null);
            return;
        }

        PhotosListVm vm = new PhotosListVm();
        vm.photos = imageUrls;
        vm.reset = pageNumber == 1;

        notifyAllCallbacks(PHOTO_LIST_OBSERVER, vm);

        isLoading = false;
        hasError = false;

        updateFooterView();
    }

    private void updateFooterView() {
        FooterUpdateVm vm = new FooterUpdateVm();
        vm.isLoading = isLoading;
        vm.hasError = hasError;

        notifyAllCallbacks(FOOTER_UPDATE_OBSERVER, vm);
    }

    @Override
    public void onError(NetworkError error) {
        isLoading = false;
        hasError = true;

        updateFooterView();
    }

    private class QueryRunnable implements Runnable {
        private String text;

        private QueryRunnable(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            MainPresenterImpl.this.lastText = text;

            if (TextUtils.isEmpty(lastText)) {
                return;
            }

            pageNumber = 0;

            onLoadMore();
        }
    }
}
