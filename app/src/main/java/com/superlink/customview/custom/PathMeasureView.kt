package com.superlink.customview.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 创 建 者   edwin
 * 创建时间   2018/5/24 0024
 * 描    述   PathMeasure相关操作
 *
 * <p>
 * 更 新 者    edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */

class PathMeasureView : View {

    /**
     * Path & PathMeasure
     *
     * 方法名	                                    释义
     * PathMeasure()	                            创建一个空的PathMeasure
     * PathMeasure(Path path, boolean forceClosed)	创建 PathMeasure 并关联一个指定的Path(Path需要已经创建完成)。
     *
     * 第二个参数是用来确保 Path 闭合，如果设置为 true， 则不论之前Path是否闭合，都会自动闭合该 Path(如果Path可以闭合的话)。
     *
     * 不论 forceClosed 设置为何种状态(true 或者 false)， 都不会影响原有Path的状态，即 Path 与 PathMeasure 关联之后，之前的的 Path 不会有任何改变。
     * forceClosed 的设置状态可能会影响测量结果，如果 Path 未闭合但在与 PathMeasure 关联的时候设置 forceClosed 为 true 时，测量结果可能会比 Path 实际长度稍长一点，获取到到是该 Path 闭合时的状态。
     *
     * 返回值	方法名	                                                                  释义
     * void	    setPath(Path path, boolean forceClosed)	                                  关联一个Path
     * boolean	isClosed()	                                                              是否闭合
     * float	getLength()	                                                              获取Path的长度
     * boolean	nextContour()	                                                          跳转到下一个轮廓
     * boolean	getSegment(float startD, float stopD, Path dst, boolean startWithMoveTo)  截取片段
     * boolean	getPosTan(float distance, float[] pos, float[] tan)	                      获取指定长度的位置坐标及该点切线值
     * boolean	getMatrix(float distance, Matrix matrix, int flags)	                      获取指定长度的位置坐标及该点Matrix
     */

    private val mPath: Path = Path()
    private val mPaint: Paint = Paint()

    private val mPathMeasure1: PathMeasure = PathMeasure()
    private val mPathMeasure2: PathMeasure = PathMeasure()

    private var mCenterX: Float = 0f
    private var mCenterY: Float = 0f

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPaint()
    }

    private fun initialPaint() {
        mPaint.strokeWidth = 10f
        mPaint.isAntiAlias = true
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mCenterX = w / 2f
        this.mCenterY = h / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.translate(mCenterX, mCenterY)

        mPath.lineTo(0f, 200f)
        mPath.lineTo(200f, 200f)
        mPath.lineTo(200f, 0f)

        mPathMeasure1.setPath(mPath, false)
        //mPathMeasure2.setPath(mPath, true)

        println("forceClosed ---> false, length ---> ${mPathMeasure1.length}")
        println("forceClosed ---> true, length ---> ${mPathMeasure2.length}")

        canvas.drawPath(mPath, mPaint)
    }

}