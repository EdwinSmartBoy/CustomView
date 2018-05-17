package com.superlink.customview.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * 创 建 者   edwin
 * 创建时间   2018/5/17 0017
 * 描    述   自定义View的基本执行流程及部分方法
 *
 * <p>
 * 更 新 者    edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */

class CustomView : View {

    /**
     * 1.构造函数
     */
    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 2.测量View大小(onMeasure)
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 获取宽度的尺寸和测量模式
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        //获取高度的尺寸和测量模式
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        // 如果对View的宽高进行修改了，不要调用super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        // 要调用setMeasuredDimension(widthSize, heightSize);
        setMeasuredDimension(widthSize, heightSize)
    }

    /**
     * 3.确定View大小(onSizeChanged) 只需关注 宽度(w), 高度(h) 即可
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    /**
     * 4.确定子View布局位置(onLayout)
     * 用于确定子View的位置，在自定义ViewGroup中会用到，他调用的是子View的layout函数。
     * onLayout一般是循环取出子View，然后经过计算得出各个子View位置的坐标值，然后用以下函数设置子View位置。
     * child.layout(l, t, r, b);
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        // 名称	说明	                        对应的函数
        // l	View左侧距父View左侧的距离	getLeft();
        // t	View顶部距父View顶部的距离	getTop();
        // r	View右侧距父View左侧的距离	getRight();
        // b	View底部距父View顶部的距离	getBottom();
    }

    /**
     * 5.绘制内容(onDraw)
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    /**
     * 6.对外提供操作方法和监听回调
     * 自定义完View之后，一般会对外暴露一些接口，用于控制View的状态等，或者监听View的变化.
     * 本内容会在后续文章中以实例的方式进讲解。
     */

}