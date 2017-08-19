package com.revl.babel.challenge.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.revl.babel.challenge.model.Image;

public class BingThumbnailViewHolder extends RecyclerView.ViewHolder {


    private ImageView thumbnailView;

    public BingThumbnailViewHolder(ImageView itemView) {
        super(itemView);
        this.thumbnailView = itemView;
    }


    public void bind(Image image) {
        Context context = itemView.getContext();

        thumbnailView.setBackgroundColor(Color.parseColor("#" + image.accentColor()));
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.error(android.R.drawable.alert_light_frame);
        Glide.with(context)
                .load(image.thumbnailUrl())
                .apply(options)
                .into(thumbnailView);

    }
}
