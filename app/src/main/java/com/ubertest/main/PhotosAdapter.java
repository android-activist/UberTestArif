package com.ubertest.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ubertest.R;
import com.ubertest.common.image.ImageLoader;
import com.ubertest.main.vm.PhotoVm;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {
    private List<PhotoVm> list = new ArrayList<>();

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_list_item, viewGroup, false);
        PhotoViewHolder viewHolder = new PhotoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder photoViewHolder, int position) {
        final PhotoVm photoVm = list.get(position);

        new ImageLoader(photoViewHolder.imageView)
                .url(photoVm.imageUrl)
                .fetch();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addImages(List<PhotoVm> photos) {
        if (photos == null) {
            return;
        }

        list.addAll(photos);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
        }
    }
}
