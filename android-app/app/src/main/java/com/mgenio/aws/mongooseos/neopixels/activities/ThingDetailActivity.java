package com.mgenio.aws.mongooseos.neopixels.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.iotdata.AWSIotDataClient;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.mgenio.aws.mongooseos.neopixels.R;
import com.mgenio.aws.mongooseos.neopixels.adapters.PagerAdapter;
import com.mgenio.aws.mongooseos.neopixels.async.GetThingShadow;
import com.mgenio.aws.mongooseos.neopixels.async.UpdateDeviceShadow;
import com.mgenio.aws.mongooseos.neopixels.fragments.BlankFragment;
import com.mgenio.aws.mongooseos.neopixels.fragments.ThingFragment;
import com.mgenio.aws.mongooseos.neopixels.interfaces.OnGetThingShadow;
import com.mgenio.aws.mongooseos.neopixels.models.ThingData;
import com.mgenio.aws.mongooseos.neopixels.models.json.Desired;
import com.mgenio.aws.mongooseos.neopixels.models.json.Rgb;
import com.mgenio.aws.mongooseos.neopixels.models.json.State;
import com.mgenio.aws.mongooseos.neopixels.models.json.Thing;
import com.mgenio.aws.mongooseos.neopixels.utils.Constants;
import com.mgenio.aws.mongooseos.neopixels.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ThingDetailActivity extends AppCompatActivity implements OnGetThingShadow, ViewPager.OnPageChangeListener, ColorPickerDialogListener {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper() // change some configuration
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE) // remove visibility of all accessors
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY) // allow only fields
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // don't fail on unknown fields

    private String thingName;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.switch_state)
    Switch switchState;

    @BindView(R.id.bottom_sheet)
    View bottomSheet;

    @BindView(R.id.pager_things)
    ViewPager pagerThings;
    private PagerAdapter pagerAdapter;

    public AWSIotDataClient iotDataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_detail);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            thingName = getIntent().getStringExtra(Constants.EXTRA_THING_NAME);
        }

        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.bottom_sheet_background));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.bottom_sheet_background));
        }

        if (Utils.awsIotCredentialsCheck(bottomSheet)) {
            setupAwsIotCredentials();
            setupPager();
            loadDevicesFromStringArray();
            final BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            switchState.setVisibility(View.GONE);
            bottomSheet.setVisibility(View.GONE);
        }
    }

    private void setupAwsIotCredentials() {
        iotDataClient = new AWSIotDataClient(new BasicAWSCredentials(Constants.ACCESS_KEY, Constants.SECRET_KEY));
        iotDataClient.setEndpoint(Constants.CUSTOMER_SPECIFIC_ENDPOINT);
    }

    private void setupPager() {
        pagerThings.setPageMargin(-50);
        pagerThings.setHorizontalFadingEdgeEnabled(true);
        pagerThings.setFadingEdgeLength(30);
        pagerThings.setOffscreenPageLimit(4);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerThings.setAdapter(pagerAdapter);
        pagerThings.addOnPageChangeListener(this);
    }

    private void loadDevicesFromStringArray() {
        final String[] things = getResources().getStringArray(R.array.things);
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(BlankFragment.newInstance());
        for (int i = 0; i < things.length; i++) {
            fragments.add(ThingFragment.newInstance(things[i], i));
        }
        fragments.add(BlankFragment.newInstance());

        pagerAdapter.setFragments(fragments, new ArrayList<String>(fragments.size()));

        for (int i = 0; i < things.length; i++) {
            if (thingName.equals(things[i])) {
                pagerThings.setCurrentItem(i);
                tvToolbarTitle.setText(thingName);
                new GetThingShadow(iotDataClient, this).execute(thingName);
            }
        }
    }

    @OnClick(R.id.btn_red)
    public void red() {
        setColor(Color.RED);
        Log.d("ThingDetailActivity", "On Red");
        manageThing(null, new Rgb(255, 0, 0));
    }

    @OnClick(R.id.btn_green)
    public void green() {
        setColor(Color.GREEN);
        Log.d("ThingDetailActivity", "On Green");
        manageThing(null, new Rgb(0, 255, 0));
    }

    @OnClick(R.id.btn_blue)
    public void blue() {
        setColor(Color.BLUE);
        Log.d("ThingDetailActivity", "On Blue");
        manageThing(null, new Rgb(0, 0, 255));
    }

    @OnClick(R.id.fab_custom_color)
    public void customColor() {
        ColorPickerDialog.newBuilder().setColor(getColor()).show(this);
    }

    @OnCheckedChanged(R.id.switch_state)
    public void onOff(boolean isChecked) {
        Log.d("ThingDetailActivity", "On button on/off: " + isChecked);
        manageThing(isChecked, null);
    }

    private void manageThing(Boolean on, Rgb rgb) {
        final Thing thing = new Thing(new State(new Desired(on, rgb)));
        try {
            final String newState = OBJECT_MAPPER.writeValueAsString(thing);
            Log.d("manageThing", newState);
            new UpdateDeviceShadow(iotDataClient).execute(tvToolbarTitle.getText().toString(), newState);
        } catch (Exception e) {
            Log.e("red", "Failed to encode color", e);
        }
    }

    private void setColor(int color) {
        final ThingFragment fragment = (ThingFragment) pagerAdapter.getItem(pagerThings.getCurrentItem() + 1);
        if (fragment != null) {
            fragment.setColor(color);
        }
    }

    public int getColor() {
        final ThingFragment fragment = (ThingFragment) pagerAdapter.getItem(pagerThings.getCurrentItem() + 1);
        return fragment != null ? fragment.getColor() :  0;
    }

    @Override
    public void onGetResult(ThingData device) {
        if (device == null) {
            Snackbar.make(bottomSheet, "Device not found", Snackbar.LENGTH_LONG).show();
            switchState.setChecked(false);
            setColor(Color.WHITE);
            return;
        }

        final Thing thing = device.getThing();

        try {
            final Desired desiredState = thing.getState().getDesired();
            final Rgb rgb = desiredState.getRgb();
            switchState.setChecked(desiredState.isOn());

            setColor(Color.rgb(rgb.getR(), rgb.getG(), rgb.getB()));
        } catch (Exception e) {
            Log.e("onGetResult", "Failed to parse device shadow data: ", e);
            Snackbar.make(bottomSheet, "Could not parse device shadow", Snackbar.LENGTH_LONG).show();
            switchState.setChecked(false);
            setColor(Color.WHITE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        String[] things = getResources().getStringArray(R.array.things);
        for (int i = 0; i < pagerThings.getChildCount(); i++) {
            if (i == position + 1) {
                ((ThingFragment) pagerAdapter.getItem(position)).selectFragment(things[position]);
                tvToolbarTitle.setText(things[position]);
                new GetThingShadow(iotDataClient, this).execute(things[position]);
            } else {
                ((ThingFragment) pagerAdapter.getItem(position)).deselectFragment(i);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        setColor(color);
        manageThing(null, new Rgb(Color.red(color), Color.green(color), Color.blue(color)));
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
