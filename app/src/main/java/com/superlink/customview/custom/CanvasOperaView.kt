package com.superlink.customview.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
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
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = 6f
        mPaint.style = Paint.Style.STROKE
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
         * canvas.translate(200f, 200f)
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
        canvas!!.translate(mWidth / 2f, mHeight / 2f)
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
        //canvas!!.translate(mWidth / 2f, mHeight / 2f)
        //mRectF.left = -400f
        //mRectF.top = -400f
        //mRectF.right = 400f
        //mRectF.bottom = 400f

        //mPaint.style = Paint.Style.STROKE
        //mPaint.strokeWidth = 24f
        //for (index in 0 until 24) {
        //    canvas.scale(0.9f, 0.9f)
        //    //同心圆
        //    //canvas.drawCircle(0f, 0f, 480f, mPaint)
        //    // 同心矩形
        //    canvas.drawRect(mRectF, mPaint)
        //}

        /**
         * ⑶旋转(rotate)
         * 旋转提供了两种方法：
         *
         * public void rotate (float degrees)
         * public final void rotate (float degrees, float px, float py)
         *
         * px py 控制旋转中心点的
         */

        //mRectF.left = 0f
        //mRectF.top = -400f
        //mRectF.right = 400f
        //mRectF.bottom = 0f

        //canvas.drawRect(mRectF, mPaint)
        // 旋转180度
        // canvas.rotate(180f)
        // 旋转180度  再将旋转中心平移200f
        //canvas.rotate(180f, 200f, 0f)
        //mPaint.color = Color.BLUE
        //canvas.drawRect(mRectF, mPaint)

        //canvas.drawCircle(0f, 0f, 400f, mPaint)
        //canvas.drawCircle(0f, 0f, 380f, mPaint)
        //for (angle in 0 until 360 step 10) {
        //    canvas.drawLine(0f, 380f, 0f, 400f, mPaint)
        //    canvas.rotate(10f)
        //}

        /**
         * ⑷错切(skew)
         *
         * skew这里翻译为错切，错切是特殊类型的线性变换。

         * 错切只提供了一种方法：
         *
         * public void skew (float sx, float sy)
         *
         * 参数含义：
         * float sx:将画布在x方向上倾斜相应的角度，sx倾斜角度的tan值，
         * float sy:将画布在y轴方向上倾斜相应的角度，sy为倾斜角度的tan值.
         *
         * 变换后:

         * X = x + sx * y
         * Y = sy * x + y
         */
        //mRectF.left = 0f
        //mRectF.top = 0f
        //mRectF.right = 200f
        //mRectF.bottom = 200f

        //canvas.drawRect(mRectF, mPaint)
        // 错切   sx与sy为倾斜角度的tan值
        // 错切也是可叠加的，不过请注意，调用次序不同绘制结果也会不同
        // 水平错切
        //canvas.skew(1f, 0f)
        // 垂直错切
        //canvas.skew(0f, 1f)

        //mPaint.color = Color.BLUE
        //canvas.drawRect(mRectF, mPaint)

        /**
         * ⑸快照(save)和回滚(restore)
         *
         * 相关API	      简介
         * save	          把当前的画布的状态进行保存，然后放入特定的栈中
         * saveLayerXxx	  新建一个图层，并放入特定的栈中
         * restore	      把栈中最顶层的画布状态取出来，并按照这个状态恢复当前的画布
         * restoreToCount 弹出指定位置及其以上所有的状态，并按照指定位置的状态进行恢复
         * getSaveCount	  获取栈中内容的数量(即保存次数)
         *
         * save
         * save 有两种方法：
         *
         * 保存全部状态
         * public int save ()
         * 根据saveFlags参数保存一部分状态
         * public int save (int saveFlags)
         *
         * 每调用一次save方法，都会在状态栈（暂且叫这个）顶添加一条状态信息，
         *
         * 数据类型	名称	简介
         * int	    ALL_SAVE_FLAG	            默认，保存全部状态
         * int	    CLIP_SAVE_FLAG	            保存剪辑区
         * int	    CLIP_TO_LAYER_SAVE_FLAG	    剪裁区作为图层保存
         * int	    FULL_COLOR_LAYER_SAVE_FLAG  保存图层的全部色彩通道
         * int	    HAS_ALPHA_LAYER_SAVE_FLAG	保存图层的alpha(不透明度)通道
         * int	    MATRIX_SAVE_FLAG	        保存Matrix信息(translate, rotate, scale, skew)
         *
         * 注意：saveLayerXxx方法会让你花费更多的时间去渲染图像(图层多了相互之间叠加会导致计算量成倍增长)，使用前请谨慎，如果可能，尽量避免使用。
         * 使用saveLayerXxx方法，也会将图层状态也放入状态栈中，同样使用restore方法进行恢复。
         *
         * restore
         * 状态回滚，就是从栈顶取出一个状态然后根据内容进行恢复。
         *
         * restoreToCount
         * 弹出指定位置以及以上所有状态，并根据指定位置状态进行恢复。
         *
         * getSaveCount
         * 获取保存的次数，即状态栈中保存状态的数量
         *
         * 常用格式
         * 虽然关于状态的保存和回滚啰嗦了不少，不过大多数情况下只需要记住下面的步骤就可以了：
         *
         * save();      //保存状态
         * ...          //具体操作
         * restore();   //回滚到之前的状态
         *
         * 这种方式也是最简单和最容易理解的使用方法。
         */

    }
}