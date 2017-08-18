package com.revl.babel.challenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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
    private static final String ANIMATED_IMAGES = "AnimatedGif";
    public static final String KITEBOARDING = "kiteboarding";

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private BingThumbnailAdapter recyclerViewAdapter = new BingThumbnailAdapter(this) {

        @Override
        protected void loadMoreItems(int offset) {
            loadImages(offset);
        }
    };
    private String searchString = KITEBOARDING;

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
                        searchString,
                        offset,
                        COUNT,
                        MARKET_EN_US,
                        ANIMATED_IMAGES,
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.change_search_string:

                showEditSearchDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEditSearchDialog() {
        final EditText editText = new EditText(this);
        editText.setHint("New search string");
        new AlertDialog.Builder(this)
                .setTitle("Change search")
                .setView(editText)
        .setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                searchString = editText.getText().toString();
                recyclerViewAdapter.getImages().clear();
                loadImages(0);
            }
        })
        .show();
    }
}
