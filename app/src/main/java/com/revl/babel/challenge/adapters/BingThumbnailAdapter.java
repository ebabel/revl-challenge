package com.revl.babel.challenge.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.revl.babel.challenge.FullScreenImageActivity;
import com.revl.babel.challenge.R;
import com.revl.babel.challenge.model.Image;
import com.revl.babel.challenge.viewholders.BingThumbnailViewHolder;

import java.util.ArrayList;
import java.util.List;


public abstract class BingThumbnailAdapter extends RecyclerView.Adapter<BingThumbnailViewHolder> {

    private static final String TAG = BingThumbnailAdapter.class.getSimpleName();

    private List<Image> images = new ArrayList<>();
    private Activity activity;

    public BingThumbnailAdapter(Activity activity) {

        this.activity = activity;
    }

    @Override
    public BingThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView itemView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.thumbnail, parent, false);

        return new BingThumbnailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BingThumbnailViewHolder holder, int position) {
        holder.bind(images.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFullImage(holder.getAdapterPosition(), view);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(activity, "Copied to clipboard", Toast.LENGTH_SHORT).show();

                copyImageUrlToClipboard(holder.getAdapterPosition());
                return true;
            }
        });


        if ((position >= getItemCount() - 1)) {
            loadMoreItems(getItemCount());
        }
    }

    private void copyImageUrlToClipboard(int position) {
        Image image = images.get(position);
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Image URL", image.imageUrl());
        clipboard.setPrimaryClip(clip);

    }


    protected abstract void loadMoreItems(int offset);

    private void showFullImage(int position, View view) {
        Image image = images.get(position);
        Log.d(TAG, "BingThumbnailAdapter.showFullImage images = " + image);
        Intent intent = new Intent(activity, FullScreenImageActivity.class);
        intent.putExtra(FullScreenImageActivity.IMAGE_PARCEL, image);
        activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, view, "imageTransition").toBundle());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public List<Image> getImages() {
        return images;
    }
}
