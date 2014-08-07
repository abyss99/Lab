package com.abyss.viewpager;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by CA on 2014. 8. 7..
 */
public class SmoothAutoRollingViewPager extends ViewPager {
    private float mRatio = 1;
    private static final String NAME_SPACE = "http://schemas.android.com/apk/res-auto";
    private Scroller mScroller;
    private boolean mIsSmoothScroll = false;

    public SmoothAutoRollingViewPager(Context context) {
        super(context);
    }

    public SmoothAutoRollingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttrs(attrs);
        setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mAutoRollingHandler != null) {
                    mAutoRollingHandler.removeCallbacks(callback);
                    mAutoRollingHandler.postDelayed(callback, mRollingDelay);
                }
                return false;
            }
        });
    }

    private void parseAttrs(AttributeSet attrs) {
        if (attrs != null) {
            mRatio = attrs.getAttributeFloatValue(NAME_SPACE, "ratio", 1);
        }

        mScroller = new Scroller(getContext(), new Interpolator() {
            public float getInterpolation(float t) {
                t -= 1.0f;
                return t * t * t * t * t + 1.0f;
            }
        });
    }

    public void scrollToNextItem(int duration) {
        if (duration < 0) {
            duration = 0;
        }
        mIsSmoothScroll = true;
        mScroller.startScroll(getScrollX(), 0, getWidth(), 0, duration);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public void computeScroll() {

        if (!mIsSmoothScroll) {
            super.computeScroll();
            return;
        }
        if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {

            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();

            if (oldX != x || oldY != y) {
                scrollTo(x, y);
            }

            ViewCompat.postInvalidateOnAnimation(this);
            return;
        }
        setCurrentItem(getCurrentItem() + 1);
        mIsSmoothScroll = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getLayoutParams().height != android.view.ViewGroup.LayoutParams.MATCH_PARENT) {
            int targetHeight = (int) (getMeasuredWidth() * mRatio);
            super.onMeasure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(targetHeight, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initHandler();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            initHandler();
        } else {
            interruptHandler();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        interruptHandler();
    }

    private boolean mAttached;
    private Handler mAutoRollingHandler;
    private int mRollingDelay = 5000;
    private static final int ROLLING_VELOCITY = 1000;

    private void interruptHandler() {
        if (mAttached) {
            mAttached = false;
            if (mAutoRollingHandler != null) {
                mAutoRollingHandler.removeCallbacks(callback);
            }
        }
    }

    private void initHandler() {
        if (mAutoRollingHandler == null && getAdapter().getCount() > 1) {
            mAutoRollingHandler = new Handler();
            mAutoRollingHandler.postDelayed(callback, mRollingDelay);
        }
    }

    private Runnable callback = new Runnable() {
        @Override
        public void run() {
            scrollToNextItem(ROLLING_VELOCITY);
            mAutoRollingHandler.postDelayed(callback, mRollingDelay);
        }
    };

}

