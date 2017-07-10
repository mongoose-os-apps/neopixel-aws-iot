package com.mgenio.aws.mongooseos.neopixels.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mgenio.aws.mongooseos.neopixels.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ThingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private int mPos;

    @Nullable
    @BindView(R.id.iv_thing) ImageView ivThing;

    public ThingFragment() {
        // Required empty public constructor
    }

    public static ThingFragment newInstance(String param1, int pos) {
        ThingFragment fragment = new ThingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mPos = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thing, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    public void setColor(int color) {
        ivThing.setColorFilter(color);
    }

    public void selectFragment(String thingName) {

    }

    public void deselectFragment(int i) {

    }

}
