package com.superlink.customview.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * 创建者      Created by Edwin
 * 创建时间    2018/5/20
 * 描述        基本的path操作View
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
class PathBasicView : View {

    /**
     * 使用Path不仅能够绘制简单图形，也可以绘制这些比较复杂的图形。另外，根据路径绘制文本和剪裁画布都会用到Path。
     */

    private val mPaint: Paint = Paint()
    private val mPath: Path = Path()

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPaint()
    }

    private fun initialPaint() {
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 10f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mWidth = w
        this.mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //将画布中心移动到屏幕中心
        canvas!!.translate(mWidth / 2f, mHeight / 2f)

        /**
         * 第1组: moveTo、 setLastPoint、 lineTo 和 close
         */
        // 创建两条线
        // 第一次由于之前没有过操作，所以默认点就是坐标原点O，结果就是坐标原点O到A(200,200)之间连直线(用蓝色圈1标注)。
        // mPath.lineTo(200f, 200f)
        // 第二次lineTo的时候，由于上次的结束位置是A(200,200),所以就是A(200,200)到B(200,0)之间的连线(用蓝色圈2标注)。
        // mPath.lineTo(200f, 0f)
        // 绘制线条到画布中
        // canvas.drawPath(mPath, mPaint)

        // 方法名	        简介	                        是否影响之前的操作	是否影响之后操作
        // moveTo	    移动下一次操作的起点位置	    否	                是
        // setLastPoint	设置之前操作的最后一个点位置	是	                是

        // 移动到（200f, 100f）
        // mPath.moveTo(200f, 100f)
        // mPath.lineTo(200f, 0f)
        // canvas.drawPath(mPath, mPaint)

        // etLastPoint是重置上一次操作的最后一个点，在执行完第一次的lineTo的时候，
        // 最后一个点是A(200,200),而setLastPoint更改最后一个点为C(200,100),所以在实际执行的时候，
        // 第一次的lineTo就不是从原点O到A(200,200)的连线了，而变成了从原点O到C(200,100)之间的连线了。
        // mPath.lineTo(200f, 200f)
        // mPath.setLastPoint(200f, 100f)
        // mPath.lineTo(200f, 0f)
        // canvas.drawPath(mPath, mPaint)

        // close方法用于连接当前最后一个点和最初的一个点(如果两个点不重合的话)，最终形成一个封闭的图形。
        // mPath.lineTo(200f, 200f)
        // mPath.lineTo(200f, 0f)
        // mPath.close()
        // canvas.drawPath(mPath, mPaint)

        /**
         * 第2组: addXxx与arcTo
         *
         * 第一类(基本形状) 这一类就是在path中添加一个基本形状，基本形状部分和前面所讲的绘制基本形状并无太大差别，
         *
         * // 第一类(基本形状)
         *  圆形
         * public void addCircle (float x, float y, float radius, Path.Direction dir)
         *  椭圆
         * public void addOval (RectF oval, Path.Direction dir)
         *  矩形
         * public void addRect (float left, float top, float right, float bottom, Path.Direction dir)
         * public void addRect (RectF rect, Path.Direction dir)
         *  圆角矩形
         * public void addRoundRect (RectF rect, float[] radii, Path.Direction dir)
         * public void addRoundRect (RectF rect, float rx, float ry, Path.Direction dir)
         *
         * Direction的意思是 方向，趋势。
         *
         * 类型	解释	              翻译   作用
         * CW	clockwise	      顺时针 在添加图形时确定闭合顺序(各个点的记录顺序)
         * CCW	counter-clockwise 逆时针 对图形的渲染结果有影响(是判断图形渲染的重要条件)
         */
        //mPath.addRect(-200f, -200f, 200f, 200f, Path.Direction.CW)
        //canvas.drawPath(mPath, mPaint)

        mPath.addRect(-200f, -200f, 200f, 200f, Path.Direction.CCW)
        mPath.setLastPoint(-300f, 300f)
        canvas.drawPath(mPath, mPaint)
    }
}