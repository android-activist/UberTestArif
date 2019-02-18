package com.ubertest.main;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class PhotoDecorator extends RecyclerView.ItemDecoration {
    private int spanCount;

    public PhotoDecorator(int spanCount) {
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        final int position = parent.getChildAdapterPosition(view);
        outRect.top = 10;
        outRect.left = 10;

        if (position % spanCount == 2) {
            outRect.right = 10;
        } else {
            outRect.right = 0;
        }
    }
}
