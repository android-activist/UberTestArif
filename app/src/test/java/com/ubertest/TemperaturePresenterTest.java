package com.ubertest;

import com.ubertest.main.FlickerInteractor;
import com.ubertest.main.MainPresenter;
import com.ubertest.main.MainPresenterImpl;

import org.junit.Before;
import org.junit.Test;

public class TemperaturePresenterTest {
    private FlickerInteractor interactor;

    private MainPresenterImpl presenter;

    @Before
    public void setUp(){
        interactor = new FlickerInteractor();
        presenter = new MainPresenterImpl(interactor);

        System.out.println("MainPresenter " + presenter);
        System.out.println("FlickerInteractor " + interactor);
    }


    @Test
    public void onErrorTest() throws  Exception {
        presenter.onError(null);
    }

    @Test
    public void onDataFetchedTest() throws  Exception {
        presenter.onDataFetched(null);
    }

    @Test
    public void onLoadMoreTest() throws  Exception {
        presenter.onLoadMore();
    }

    @Test
    public void onRetryClickedTest() throws  Exception {
        presenter.onRetryClicked();
    }

    @Test
    public void onTextChangedTest() throws  Exception {
        presenter.onTextChanged("");
    }
}