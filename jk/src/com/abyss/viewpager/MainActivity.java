package com.abyss.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.abyss.R;

import java.util.ArrayList;

/**
 * Created by CA on 2014. 8. 7..
 */
public class MainActivity extends Activity {
    SmoothAutoRollingViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        TestAdapter testAdapter = new TestAdapter(this, new ArrayList<String>());

        mViewPager = (SmoothAutoRollingViewPager)findViewById(R.id.view_pager);
        mViewPager.setAdapter(testAdapter);
        mViewPager.setCurrentItem(testAdapter.MAX_PAGER_ADAPTER_COUNT / 2);
        mViewPager.setOffscreenPageLimit(3);
    }
}
