package com.abyss.camera;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.abyss.R;

/**
 * Created by abyss on 2014. 5. 10..
 */
public class CameraActivity extends Activity {
	private String TAG = CameraActivity.class.getSimpleName();
	private Camera mCamera;
	private CameraPreview mPreview;

    private void setContinuous() {
        Camera.Parameters params = mCamera.getParameters();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(params);
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_test);

		// Create an instance of Camera

	}

    @Override
    protected void onResume() {
        super.onResume();
        mCamera = getCameraInstance();
//        Camera.Parameters params = mCamera.getParameters();
//        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//        mCamera.setParameters(params);
        setContinuous();
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout)findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        changeSize();
        Button captureButton = (Button)findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get an image from the camera
//                mCamera.takePicture(null, null, mPicture);
                movePosition();
            }
        });
    }

    private void movePosition() {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)mPreview.getLayoutParams();
        params.leftMargin += 30;
        mPreview.setLayoutParams(params);
    }

    private void changeSize() {
        ViewGroup.LayoutParams params = mPreview.getLayoutParams();
        params.width = 500;
        params.height = 800;
        mPreview.setLayoutParams(params);
    }

    @Override
	protected void onPause() {
		super.onPause();
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
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

	private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken");
		}
	};

	public void touchFocus(final int posX, final int posY) {
		mCamera.stopFaceDetection();
        setFocus(posX, posY);
		mCamera.autoFocus(mAutoFocusCallback);
	}

    private void setFocus(int posX, int posY) {
        Log.d(TAG, "posX : posY = " + posX + " : " + posY);
        Rect rect = new Rect(-1000, -1000, -999, -999);
        List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
        focusAreas.add(new Camera.Area(rect, 1000));

        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
        parameters.setFocusAreas(focusAreas);
        parameters.setMeteringAreas(focusAreas);

        mCamera.setParameters(parameters);

        Log.d(TAG, mCamera.getParameters().getFocusMode());
    }

	private void setAutoFocusArea(Camera camera, int posX, int posY, int focusRange, boolean flag, Point point) {

		if (posX < 0 || posY < 0) {
			setArea(camera, null);
			return;
		}

		int touchPointX;
		int touchPointY;
		int endFocusY;
		int startFocusY;

		if (!flag) {
			/** Camera.setDisplayOrientation()을 이용해서 영상을 세로로 보고 있는 경우. **/
			touchPointX = point.y >> 1;
			touchPointY = point.x >> 1;

			startFocusY = posX;
			endFocusY = posY;
		} else {
			/** Camera.setDisplayOrientation()을 이용해서 영상을 가로로 보고 있는 경우. **/
			touchPointX = point.x >> 1;
			touchPointY = point.y >> 1;

			startFocusY = posY;
			endFocusY = point.x - posX;
		}

		float startFocusX = 1000F / (float)touchPointY;
		float endFocusX = 1000F / (float)touchPointX;

		/** 터치된 위치를 기준으로 focusing 영역으로 사용될 영역을 구한다. **/
		startFocusX = (int)(startFocusX * (float)(startFocusY - touchPointY)) - focusRange;
		startFocusY = (int)(endFocusX * (float)(endFocusY - touchPointX)) - focusRange;
		endFocusX = startFocusX + focusRange;
		endFocusY = startFocusY + focusRange;

		if (startFocusX < -1000)
			startFocusX = -1000;

		if (startFocusY < -1000)
			startFocusY = -1000;

		if (endFocusX > 1000) {
			endFocusX = 1000;
		}

		if (endFocusY > 1000) {
			endFocusY = 1000;
		}

		/*
		 * 주의 : Android Developer 예제 소스 처럼 ArrayList에 Camera.Area를 2개 이상 넣게 되면
		 *          에러가 발생되는 것을 경험할 수 있을 것입니다.
		 **/
		Rect rect = new Rect((int)startFocusX, (int)startFocusY, (int)endFocusX, (int)endFocusY);
		ArrayList<Camera.Area> arraylist = new ArrayList<Camera.Area>();
		arraylist.add(new Camera.Area(rect, 1000)); // 지정된 영역을 100%의 가중치를 두겠다는 의미입니다.

		setArea(camera, arraylist);
	}

	private void setArea(Camera camera, List<Camera.Area> list) {
		boolean enableFocusModeMacro = true;

		Camera.Parameters parameters;
		parameters = camera.getParameters();

		int maxNumFocusAreas = parameters.getMaxNumFocusAreas();
		int maxNumMeteringAreas = parameters.getMaxNumMeteringAreas();

		if (maxNumFocusAreas > 0) {
			parameters.setFocusAreas(list);
		}

		if (maxNumMeteringAreas > 0) {
			parameters.setMeteringAreas(list);
		}

		if (list == null || maxNumFocusAreas < 1 || maxNumMeteringAreas < 1) {
			enableFocusModeMacro = false;
		}

//		if (enableFocusModeMacro == true) {
//			/*
//			 * FOCUS_MODE_MACRO을 사용하여 근접 촬영이 가능하도록 해야
//			 * 지정된 Focus 영역으로 초점이 좀더 선명하게 잡히는 것을 볼 수 있습니다.
//			 */
//			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
//			Log.d(TAG, "focus mode macro");
//		} else {
//			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//			Log.d(TAG, "focus mode auto");
//		}
		camera.setParameters(parameters);
	}

	Camera.AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback() {

		@Override
		public void onAutoFocus(boolean success, Camera camera) {
            Log.d(TAG, ""+success);
			if (success) {
				mCamera.cancelAutoFocus();
                setContinuous();
			}
		}
	};
}
