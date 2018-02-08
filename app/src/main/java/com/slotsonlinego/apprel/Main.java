package com.slotsonlinego.apprel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.slotsonlinego.apprel.data.ItemGame;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Main extends AppCompatActivity {
    private static final String TAG = Main.class.getSimpleName();

    private static final int MAX_COUNT = 10;

    private static final String STATE_RESULT_CODE = "result_code";
    private static final String STATE_RESULT_DATA = "result_data";

    private List<ItemGame> itemGames;

    private String data;
    private String key;

    private ProgressBar progressBar;

    private int mResultCode;
    private Intent mResultData;

    @Override
    public void onStart() {
        super.onStart();
        if (itemGames == null) {
            itemGames = new ArrayList<>();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mResultData != null) {
            outState.putInt(STATE_RESULT_CODE, mResultCode);
            outState.putParcelable(STATE_RESULT_DATA, mResultData);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mResultCode = savedInstanceState.getInt(STATE_RESULT_CODE, -1);
            mResultData = savedInstanceState.getParcelable(STATE_RESULT_DATA);
        }

        data = getString(R.string.opening_url);      // don't change value id
        key = getString(R.string.key_redirecting);                     // don't change value id

        itemGames = generateItems();

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        openWebView();
    }

    @Override
    public void onDestroy() {
        itemGames = null;
        super.onDestroy();
    }

    private List<ItemGame> generateItems() {
        List<ItemGame> itemGames = new ArrayList<>();
        for (int i = 0; i < MAX_COUNT; ++i) {
            itemGames.add(new ItemGame(UUID.randomUUID().toString()));
        }
        return itemGames;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void openWebView() {
        Log.d(TAG, "openWebView");
        progressBar.setVisibility(View.GONE);

        WebView webView = findViewById(R.id.web_view);
        webView.setWebViewClient(client());
        settings(webView.getSettings());
        webView.loadUrl(data);

        for(ItemGame item : itemGames) {
            item.setName("Local user");
            item.setTotal(mResultCode);
        }
    }

    @NonNull
    public static Intent getMainActivityIntent(Context context) {
        return new Intent(context, Main.class);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void openScreenGame() {
        Log.d(TAG, "openScreenGame");
        progressBar.setVisibility(View.GONE);
        startActivity(Game.getGameActivityIntent(this));
        overridePendingTransition(0,0);
        finish();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void settings(@NonNull WebSettings webSettings) {
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
    }

    @NonNull
    private WebViewClient client() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(key)) {
                    view.loadUrl(url);
                } else {
                    openScreenGame();
                }
                return true;
            }

            @RequiresApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (!request.getUrl().toString().contains(key)) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    openScreenGame();
                }
                return true;
            }
        };
    }
}
