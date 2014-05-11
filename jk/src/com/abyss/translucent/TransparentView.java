package com.abyss.translucent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;
import android.view.View;

import com.abyss.Constants;

/**
 * Created by abyss on 2014. 5. 3..
 */
public class TransparentView extends View {
    public TransparentView(Context context) {
        super(context);
    }

    //캔버스에 원래있던 도형 (배경화면)
    private Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        Rect rect = new Rect(0, 0, w, h);
        canvas.drawRect(rect, paint);
        return bm;
    }

    //캔버스에 추가될 도형
    private Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);

        Rect rect = new Rect(0, 0, w, h);

        canvas.drawRect(rect, paint);
        return bm;
    }

    //캔버스에 추가될 도형
    private Bitmap makeSrcOval(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);


        RectF rect = new RectF(0, 0, w, h);
        canvas.drawOval(rect, paint);
        return bm;
    }



    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setFilterBitmap(false);
        paint.setShader(null);
        paint.setColor(Color.BLACK);
//        Bitmap srcBitmap = makeSrc(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
//        Bitmap dstBitmap = makeDst(getMeasuredWidth(), getMeasuredHeight());

        Path path = new Path();
        path.addCircle(getMeasuredWidth() / 2, getMeasuredWidth() / 2, getMeasuredWidth() / 2,
                Path.Direction.CW);
        canvas.save();

        canvas.clipPath(path, Region.Op.DIFFERENCE);
        canvas.drawColor(Color.RED);
        //이 부분을 주석처리하지 않으면 구멍이 뚫리지 않은 파란화면만 나온다.
        //restore 메소드로 인해 clipPath한 부분이 복구되었기 때문이다.
        canvas.restore();
        canvas.drawColor(Color.BLUE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
