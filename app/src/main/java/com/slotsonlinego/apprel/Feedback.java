package com.slotsonlinego.apprel;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;


public class Feedback extends AppCompatActivity {
    private static final String TAG = Feedback.class.getSimpleName();

    static  class SamplePagerItem {
        private final CharSequence mTitle;
        private final int mIndicatorColor;
        private final int mDividerColor;

        SamplePagerItem(CharSequence title, int indicatorColor, int dividerColor) {
            mTitle = title;
            mIndicatorColor = indicatorColor;
            mDividerColor = dividerColor;
        }


        Fragment createFragment() {
            return null;
        }


        CharSequence getTitle() {
            return mTitle;
        }


        int getIndicatorColor() {
            return mIndicatorColor;
        }


        int getDividerColor() {
            return mDividerColor;
        }
    }

    static final String LOG_TAG = "SlidingTabsColorsFragment";


    private ViewPager mViewPager;

    private List<SamplePagerItem> mTabs = new ArrayList<SamplePagerItem>();


    /**

     */
    class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

        SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the {@link android.support.v4.app.Fragment} to be displayed at {@code position}.
         * <p>
         * Here we return the value returned from {@link SamplePagerItem#createFragment()}.
         */
        @Override
        public Fragment getItem(int i) {
            return mTabs.get(i).createFragment();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position).getTitle();
        }

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_feedback);

        // BEGIN_INCLUDE (populate_tabs)
        /**
         * Populate our tab list with tabs. Each item contains a title, indicator color and divider
         * color, which are used by {@link SlidingTabLayout}.
         */
        mTabs.add(new SamplePagerItem(
                "one", // Title
                Color.BLUE, // Indicator color
                Color.GRAY // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                "second", // Title
                Color.RED, // Indicator color
                Color.GRAY // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                "next" ,// Title
                Color.YELLOW, // Indicator color
                Color.GRAY // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                "close", // Title
                Color.GREEN, // Indicator color
                Color.GRAY // Divider color
        ));
        // END_INCLUDE (populate_tabs)

        ArrayList<ChatMessage> messages = new ArrayList<>();

        String[] names = getResources().getStringArray(R.array.users);
        String[] comments = getResources().getStringArray(R.array.comments);

        for (int i = 0; i < names.length; ++i) {
            ChatMessage message = new ChatMessage(comments[i],
                    names[i],
                    System.currentTimeMillis(),
                    ChatMessage.Type.RECEIVED);
            message.setTimestamp(System.currentTimeMillis());
            messages.add(message);
        }

        ChatView view = findViewById(R.id.chat_view);
        view.addMessages(messages);
        view.setOnSentMessageListener(chatMessage -> {
            chatMessage.setUserName(getString(R.string.you));
            chatMessage.setTimestamp(System.currentTimeMillis());
            return true;
        });
    }

    @NonNull
    public static Intent getFeedbackActivityIntent(Context context) {
        return new Intent(context, Feedback.class);
    }
}
