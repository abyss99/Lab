package com.abyss.translucent;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abyss.R;
import com.abyss.multisurfaceview.BaseGLSurfaceView;
import com.abyss.multisurfaceview.Preview;

/**
 * Created by abyss on 2014. 5. 3..
 */
public class MainActivity extends Activity {

    RelativeLayout mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_transparent);
        mView = (RelativeLayout)findViewById(R.id.preview);

        TransparentView tView = new TransparentView(this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        tView.setLayoutParams(layoutParams);
        mView.addView(tView);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
