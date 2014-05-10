package com.abyss.multisurfaceview;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by abyss on 2014. 5. 3..
 */
public class SquareRenderer implements GLSurfaceView.Renderer {
    private boolean mTranslucentBackground;
    private Square mSquare;
    private float mTransY;
    private float mAngle;

    public SquareRenderer(boolean useTranslucentBackground) {
        mTranslucentBackground = useTranslucentBackground;
        mSquare = new Square();
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_FASTEST);
        if (mTranslucentBackground) {
            gl.glClearColor(0, 0, 0, 0);
        } else {

            gl.glClearColor(1, 1, 1, 1);
        }
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);                               //12
        float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);                              //13
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);     //5
        gl.glMatrixMode(GL10.GL_MODELVIEW);                                  //6
        gl.glLoadIdentity();                                                 //7
        gl.glTranslatef(0.0f, (float) Math.sin(mTransY), -3.0f);               //8
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);                        //9
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        mSquare.draw(gl);                                                    //10
        mTransY += .075f;
    }
}
