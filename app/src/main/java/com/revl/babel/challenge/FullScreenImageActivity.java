package com.revl.babel.challenge;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FullScreenImageActivity extends Activity {


    public static final String URL_OF_IMAGE = "URL_OF_IMAGE";
    public static final String THUMBNAIL_OF_IMAGE = "THUMB_OF_IMAGE";
    @BindView(R.id.full_screen_image) ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.full_screen_image);

        ButterKnife.bind(this);

        String imageUrl = getIntent().getStringExtra(URL_OF_IMAGE);
        Picasso picasso = Picasso.with(this);
        picasso
                .load(imageUrl)
                .placeholder(R.drawable.animated_progress)
                .into(imageView);
    }
}
