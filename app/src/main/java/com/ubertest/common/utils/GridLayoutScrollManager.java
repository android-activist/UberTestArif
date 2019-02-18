package com.ubertest.common.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class GridLayoutScrollManager extends RecyclerView.OnScrollListener {
    private GridLayoutManager layoutManager;

    private PageScrollManagerListener listener;

    public GridLayoutScrollManager(GridLayoutManager layoutManager, PageScrollManagerListener listener) {
        this.layoutManager = layoutManager;
        this.listener = listener;
    }

    public void setListener(PageScrollManagerListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        int lastItemPosition = visibleItemCount + firstVisibleItemPosition;

        int pageSize = recyclerView.getAdapter().getItemCount();
        if (lastItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= pageSize) {
            if (listener != null) {
                listener.onLoadMore();
            }
        }
    }
}