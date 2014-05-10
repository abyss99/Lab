package com.abyss.multisurfaceview;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.abyss.Constants;

import java.io.IOException;
import java.util.List;

/**
 * Created by abyss on 2014. 5. 3..
 */
public class Preview extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    Camera mCamera;

    public Preview(Context context, Camera camera) {
        super(context);

        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        Log.d(Constants.TAG, "Supported Preview Size");
        for (Camera.Size size : previewSizes) {
            Log.d(Constants.TAG, "width : height = " + size.width + " : " + size.height);
        }

        Log.d(Constants.TAG, "Supported Picture Size");
        List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
        for (Camera.Size size : pictureSizes) {
            Log.d(Constants.TAG, "width : height = " + size.width + " : " + size.height);
        }

        parameters.setPreviewSize(1920, 1080);

        try {
            mCamera.setParameters(parameters);
            mCamera.setPreviewDisplay(holder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();

        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;

        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


//        parameters.setPreviewSize(width, height);
//        mCamera.setParameters(parameters);

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
        }

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(Constants.TAG, "Error starting camera preview: " + e.getMessage());
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
