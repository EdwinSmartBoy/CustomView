package com.superlink.customview.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * 创建者      Created by Edwin
 * 创建时间    2018/5/17
 * 描述        画笔相关内容
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
class PaintView : View {

    /**
     * 绘制的基本形状由Canvas确定，但绘制出来的颜色,具体效果则由Paint确定。
     *mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
     *
     *画笔有三种模式，如下：
     *
     *STROKE                //描边
     *FILL                  //填充
     *FILL_AND_STROKE       //描边加填充
     */

    private val mPaint = Paint()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        mPaint.strokeWidth = 10f
        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.FILL
        canvas!!.drawCircle(200f, 200f, 100f, mPaint)

        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.STROKE
        canvas.drawCircle(200f, 400f, 100f, mPaint)

        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.FILL_AND_STROKE
        canvas.drawCircle(200f, 600f, 100f, mPaint)
    }
}