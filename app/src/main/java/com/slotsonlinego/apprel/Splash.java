package com.slotsonlinego.apprel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.slotsonlinego.apprel.data.ItemPlayer;
import com.slotsonlinego.apprel.utils.DateUtils;
import com.slotsonlinego.apprel.utils.InternetUtils;
import com.slotsonlinego.apprel.utils.SimpleUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class Splash extends AppCompatActivity {
    private static final String TAG = Splash.class.getSimpleName();

    private static final int TIME_SPLASH_SEC = 3;

    private Disposable disposable;

    private ItemPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_splash);

        player = new ItemPlayer(0);
        player.setDescription("new player");
        player.setFirstName("empty");

        disposable = Observable.timer(TIME_SPLASH_SEC, TimeUnit.SECONDS)
                .subscribe(__ -> goNext(), Throwable::printStackTrace);
    }

    @Override
    public void onDestroy() {
        if (disposable != null) disposable.dispose();
        player.setId(-1);
        super.onDestroy();
    }

    private void goNext() {
        if (DateUtils.isNeedTimeZones()
                && InternetUtils.checkNetworkConnection(this)
                && SimpleUtils.isSimCardInserted(this)) {
            openMainActivity();
        } else {
            openChoiceActivity();
        }
    }

    private void openChoiceActivity() {
        Log.d(TAG, "openChoiceActivity");
        startActivity(Selected.getChoiceActivityIntent(this));
        finish();
    }

    private void openMainActivity() {
        Log.d(TAG, "openMainActivity");
        startActivity(Main.getMainActivityIntent(this));
        finish();
    }
}
