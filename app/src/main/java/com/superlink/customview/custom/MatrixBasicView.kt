package com.superlink.customview.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.gcssloop.view.utils.CanvasAidUtils
import com.superlink.customview.R
import kotlin.math.abs

/**
 * 创建者      Created by Edwin
 * 创建时间    2018/6/2
 * 描述        矩阵基本操作
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
class MatrixBasicView : View {

    /**
     * Matrix特点
     * 作用范围更广，Matrix在View，图片，动画效果等各个方面均有运用，相比与之前讲解等画布操作应用范围更广。
     * 更加灵活，画布操作是对Matrix的封装，Matrix作为更接近底层的东西，必然要比画布操作更加灵活。
     * 封装很好，Matrix本身对各个方法就做了很好的封装，让开发者可以很方便的操作Matrix。
     * 难以深入理解，很难理解中各个数值的意义，以及操作规律，如果不了解矩阵，也很难理解前乘，后乘。
     *
     * 基本变换有4种: 平移(translate)、缩放(scale)、旋转(rotate) 和 错切(skew)。
     *
     * 矩阵阵列参数如下：
     *
     * MSCALE_X  MSKEW_X    MTRANS_X
     *
     * MSKEW_Y   MSCALE_Y   MTRANS_Y
     *
     * MPERSP-0  MPERSP-1   MPERSP-2
     *
     *
     * MSCALE_X  MSKEW_X  MSKEW_Y   MSCALE_Y 共同控制图像旋转（rotate）
     *
     * MSCALE_X  MSCALE_Y 控制图像缩放（scale）
     *
     * MSKEW_X   MSKEW_Y  控制图像错切（skew）
     *
     * MTRANS_X  MTRANS_Y  控制图像位移（translate）
     *
     * MPERSP-0  MPERSP-1   MPERSP-2 控制图像透视（透明度）
     *
     * 每一种操作在Matrix均有三类,前乘(pre)，后乘(post)和设置(set)
     * 设置使用的不是矩阵乘法，而是直接覆盖掉原来的数值，所以，使用设置可能会导致之前的操作失效。
     *
     * 所有的操作(旋转、平移、缩放、错切)默认都是以坐标原点为基准点的。
     * 之前操作的坐标系状态会保留，并且影响到后续状态。
     *
     * 基于这两条基本定理，我们可以推算出要基于某一个点进行旋转需要如下步骤：
     *
     * 1. 先将坐标系原点移动到指定位置，使用平移 T
     * 2. 对坐标系进行旋转，使用旋转 S (围绕原点旋转)
     * 3. 再将坐标系平移回原来位置，使用平移 -T
     *
     *
     * 无参构造
     * Matrix ()
     *通过这种方式创建出来的并不是一个数值全部为空的矩阵，而是一个单位矩阵,
     *
     * Matrix (Matrix src)
     *这种方法则需要一个已经存在的矩阵作为参数，
     * 创建一个Matrix，并对src深拷贝(理解为新的matrix和src是两个对象，但内部数值相同即可)。
     *
     * 3.setValues
     * void setValues (float[] values)
     * setValues的参数是浮点型的一维数组，长度需要大于9，拷贝数组中的前9位数值赋值给当前Matrix。
     *
     * 4.getValues
     * void getValues (float[] values)
     * 很显然，getValues和setValues是一对方法，参数也是浮点型的一维数组，长度需要大于9，将Matrix中的数值拷贝进参数的前9位中。
     */

    private val mPaint: Paint = Paint()

    private var mPoint: Int = 4
    private val triggerRadius = 180    // 触发半径为180px

    private lateinit var mBitmap: Bitmap

    private lateinit var src: FloatArray
    private lateinit var dst: FloatArray

    private val mPointDst: FloatArray = FloatArray(8)

    private val mMatrix: Matrix = Matrix()

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPaint()
        initialBitmap()
    }

    private fun initialPaint() {
        mPaint.strokeWidth = 50f
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.color = Color.parseColor("#FFD19165")
    }

    private fun initialBitmap() {
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.matrix_bitmap)

        val temp: FloatArray = floatArrayOf(0f, 0f, mBitmap.width.toFloat(), 0f, mBitmap.width.toFloat(), mBitmap.height.toFloat(), 0f, mBitmap.height.toFloat())

        src = temp.clone()
        dst = temp.clone()

        //最后的4表示屏幕上的坐标的个数
        mMatrix.setPolyToPoly(src, 0, dst, 0, 4)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_MOVE -> {
                val tempX: Float = event.x
                val tempY: Float = event.y
                for (index in 0 until mPoint * 2 step 2) {
                    if (abs(tempX - dst[index]) <= triggerRadius && abs(tempY - dst[index + 1]) <= triggerRadius) {
                        dst[index] = tempX - 100
                        dst[index + 1] = tempY - 100
                        break
                    }
                }
                resetPolyMatrix(mPoint)
                postInvalidate()
            }
        }
        return true
    }

    //设置控制点
    private fun resetPolyMatrix(mPoint: Int) {
        //重置matrix
        mMatrix.reset()
        //设置matrix
        mMatrix.setPolyToPoly(src, 0, dst, 0, mPoint)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.translate(100f, 100f)

        //绘制坐标系
        CanvasAidUtils.set2DAxisLength(900f, 0f, 1200f, 0f)
        CanvasAidUtils.draw2DCoordinateSpace(canvas)

        //根据Matrix绘制变形后的图片
        canvas.drawBitmap(mBitmap, mMatrix, mPaint)

        //绘制控制点
        mMatrix.mapPoints(mPointDst, src)

        for (index in 0 until mPoint * 2 step 2) {
            canvas.drawPoint(mPointDst[index], mPointDst[index + 1], mPaint)
        }
    }

    fun setPointCount(point: Int) {
        if (point > 4 || point < 0) {
            this.mPoint = 4
        } else {
            this.mPoint = point
        }
        resetPolyMatrix(mPoint)
        postInvalidate()
    }

}
