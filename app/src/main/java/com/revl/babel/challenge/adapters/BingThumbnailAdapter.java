package com.revl.babel.challenge.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.revl.babel.challenge.R;
import com.revl.babel.challenge.model.Image;
import com.revl.babel.challenge.viewholders.BingThumbnailViewHolder;

import java.util.ArrayList;
import java.util.List;


public class BingThumbnailAdapter extends RecyclerView.Adapter<BingThumbnailViewHolder> {

    private static final String TAG = BingThumbnailAdapter.class.getSimpleName();

    private List<Image> images = new ArrayList<>();

    @Override
    public BingThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView itemView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.thumbnail, parent, false);

        return new BingThumbnailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BingThumbnailViewHolder holder, final int position) {
        holder.bind(images.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFullImage(position);
            }
        });
    }

    private void showFullImage(int position) {
        Log.d(TAG, "BingThumbnailAdapter.showFullImage images = " + images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public List<Image> getImages() {
        return images;
    }
}
