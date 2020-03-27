package com.mgenio.aws.mongooseos.neopixels.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mgenio.aws.mongooseos.neopixels.R;
import com.mgenio.aws.mongooseos.neopixels.adapters.ThingAdapter;
import com.mgenio.aws.mongooseos.neopixels.interfaces.OnRecyclerViewItemClickListener;
import com.mgenio.aws.mongooseos.neopixels.models.ThingData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mgenio.aws.mongooseos.neopixels.utils.Constants.EXTRA_THING_NAME;

public class ThingListActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener<ThingData> {
    @BindView(R.id.rv_devices)
    RecyclerView rvDevices;
    private ThingAdapter thingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        ButterKnife.bind(this);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.gradient_end));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.gradient_end));
        }

        setupAdapter();
        loadDevicesFromStringArray();

        if (thingAdapter.getItemCount() == 0) {
            Snackbar.make(rvDevices, "No devices listed. List them in strings.xml", Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    @OnClick(R.id.fab_add_device)
    public void addDevice() {

    }

    private void setupAdapter() {
        thingAdapter = new ThingAdapter(this);
        rvDevices.setLayoutManager(new LinearLayoutManager(this));
        rvDevices.setAdapter(thingAdapter);

        thingAdapter.setOnItemClickListener(this);
    }

    private void loadDevicesFromStringArray() {
        String[] things = getResources().getStringArray(R.array.things);
        ArrayList<ThingData> thingList = new ArrayList<>();
        for (String thing : things) {
            thingList.add(new ThingData(thing));
        }
        thingAdapter.setModels(thingList);
    }

    @Override
    public void onItemClick(View view, ThingData model) {
        final Intent intent = new Intent(ThingListActivity.this, ThingDetailActivity.class);
        intent.putExtra(EXTRA_THING_NAME, model.getName());
        startActivity(intent);
    }
}
