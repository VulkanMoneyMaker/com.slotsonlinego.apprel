package com.slotsonlinego.apprel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.slotsonlinego.apprel.spins.src.kankan.wheel.widget.adapters.AbstractWheelAdapter;

import java.util.ArrayList;


public class Adapter extends AbstractWheelAdapter {
    private LayoutInflater mLayoutInflater;
    private ArrayList<Integer> mListImages;
    private Context mContext;

    @SuppressLint("WrongConstant")
    public Adapter(Context mContext, ArrayList<Integer> list) {
        this.mContext = mContext;
        this.mListImages = list;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService("layout_inflater");
    }

    @Override
    public int getItemsCount() {
        return mListImages == null ? 0 : mListImages.size();
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        View view = this.mLayoutInflater.inflate(R.layout.item_list, parent, false);
        ((ImageView) view.findViewById(R.id.image)).setImageDrawable(
                ContextCompat.getDrawable(this.mContext, this.mListImages.get(index))
        );
        return view;
    }
}
