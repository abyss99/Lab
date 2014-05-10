package com.abyss.translucent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d(Constants.TAG, "width : height = " + getMeasuredWidth() + ":" + getMeasuredHeight());

        canvas.drawColor(Color.TRANSPARENT);

//        Paint borderPaint = new Paint();
//        borderPaint.setARGB(128, 128, 128, 128);
//        borderPaint.setStyle(Paint.Style.STROKE);
//        borderPaint.setStrokeWidth(4);
//
//        RectF drawRect = new RectF();
//        drawRect.set(100, 100, 100, 100);
//        canvas.drawRect(drawRect, borderPaint);


        Rect square = new Rect();
        Paint drawColor = new Paint();
        drawColor.setStyle(Paint.Style.STROKE);
        drawColor.setStrokeWidth(4);
        drawColor.setColor(Color.RED);
//        drawColor.setAlpha(0);
//        drawColor.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        square.set(0, 0, 800, 800);
        canvas.drawRect(square, drawColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
