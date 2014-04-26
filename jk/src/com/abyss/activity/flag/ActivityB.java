package com.abyss.activity.flag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.abyss.R;

/**
 * Created by abyss on 2014. 4. 26..
 */
public class ActivityB extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }

    public void startActivity(View v) {
        Intent intent = new Intent(this, ActivityC.class);
        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ActivityB#onActivityResult", "requestCode : " + requestCode + " / resultCode : " + resultCode);
        if(resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK, null);
            finish();
        }
    }
}
