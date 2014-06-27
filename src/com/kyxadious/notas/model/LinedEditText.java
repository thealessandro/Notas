package com.kyxadious.notas.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

public class LinedEditText extends EditText {
    private Rect mRect;
    private Paint horizontalPaint;
    private Paint verticalPaint;

    // we need this constructor for LayoutInflater
    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        mRect = new Rect();
        horizontalPaint = new Paint();
        horizontalPaint.setStyle(Paint.Style.STROKE);
        horizontalPaint.setStrokeWidth(5);
        horizontalPaint.setColor(Color.parseColor("#80b49c"));
        
    }

    @Override
    protected void onDraw(Canvas canvas) {
    	
        Rect r = mRect;
        Paint hPaint = horizontalPaint;        
        int baseline = getLineBounds(0, r);
        int count = getLineCount();
        
        for (int i = 0; i < count; i++) {     
        	baseline = getLineBounds(i, r);
            canvas.drawLine(r.left, baseline+20, r.right, baseline+20, hPaint); 
           
        }

        super.onDraw(canvas);
    }
} 
