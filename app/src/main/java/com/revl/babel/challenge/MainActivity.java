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

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private BingThumbnailAdapter recyclerViewAdapter = new BingThumbnailAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(recyclerViewAdapter);

        RevlApplication.getInstance().getApi()
                .searchForImages("snowboarding")
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
