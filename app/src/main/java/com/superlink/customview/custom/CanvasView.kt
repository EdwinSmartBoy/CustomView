package com.superlink.customview.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 创 建 者   edwin
 * 创建时间   2018/5/17 0017
 * 描    述   画布相关内容
 *
 * <p>
 * 更 新 者    edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */

class CanvasView : View {

    /**
     * Canvas我们可以称之为画布，能够在上面绘制各种东西，是安卓平台2D图形绘制的基础，非常强大。
     *
     * 坐标原点默认在左上角，水平向右为x轴增大方向，竖直向下为y轴增大方向。
     *
     * 二.Canvas的常用操作速查表
     */

    // 1.创建一个画笔
    private val mPaint = Paint()

    private val mRect = Rect(100, 100, 800, 400)
    private val mRectF = RectF(100f, 100f, 800f, 400f)

    // 操作类型	相关API	备注
    // 绘制颜色	drawColor, drawRGB, drawARGB	使用单一颜色填充整个画布
    // 绘制基本形状	drawPoint, drawPoints, drawLine, drawLines, drawRect, drawRoundRect, drawOval, drawCircle, drawArc	依次为 点、线、矩形、圆角矩形、椭圆、圆、圆弧
    // 绘制图片	drawBitmap, drawPicture	绘制位图和图片
    // 绘制文本	drawText, drawPosText, drawTextOnPath	依次为 绘制文字、绘制文字时指定每个文字位置、根据路径绘制文字
    // 绘制路径	drawPath	绘制路径，绘制贝塞尔曲线时也需要用到该函数
    // 顶点操作	drawVertices, drawBitmapMesh	通过对顶点操作可以使图像形变，drawVertices直接对画布作用、 drawBitmapMesh只对绘制的Bitmap作用
    // 画布剪裁	clipPath, clipRect	设置画布的显示区域
    // 画布快照	save, restore, saveLayerXxx, restoreToCount, getSaveCount	依次为 保存当前状态、 回滚到上一次保存的状态、 保存图层状态、 回滚到指定状态、 获取保存次数
    // 画布变换	translate, scale, rotate, skew	依次为 位移、缩放、 旋转、错切
    // Matrix(矩阵)	getMatrix, setMatrix, concat	实际画布的位移，缩放等操作的都是图像矩阵Matrix，只不过Matrix比较难以理解和使用，故封装了一些常用的方法。

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPaint()
    }

    private fun initialPaint() {
        mPaint.strokeWidth = 10f
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        // 绘制点：
        // 在坐标(200,200)位置绘制一个点
        // canvas!!.drawPoint(200f, 200f, mPaint)
        // 绘制一组点，坐标位置由float数组指定
        // canvas.drawPoints(floatArrayOf(500f, 500f, 500f, 600f, 500f, 700f), mPaint)

        // 绘制直线：
        // 绘制直线需要两个点，初始点和结束点，同样绘制直线也可以绘制一条或者绘制一组：
        // 在坐标(300,300)(500,600)之间绘制一条直线
        // canvas!!.drawLine(300f, 300f, 500f, 600f, mPaint)
        // 绘制一组线 每四数字(两个点的坐标)确定一条线
        // canvas.drawLines(floatArrayOf(
        //         100f, 200f, 200f, 200f,//第一组线条
        //         100f, 300f, 200f, 300f //第二组线条
        // ), mPaint)

        // 绘制矩形：
        // 确定一个矩形最少需要四个数据，就是对角线的两个点的坐标值，这里一般采用左上角和右下角的两个点的坐标。
        // 绘制矩形，Canvas提供了三种重载方法
        // 1.四个数值(矩形左上角和右下角两个点的坐标)来确定一个矩形
        // canvas!!.drawRect(100f, 100f, 800f, 400f, mPaint)
        // 2.先将矩形封装为Rect或RectF(实际上仍然是用两个坐标点来确定的矩形)，然后传递给Canvas绘制
        // Rect与RectF区别 Rect是int(整形)的，而RectF是float(单精度浮点型)的。
        // canvas!!.drawRect(mRect, mPaint)
        // canvas!!.drawRect(mRectF, mPaint)

        //绘制圆角矩形：
        //绘制圆角矩形也提供了两种重载方式，如下：
        //float rx, float ry   圆角的
        canvas!!.drawRoundRect(mRectF, 12f, 12f, mPaint)
    }

}