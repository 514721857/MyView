package com.example.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CircleBarView extends View {

    private Paint rPaint;//绘制矩形的画笔
    private Paint progressPaint;//绘制圆弧的画笔
    private Paint progressBgPaint;//绘制圆弧的画笔
    CircleBarAnim anim;
    private float sweepAngle;//圆弧经过的角度


    public CircleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        anim = new CircleBarAnim();

        rPaint = new Paint();
        rPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
        rPaint.setStrokeWidth(4);
        rPaint.setColor(Color.RED);

        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
        progressPaint.setColor(Color.BLUE);
        progressPaint.setStrokeWidth(4);
        progressPaint.setAntiAlias(true);//设置抗锯齿

        progressBgPaint = new Paint();
        progressBgPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
        progressBgPaint.setColor(Color.GRAY);
        progressBgPaint.setStrokeWidth(4);
        progressBgPaint.setAntiAlias(true);//设置抗锯齿

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = 50;
        float y = 50;
        RectF rectF = new RectF(x,y,x+300,y+300);//建一个大小为300 * 300的正方形区域
        canvas.drawArc(rectF,0,360,false,progressBgPaint);//这里角度0对应的是三点钟方向，顺时针方向递增

        canvas.drawArc(rectF,0,sweepAngle,false,progressPaint);//这里角度0对应的是三点钟方向，顺时针方向递增
//        canvas.drawRect(rectF,rPaint);
    }
    public class CircleBarAnim extends Animation {

        public CircleBarAnim(){
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            sweepAngle = interpolatedTime * 360;
            postInvalidate();
        }
    }

    //写个方法给外部调用，用来设置动画时间
    public void setProgressNum(int time) {
        anim.setDuration(time);
        this.startAnimation(anim);
    }
}