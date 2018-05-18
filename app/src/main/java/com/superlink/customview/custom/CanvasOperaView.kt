package com.superlink.customview.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * 创 建 者   edwin
 * 创建时间   2018/5/18 0018
 * 描    述   Canvas操作相关
 *
 * <p>
 * 更 新 者    edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */

class CanvasOperaView : View {

    /**
     *  操作类型	相关API	备注
     *  绘制颜色	        drawColor, drawRGB, drawARGB	使用单一颜色填充整个画布
     *  绘制基本形状	    drawPoint, drawPoints, drawLine, drawLines, drawRect, drawRoundRect, drawOval, drawCircle, drawArc	依次为 点、线、矩形、圆角矩形、椭圆、圆、圆弧
     *  绘制图片	        drawBitmap, drawPicture	绘制位图和图片
     *  绘制文本	        drawText, drawPosText, drawTextOnPath	依次为 绘制文字、绘制文字时指定每个文字位置、根据路径绘制文字
     *  绘制路径	        drawPath	绘制路径，绘制贝塞尔曲线时也需要用到该函数
     *  顶点操作	        drawVertices, drawBitmapMesh	通过对顶点操作可以使图像形变，drawVertices直接对画布作用、 drawBitmapMesh只对绘制的Bitmap作用
     *  画布剪裁	        clipPath, clipRect	设置画布的显示区域
     *  画布快照	        save, restore, saveLayerXxx, restoreToCount, getSaveCount	依次为 保存当前状态、 回滚到上一次保存的状态、 保存图层状态、 回滚到指定状态、 获取保存次数
     *  画布变换	        translate, scale, rotate, skew	依次为 位移、缩放、 旋转、错切
     *  Matrix(矩阵) 	getMatrix, setMatrix, concat	实际上画布的位移，缩放等操作的都是图像矩阵Matrix， 只不过Matrix比较难以理解和使用，故封装了一些常用的方法。
     */

    /**
     * 合理的使用画布操作可以帮助你用更容易理解的方式创作你想要的效果，这也是画布操作存在的原因。
     * PS: 所有的画布操作都只影响后续的绘制，对之前已经绘制过的内容没有影响。
     */

    /**
     * 创建画笔
     */
    private val mPaint = Paint()

    private val mRectF: RectF = RectF()

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPaint()
    }

    private fun initialPaint() {

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mHeight = h
        this.mWidth = w
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        /**
         * ⑴位移(translate)  ，两次移动是可叠加的。
         * mPaint.isAntiAlias = true
         * mPaint.color = Color.BLACK
         * canvas!!.translate(200f, 200f)
         * canvas.drawCircle(0f, 0f, 100f, mPaint)
         * mPaint.color = Color.BLUE
         * canvas.translate(200f, 2     00f)
         * canvas.drawCircle(0f, 0f, 100f, mPaint)
         */

        /**
         * ⑵缩放(scale)
         * 和位移(translate)一样，缩放也是可以叠加的。
         * 缩放提供了两个方法，如下：
         * public void scale (float sx, float sy)
         * public final void scale (float sx, float sy, float px, float py) px与py用来控制缩放中心位置
         * 缩放比例(sx,sy)取值范围详解：
         *
         * 取值范围(n)	说明
         * [-∞, -1)	    先根据缩放中心放大n倍，再根据中心轴进行翻转
         * -1	        根据缩放中心轴进行翻转
         * (-1, 0)	    先根据缩放中心缩小到n，再根据中心轴进行翻转
         * 0	        不会显示，若sx为0，则宽度为0，不会显示，sy同理
         * (0, 1)	    根据缩放中心缩小到n
         * 1	        没有变化
         * (1, +∞)	    根据缩放中心放大n倍
         */

        // 将坐标系原点移动到画布正中心
        //canvas!!.translate(mWidth / 2f, mHeight / 2f)
        //mRectF.left = 0f
        //mRectF.top = -400f
        //mRectF.right = 400f
        //mRectF.bottom = 0f

        //mPaint.style = Paint.Style.STROKE
        //mPaint.strokeWidth = 2f
        //mPaint.color = Color.BLACK
        //canvas.drawRect(mRectF, mPaint)

        //// 第一种缩放方式
        ////canvas.scale(0.5f, 0.5f)
        //// 第二种缩放方式
        //canvas.scale(-0.5f, -0.5f, 200f, 0f)

        //mPaint.color = Color.BLUE
        //canvas.drawRect(mRectF, mPaint)

        // 将坐标系原点移动到画布正中心
        canvas!!.translate(mWidth / 2f, mHeight / 2f)
        mRectF.left = -400f
        mRectF.top = -400f
        mRectF.right = 400f
        mRectF.bottom = 400f

        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 24f
        for (index in 0 until 20) {
            canvas.scale(0.9f, 0.9f)
            canvas.drawRect(mRectF, mPaint)
        }
    }
}