package com.revl.babel.challenge.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.revl.babel.challenge.model.Image;
import com.squareup.picasso.Picasso;

public class BingThumbnailViewHolder extends RecyclerView.ViewHolder {


    ImageView thumbnailView;

    public BingThumbnailViewHolder(ImageView itemView) {
        super(itemView);
        this.thumbnailView = itemView;
    }


    public void bind(Image image) {
        Context context = itemView.getContext();
        Picasso.with(context)
                .load(image.imageUrl())
                .placeholder(new ColorDrawable(Color.parseColor("#"+image.accentColor())))
                .into(thumbnailView);

    }
}
