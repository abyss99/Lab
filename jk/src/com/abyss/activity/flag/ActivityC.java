package com.abyss.activity.flag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.abyss.R;

/**
 * Created by abyss on 2014. 4. 26..
 */
public class ActivityC extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
    }

    public void finishActivity(View v) {
        setResult(Activity.RESULT_OK, null);
        finish();
    }
}
