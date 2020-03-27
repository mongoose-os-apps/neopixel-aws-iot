package com.mgenio.aws.mongooseos.neopixels.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgenio.aws.mongooseos.neopixels.R;

import butterknife.ButterKnife;

public class BlankFragment extends ThingFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance() {
        final BlankFragment fragment = new BlankFragment();
        final Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // ?
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_blank, container, false);
        ButterKnife.bind(this, v);
        return v;
    }
}
