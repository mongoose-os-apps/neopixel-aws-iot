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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.iotdata.AWSIotDataClient;
import com.mgenio.aws.mongooseos.neopixels.R;
import com.mgenio.aws.mongooseos.neopixels.adapters.PagerAdapter;
import com.mgenio.aws.mongooseos.neopixels.async.GetThingShadow;
import com.mgenio.aws.mongooseos.neopixels.async.UpdateDeviceShadow;
import com.mgenio.aws.mongooseos.neopixels.fragments.BlankFragment;
import com.mgenio.aws.mongooseos.neopixels.fragments.ThingFragment;
import com.mgenio.aws.mongooseos.neopixels.interfaces.OnGetThingShadow;
import com.mgenio.aws.mongooseos.neopixels.models.Thing;
import com.mgenio.aws.mongooseos.neopixels.utils.Constants;
import com.mgenio.aws.mongooseos.neopixels.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ThingDetailActivity extends AppCompatActivity implements OnGetThingShadow, ViewPager.OnPageChangeListener {

    private String thingName;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title) TextView tvToolbarTitle;
    @BindView(R.id.switch_state) Switch switchState;

    @BindView(R.id.bottom_sheet) View bottomSheet;
    private BottomSheetBehavior mBottomSheetBehavior;

    @BindView(R.id.pager_things) ViewPager pagerThings;
    private PagerAdapter pagerAdapter;

    private CognitoCachingCredentialsProvider credentialsProvider;
    public AWSIotDataClient iotDataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_detail);
        ButterKnife.bind(this);

        if (null != getIntent()) {
            thingName = getIntent().getStringExtra(Constants.EXTRA_THING_NAME);
        }

        Window window = getWindow();
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
            mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            switchState.setVisibility(View.GONE);
            bottomSheet.setVisibility(View.GONE);
        }
    }

    private void setupAwsIotCredentials() {
        credentialsProvider = new CognitoCachingCredentialsProvider(
                this,
                Constants.COGNITO_POOL_ID, // Identity Pool ID
                Constants.MY_REGION // Region
        );

        iotDataClient = new AWSIotDataClient(credentialsProvider);
        String iotDataEndpoint = Constants.CUSTOMER_SPECIFIC_ENDPOINT;
        iotDataClient.setEndpoint(iotDataEndpoint);
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
        String[] things = getResources().getStringArray(R.array.things);
        ArrayList<Fragment> fragments = new ArrayList<>();
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
        String newState = String.format("{\"state\":{\"desired\":{\"rgb\":{\"b\":%s,\"g\":%s,\"r\":%s}}}}", 0, 0, 255);
        new UpdateDeviceShadow(iotDataClient).execute(tvToolbarTitle.getText().toString(), newState);
    }

    @OnClick(R.id.btn_green)
    public void green() {
        setColor(Color.GREEN);
        String newState = String.format("{\"state\":{\"desired\":{\"rgb\":{\"b\":%s,\"g\":%s,\"r\":%s}}}}", 0, 255, 0);
        new UpdateDeviceShadow(iotDataClient).execute(tvToolbarTitle.getText().toString(), newState);
    }

    @OnClick(R.id.btn_blue)
    public void blue() {
        setColor(Color.BLUE);
        String newState = String.format("{\"state\":{\"desired\":{\"rgb\":{\"b\":%s,\"g\":%s,\"r\":%s}}}}", 255, 0, 0);
        new UpdateDeviceShadow(iotDataClient).execute(tvToolbarTitle.getText().toString(), newState);
    }

    @OnClick(R.id.fab_custom_color)
    public void customColor() {
        Toast.makeText(getApplicationContext(), "Custom colors not yet implemented. Change colors in colors.xml", Toast.LENGTH_LONG).show();
    }

    @OnCheckedChanged(R.id.switch_state)
    public void onOff(boolean isChecked) {
        String newState = String.format("{\"state\":{\"desired\":{\"on\":%s}}}", isChecked);
        new UpdateDeviceShadow(iotDataClient).execute(tvToolbarTitle.getText().toString(), newState);
    }

    private void setColor(int color) {
        ThingFragment fragment = (ThingFragment) pagerAdapter.getItem(pagerThings.getCurrentItem() + 1);
        if (null != fragment) {
            fragment.setColor(color);
        }
    }

    @Override public void onGetResult(Thing device) {
        if (null == device) {
            Snackbar.make(bottomSheet, "Device not found", Snackbar.LENGTH_LONG).show();
            switchState.setChecked(false);
            setColor(Color.WHITE);
            return;
        }

        JSONObject state = device.getState();

        try {
            JSONObject desiredState = state.getJSONObject("reported");
            JSONObject rgb = desiredState.getJSONObject("rgb");
            switchState.setChecked(desiredState.getBoolean("on"));

            setColor(Color.rgb(rgb.getInt("r"), rgb.getInt("g"), rgb.getInt("b")));
        } catch (JSONException e) {
            Snackbar.make(bottomSheet, "Could not parse device shadow", Snackbar.LENGTH_LONG).show();
            switchState.setChecked(false);
            setColor(Color.WHITE);
        }
    }

    @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override public void onPageSelected(int position) {
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

    @Override public void onPageScrollStateChanged(int state) {

    }
}
