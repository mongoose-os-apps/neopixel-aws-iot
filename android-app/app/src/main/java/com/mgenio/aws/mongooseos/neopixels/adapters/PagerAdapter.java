package com.mgenio.aws.mongooseos.neopixels.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Austin Nelson on 3/23/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();

    @Override
    public float getPageWidth(int position) {
        return 0.35f;
    }

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments, ArrayList<String> titles) {
        this.fragments.clear();
        this.fragments.addAll(fragments);
        this.titles.clear();
        this.titles.addAll(titles);
        notifyDataSetChanged();
    }

    public void add(Fragment fragment, String title) {
        this.fragments.add(fragment);
        this.titles.add(title);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).toUpperCase();
    }
}
