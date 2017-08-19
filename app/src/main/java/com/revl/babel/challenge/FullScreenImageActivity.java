package com.revl.babel.challenge;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.revl.babel.challenge.model.Image;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FullScreenImageActivity extends Activity {

    public static final String IMAGE_PARCEL = "IMAGE_PARCEL";

    @BindView(R.id.full_screen_image) ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.full_screen_image);

        ButterKnife.bind(this);

        Image image = getIntent().getParcelableExtra(IMAGE_PARCEL);
        RequestOptions options = new RequestOptions();
        options.error(R.drawable.broken_image);
        Glide.with(this)
                .load(image.imageUrl())
                .apply(options)
                .into(imageView);

        imageView.getRootView().setBackgroundColor(Color.parseColor("#"+image.accentColor()));
    }
}
