package com.slotsonlinego.apprel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class Selected extends AppCompatActivity {
    private static final String TAG = Selected.class.getSimpleName();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_choice);

        findViewById(R.id.btn_choice_play).setOnClickListener(__ -> {
            openScreenGameActivity();
        });

        findViewById(R.id.btn_choice_feedback).setOnClickListener(__ -> {
            openFeedBackActivity();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       return super.onCreateOptionsMenu(menu);
    }

    private void openScreenGameActivity() {
        Log.d(TAG, "openScreenMainActivity");
        startActivity(Game.getGameActivityIntent(this));
        finish();
    }

    private void openFeedBackActivity() {
        Log.d(TAG, "openFeedBackActivity");
        startActivity(Feedback.getFeedbackActivityIntent(this));
    }

    @NonNull
    public static Intent getChoiceActivityIntent(Context context) {
        return new Intent(context, Selected.class);
    }
}
