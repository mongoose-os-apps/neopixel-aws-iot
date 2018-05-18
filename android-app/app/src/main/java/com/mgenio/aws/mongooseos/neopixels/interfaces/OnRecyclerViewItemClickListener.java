package com.mgenio.aws.mongooseos.neopixels.interfaces;

import android.view.View;

/**
 * Created by anelson on 1/7/16.
 */
public interface OnRecyclerViewItemClickListener<T> {
    void onItemClick(View view, T model);
}
