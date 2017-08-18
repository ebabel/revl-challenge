package com.revl.babel.challenge.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.revl.babel.challenge.model.Image;
import com.squareup.picasso.Picasso;


public class BingImageViewHolder extends RecyclerView.ViewHolder {
    public BingImageViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(Image image) {
        Context context = itemView.getContext();
        Picasso.with(context)
                .load(image.imageUrl());

    }
}
