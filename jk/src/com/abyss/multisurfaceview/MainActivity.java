package com.abyss.multisurfaceview;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abyss.Constants;
import com.abyss.R;

import org.w3c.dom.Text;

/**
 * Created by abyss on 2014. 5. 3..
 */
public class MainActivity extends Activity {
    Preview mPreview;
    BaseGLSurfaceView mGglView;
    RelativeLayout mView;
    TextView mTextView;
    Button shutterButton;
    Camera mCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        mView = (RelativeLayout) findViewById(R.id.preview);
        shutterButton = (Button) findViewById(R.id.shutter_button);



        mCamera = getCameraInstance();
        shutterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(mShutterCallback, null, mJpegCallback);
            }
        });
        mPreview = new Preview(this, mCamera);
        mGglView = new BaseGLSurfaceView(this);

//        mTextView = new TextView(this);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        mTextView.setText("On the top");
//        mTextView.setLayoutParams(layoutParams);

        mView.addView(mGglView);
        mView.addView(mPreview);
//        mView.addView(mTextView);

    }

    private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {

        }
    };

    private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            mCamera.startPreview();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mGglView.onResume();
//        mCamera = getCameraInstance();
//        shutterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCamera.takePicture(mShutterCallback, null, mJpegCallback);
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGglView.onPause();

        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

    }

    @Override
    protected void onDestroy() {
        Log.d(Constants.TAG, "onDestroy");
        super.onDestroy();

    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
