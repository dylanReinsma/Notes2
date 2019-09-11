package com.dylanreinsma.notes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

public class LinedEditText extends AppCompatEditText {

    private Rect rect;
    private Paint paint;

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(0xFFFFD966);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int height = ((View)this.getParent()).getHeight();

        int lineHeight = getLineHeight();
        int numberOfLines = height / lineHeight;

        Rect r = rect;
        Paint p = paint;

        int baseline = getLineBounds(0, r);

        for (int i = 0; i < numberOfLines; i++) {
            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);

            baseline += lineHeight;
        }

        super.onDraw(canvas);
    }
}
