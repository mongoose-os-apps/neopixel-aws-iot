package com.mgenio.aws.mongooseos.neopixels.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mgenio.aws.mongooseos.neopixels.R;
import com.mgenio.aws.mongooseos.neopixels.interfaces.OnRecyclerViewItemClickListener;
import com.mgenio.aws.mongooseos.neopixels.models.ThingData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anelson on 1/7/16.
 */
public class ThingAdapter extends RecyclerView.Adapter<ThingAdapter.ViewHolder> implements View.OnClickListener {
    private List<ThingData> models;
    private static Activity mActivity;

    //animation
    private boolean animateItems = false;
    private static final int ANIMATED_ITEMS_COUNT = 7;
    private int lastAnimatedPosition = -1;
    private OnRecyclerViewItemClickListener<ThingData> itemClickListener;

    public ThingAdapter(Activity activity) {
        this.models = new ArrayList<>();
        this.mActivity = activity;
    }

    public ThingAdapter(Activity activity, List<ThingData> models) {
        this.models = models;
        this.mActivity = activity;
        notifyDataSetChanged();
    }

    public void setModels(ArrayList<ThingData> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_thing, viewGroup, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ThingData model = models.get(i);
        viewHolder.itemView.setTag(model);

        //viewHolder.btnThingImage.setText('E');
        viewHolder.tvThingName.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_thing_image)
        Button btnThingImage;
        @BindView(R.id.tv_thing_name)
        TextView tvThingName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public ThingData getItemAt(int pos) {
        return models.get(pos);
    }

    public void clear() {
        models.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<ThingData> listener) {
        this.itemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            ThingData model = (ThingData) v.getTag();
            itemClickListener.onItemClick(v, model);
        }
    }
}
