package com.duanlian.tabviewpagerindicator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 自定义导航栏指示器
 */
public class ViewPagerIndicator extends LinearLayout {
    private Paint mPaint;//画笔
    private Path mPath;//代表三角形
    private int mTriangleWidth;//三角形的宽度
    private int mTriangleHeight;//三角形的高度
    /*
    我们一共有3个tab，三角形的底边长度大概是每个tab的1/6
     */
    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6F;//三角形底边长度和tab长度的比例
    private int mInitTranslationX;//三角形初始化的偏移位置
    private int mTranslationX;//移动时的偏移量

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();//实例化画笔
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setColor(Color.parseColor("#c9b2ab"));//设置画笔的颜色,也就是三角形的颜色
        mPaint.setStyle(Paint.Style.FILL);//设置style
        mPaint.setPathEffect(new CornerPathEffect(3));//设置三角形圆角

    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        /*
        canvas.save();和canvas.restore();是两个相互匹配出现的，作用是用来保存画布的状态和取出保存的状态的。
        save：用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作
         */
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight()+2);//绘制之前先平移到指定的位置
        canvas.drawPath(mPath,mPaint);//平移到指定的位置后开始绘制，
        /*
        restore：用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
        */
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    /**
     * 当控件的宽高发生变化时都会回调这个方法
     *
     * @param w    控件的长度
     * @param h   高度
     * @param oldw   以前的高度
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /*
        控件的长度（3个tab的总长度）/3就得到每个tab的长度
        而三角形的宽度又是每个tab的长度的六分之一
         */
        mTriangleWidth = (int) (w / 3 * RADIO_TRIANGLE_WIDTH);
        /*
        三角形初始的偏移量，也就是三角形初始的位置
        w / 3 / 2：每个tab长度的一半就是tab的正中间
        再减去三角形宽度的一半，这个偏移量刚好三角形就显示在tab的正中间
         */
        mInitTranslationX = w / 3 / 2 - mTriangleWidth / 2;
        initTriangle();


    }

    /**
     * 初始化三角形
     */
    private void initTriangle() {

        mTriangleHeight = mTriangleWidth / 2;//三角形的高度设置为宽度的一半

        //实例化三角形
        mPath = new Path();
        mPath.moveTo(0, 0);//三角形的起点
        mPath.lineTo(mTriangleWidth, 0);//首先绘制一条宽度为三角形宽度的一条直线
        //然后绘制三角形右边的那条线x坐标为宽度的一半，y坐标为宽度的一半
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();//完成闭合，绘制了一个完整的三角形

    }

    /**
     * 指示器根据手指滚动
     * @param position
     */
    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / 3;
        mTranslationX = (int)(tabWidth * offset + position * tabWidth);//三角形的偏移量
        invalidate();


    }
}
