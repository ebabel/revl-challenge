package com.revl.babel.challenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.revl.babel.challenge.adapters.BingThumbnailAdapter;
import com.revl.babel.challenge.model.Images;
import com.revl.babel.challenge.util.RevlSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int COUNT = 50;
    private static final String SAFE_SEARCH = "Moderate";
    private static final String MARKET_EN_US = "en-us";
    private static final String ANIMATED_IMAGES = "AnimatedGif";

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private BingThumbnailAdapter recyclerViewAdapter = new BingThumbnailAdapter(this) {

        @Override
        protected void loadMoreItems(int offset) {
            loadImages(offset);
        }
    };
    private boolean includeAnimated;
    private Disposable serviceDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTransitionAnimation();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(recyclerViewAdapter);

        loadImages(0);

    }

    private void setupTransitionAnimation() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
    }

    private void loadImages(int offset) {
        serviceDisposable = RevlApplication.getInstance().getApi()
                .searchForImages(
                        RevlSharedPreferences.getSearchString(),
                        offset,
                        COUNT,
                        MARKET_EN_US,
                        includeAnimated ? ANIMATED_IMAGES : null,
                        SAFE_SEARCH)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Images>() {
                    @Override
                    public void onSuccess(@NonNull Images response) {
                        Log.d(TAG, "MainActivity.onSuccess response = " + response);

                        recyclerViewAdapter.getImages().addAll(response.images());
                        recyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(MainActivity.this, "An error has occurred.", Toast.LENGTH_SHORT).show();

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
            case R.id.toggle_animated:
                toggleAnimated();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleAnimated() {
        includeAnimated = !includeAnimated;

        Toast.makeText(this, includeAnimated ? "Search includes animations" : "Normal photos", Toast.LENGTH_SHORT).show();

        clearAndRefetchImages();
    }

    private void clearAndRefetchImages() {
        recyclerViewAdapter.getImages().clear();
        loadImages(0);
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
                RevlSharedPreferences.saveSearchString(editText.getText().toString());
                clearAndRefetchImages();
            }
        })
        .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serviceDisposable.dispose();
    }

}
