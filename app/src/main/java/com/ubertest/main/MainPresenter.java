package com.ubertest.main;

import com.ubertest.common.Disposable;
import com.ubertest.common.DisposableObserver;
import com.ubertest.common.Presenter;
import com.ubertest.main.vm.FooterUpdateVm;
import com.ubertest.main.vm.PhotosListVm;

public interface MainPresenter extends Presenter {
    void onLoadMore();

    Disposable subscribePhotoListObserver(DisposableObserver<PhotosListVm> observer);

    Disposable subscribeFooterUpdateObserver(DisposableObserver<FooterUpdateVm> observer);

    void onRetryClicked();

    void onTextChanged(String query);
}
