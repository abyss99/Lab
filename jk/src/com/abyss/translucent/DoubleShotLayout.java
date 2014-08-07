package com.abyss.translucent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abyss on 2014. 5. 12..
 */
public class DoubleShotLayout extends View {
	private String TAG = DoubleShotLayout.class.getSimpleName();

	Canvas mCanvas;
	Canvas mInitalCanvas = null;

	//활성화 된 상태
	//텍스쳐 입혀진 상태
	//사진이 찍힌 상태

	public DoubleShotLayout(Context context) {
		super(context);
	}

	public DoubleShotLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DoubleShotLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private Bitmap makeBitmap(int width, int height) {
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		return bitmap;
	}

	private Rect makeRect(int startX, int startY, int width, int height) {
		Rect rect = new Rect();
		rect.set(startX, startY, width, height);
		return rect;
	}

	Area mTopArea;
	Area mBottomArea;

	List<Area> areaList = new ArrayList<Area>();
	int mIndex = 0;

	@Override
	public void onDraw(Canvas canvas) {
		Log.d(TAG, "draw");
		mCanvas = canvas;
		if (mInitalCanvas == null) {
			mInitalCanvas = canvas;
		}
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);

		int width = getMeasuredWidth();
		int height = getMeasuredHeight();

        initArea(width, height);

		areaList.add(mTopArea);
		areaList.add(mBottomArea);

		enable(mIndex);
		mCanvas.drawRect(mTopArea.getRect(), paint);
		mCanvas.drawRect(mBottomArea.getRect(), paint);

	}

	private void initArea(int width, int height) {
		if (mTopArea == null) {
			mTopArea = new Area(0, 0, width, height / 2, 0);
		}

		if (mBottomArea == null) {
			mBottomArea = new Area(0, height / 2, width, height, 1);
		}

	}

	public void enable(int index) {
		mIndex = index;
		mCanvas.restore();
		for (Area area : areaList) {
			if (area.getIndex() == index) {
				mCanvas.clipRect(area.getRect(), Region.Op.DIFFERENCE);
				return;
			}
		}

	}

	class Area {
		Bitmap bitmap;
		Rect rect;
		int index;

		public Area(int startX, int startY, int width, int height, int index) {
			bitmap = makeBitmap(width, height);
			rect = makeRect(startX, startY, width, height);
			this.index = index;
		}

		public Bitmap getBitmap() {
			return bitmap;
		}

		public Rect getRect() {
			return rect;
		}

		public int getIndex() {
			return index;
		}
	}
}
