package com.revl.babel.challenge;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.revl.babel.challenge.adapters.BingThumbnailAdapter;
import com.revl.babel.challenge.model.Images;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int COUNT = 50;
    private static final String SAFE_SEARCH = "Moderate";
    private static final String MARKET_EN_US = "en-us";

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private BingThumbnailAdapter recyclerViewAdapter = new BingThumbnailAdapter(this) {

        @Override
        protected void loadMoreItems(int offset) {
            loadImages(offset);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(recyclerViewAdapter);


        loadImages(0);
    }

    private void loadImages(int offset) {
        RevlApplication.getInstance().getApi()
                .searchForImages(
                        "kiteboarding",
                        offset,
                        COUNT,
                        MARKET_EN_US,
                        SAFE_SEARCH)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Images>() {
                    @Override
                    public void onSuccess(@NonNull Images response) {
                        Log.d(TAG, "MainActivity.onSuccess response = " + response);

                        recyclerViewAdapter.getImages().addAll(response.images());
                        recyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    e.printStackTrace();
                }
                });
    }
}
