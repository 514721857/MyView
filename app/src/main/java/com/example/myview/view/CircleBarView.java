package com.example.myview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.myview.R;
import com.example.myview.utils.DpOrPxUtils;

public class CircleBarView extends View {

    private Paint rPaint;//绘制矩形的画笔
    private Paint progressPaint;//绘制圆弧的画笔
    private Paint progressBgPaint;//绘制圆弧的画笔
    CircleBarAnim anim;
    private float sweepAngle;//圆弧经过的角度

    private RectF mRectF;//绘制圆弧的矩形区域
    private float barWidth;//圆弧进度条宽度
    private int defaultSize;//自定义View默认的宽高

    private int progressColor;//进度条圆弧颜色
    private int bgColor;//背景圆弧颜色
    private float startAngle;//背景圆弧的起始角度


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

        defaultSize = DpOrPxUtils.dip2px(context,100);
        barWidth = DpOrPxUtils.dip2px(context,10);
        mRectF = new RectF();


        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CircleBarView);

        progressColor = typedArray.getColor(R.styleable.CircleBarView_progress_color,Color.GREEN);//默认为绿色
        bgColor = typedArray.getColor(R.styleable.CircleBarView_bg_color,Color.GRAY);//默认为灰色
        startAngle = typedArray.getFloat(R.styleable.CircleBarView_start_angle,0);//默认为0
        sweepAngle = typedArray.getFloat(R.styleable.CircleBarView_sweep_angle,360);//默认为360
        barWidth = typedArray.getDimension(R.styleable.CircleBarView_bar_width,DpOrPxUtils.dip2px(context,10));//默认为10dp
        typedArray.recycle();//typedArray用完之后需要回收，防止内存泄漏


        progressPaint.setColor(progressColor);
        progressPaint.setStrokeWidth(barWidth);

        progressBgPaint.setColor(bgColor);
        progressBgPaint.setStrokeWidth(barWidth);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = measureSize(defaultSize, heightMeasureSpec);
        int width = measureSize(defaultSize, widthMeasureSpec);
        int min = Math.min(width, height);// 获取View最短边的长度
        setMeasuredDimension(min, min);// 强制改View为以最短边为长度的正方形

        if(min >= barWidth*2){//这里简单限制了圆弧的最大宽度
            mRectF.set(barWidth/2,barWidth/2,min-barWidth/2,min-barWidth/2);
        }

    }

    private int measureSize(int defaultSize,int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {//layout_width="match_parent" 或者给出准确的大小
            System.out.println("View.MeasureSpec.EXACTLY");
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {//wrap_content
            System.out.println(" View.MeasureSpec.AT_MOST");
            result = Math.min(result, specSize);
        }
        return result;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        RectF rectF = new RectF(x,y,x+300,y+300);//建一个大小为300 * 300的正方形区域
        canvas.drawArc(mRectF,startAngle,sweepAngle,false,progressBgPaint);//这里角度0对应的是三点钟方向，顺时针方向递增

        canvas.drawArc(mRectF,startAngle,sweepAngle,false,progressPaint);//这里角度0对应的是三点钟方向，顺时针方向递增
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
