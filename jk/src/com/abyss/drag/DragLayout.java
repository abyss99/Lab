package com.abyss.drag;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.abyss.R;

/**
 * Created by CA on 2014. 8. 6..
 */
public class DragLayout extends LinearLayout {
    private String TAG = DragLayout.class.getSimpleName();
    private final ViewDragHelper mDragHelper;

    private View mDragView1;

    private boolean mDragEdge;
    private boolean mDragHorizontal;
    private boolean mDragCapture;
    private boolean mDragVertical;

    private int mDragRange;
    private int mTop;
    private float mDragOffset;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
    }

//    public DragLayout(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//
//    }

    @Override
    protected void onFinishInflate() {
        mDragView1 = findViewById(R.id.drag1);
    }

    public void setDragHorizontal(boolean dragHorizontal) {
        mDragHorizontal = dragHorizontal;
    }

    public void setDragVertical(boolean dragVertical) {
        mDragVertical = dragVertical;
    }

    public void setDragEdge(boolean dragEdge) {
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
        mDragEdge = dragEdge;
    }

    public void setDragCapture(boolean dragCapture) {
        mDragCapture = dragCapture;
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.d(TAG, "tryCaptureView");
            return child == mDragView1;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.d(TAG, "onViewPositionChanged");
            mTop = top;
            mDragOffset = (float) top / mDragRange;

            invalidate();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            Log.d(TAG, "onViewCaptured");
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.d(TAG, "onViewReleased");
//            mDragHelper.settleCapturedViewAt(0, 0);
            mDragHelper.smoothSlideViewTo(mDragView1, 0, 0);
            invalidate();
        }


        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            Log.d(TAG, "onEdgeTouched");
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            Log.d(TAG, "onEdgeDragStarted");
            if (mDragEdge) {
                mDragHelper.captureChildView(mDragView1, pointerId);
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.d(TAG, "clampViewPositionVertical");
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mDragView1.getHeight();

            final int newTop = Math.min(Math.max(top, topBound), bottomBound);

            return newTop;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.d(TAG, "clampViewPositionHorizontal");
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - mDragView1.getWidth();

            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

            return newLeft;
        }


    }

    @Override
    public void computeScroll() {
        Log.d(TAG, "computeScroll : " + mDragHelper.continueSettling(true));
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent");
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onTouchEvent");
        mDragHelper.processTouchEvent(ev);
        return true;
    }


}