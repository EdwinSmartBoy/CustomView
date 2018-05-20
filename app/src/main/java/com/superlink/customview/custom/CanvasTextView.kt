package com.superlink.customview.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * 创建者      Created by Edwin
 * 创建时间    2018/5/19
 * 描述        canvas文本操作
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
class CanvasTextView : View {

    /**
     * 绘制文本的方法
     *
     * 只能指定文本基线位置(基线x默认在字符串左侧，基线y默认在字符串下方)。
     *
     * // 第一类
     * public void drawText (String text, float x, float y, Paint paint)
     * public void drawText (String text, int start, int end, float x, float y, Paint paint)
     * public void drawText (CharSequence text, int start, int end, float x, float y, Paint paint)
     * public void drawText (char[] text, int index, int count, float x, float y, Paint paint)
     *
     * // 第二类
     *
     * 可以分别指定每个文字的位置。
     *
     * public void drawPosText (String text, float[] pos, Paint paint)
     * public void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)
     *
     * // 第三类
     *
     * 指定一个路径，根据路径绘制文字。
     *
     * public void drawTextOnPath (String text, Path path, float hOffset, float vOffset, Paint paint)
     * public void drawTextOnPath (char[] text, int index, int count, Path path, float hOffset, float vOffset, Paint paint)
     *
     *
     * Paint文本相关常用方法表
     *
     *
     * 标题	相关方法	                   备注
     * 色彩	setColor setARGB setAlpha  设置颜色，透明度
     * 大小	setTextSize	               设置文本字体大小
     * 字体	setTypeface	               设置或清除字体样式
     * 样式	setStyle	               填充(FILL),描边(STROKE),填充加描边(FILL_AND_STROKE)
     * 对齐	setTextAlign	           左对齐(LEFT),居中对齐(CENTER),右对齐(RIGHT)
     * 测量	measureText	               测量文本大小(注意，请在设置完文本各项参数后调用)
     */

    private val mPaint: Paint = Paint()

    private val text: String = "SLOOP"

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPaint()
    }

    private fun initialPaint() {
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.FILL
        mPaint.textSize = 50f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 第一类方法
        // 参数分别为 (文本 基线x 基线y 画笔)
        // canvas!!.drawText(text, 200f, 500f, mPaint)
        // canvas!!.drawText(text, 1, 5, 200f, 500f, mPaint)

        //第二类方法  过时方法  不推荐使用
        /**
         * 序号	反对理由
         *  1	必须指定所有字符位置，否则直接crash掉，反人类设计
         *  2	性能不佳，在大量使用的时候可能导致卡顿
         *  3	不支持emoji等特殊字符，不支持字形组合与分解
         */
        //canvas!!.drawPosText(text, floatArrayOf(100f, 100f, 200f, 200f, 300f, 300f, 400f, 400f, 500f, 500f), mPaint)

        //第三类方法 drawTextOnPath 需要用到path下一个解析

    }

}