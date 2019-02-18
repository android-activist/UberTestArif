package com.ubertest.main;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.ubertest.R;
import com.ubertest.common.BaseActivity;
import com.ubertest.common.DisposableObserver;
import com.ubertest.common.utils.GridLayoutScrollManager;
import com.ubertest.common.utils.PageScrollManagerListener;
import com.ubertest.main.vm.FooterUpdateVm;
import com.ubertest.main.vm.PhotosListVm;

public class MainActivity extends BaseActivity {
    private MainPresenter presenter;

    private RecyclerView recyclerView;
    private PhotosAdapter adapter;

    private View loaderView;
    private View errorView;
    private Button retryButton;

    private SearchView searchView;
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        loaderView = findViewById(R.id.loader_view);
        errorView = findViewById(R.id.error_view);
        retryButton = findViewById(R.id.retry_button);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRetryClicked();
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter = new PhotosAdapter());
        recyclerView.addItemDecoration(new PhotoDecorator(3));
        recyclerView.addOnScrollListener(new GridLayoutScrollManager(layoutManager, new PageScrollManagerListener() {
            @Override
            public void onLoadMore() {
                presenter.onLoadMore();
            }
        }));

        initPresenter();
        addDisposable(presenter.subscribePhotoListObserver(new DisposableObserver<PhotosListVm>() {
            @Override
            public void onUpdate(PhotosListVm photosListVm) {
                if (photosListVm.reset) {
                    adapter.clear();
                }

                adapter.addImages(photosListVm.photos);
            }
        }));

        addDisposable(presenter.subscribeFooterUpdateObserver(new DisposableObserver<FooterUpdateVm>() {
            @Override
            public void onUpdate(FooterUpdateVm viewModel) {
                loaderView.setVisibility(viewModel.isLoading ? View.VISIBLE : View.GONE);
                errorView.setVisibility(viewModel.hasError ? View.VISIBLE : View.GONE);
                if (searchField != null) {
                    searchField.setEnabled(!viewModel.isLoading);
                }
            }
        }));
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        final SearchableInfo searchableInfo = manager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
        for (int i = 0; i < searchView.getChildCount(); i++) {
            final View view = searchView.getChildAt(i);
            if (view instanceof EditText) {
                searchField = (EditText) view;
                break;
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                presenter.onTextChanged(query);
                return true;
            }
        });

        return true;
    }

    private void initPresenter() {
        presenter = new MainPresenterImpl(new FlickerInteractor());
        presenter.start();
    }
}
