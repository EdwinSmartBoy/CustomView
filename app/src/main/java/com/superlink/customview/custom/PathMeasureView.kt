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
    private val mDst: Path = Path()

    private val mPaint: Paint = Paint()

    private val mPathMeasure1: PathMeasure = PathMeasure()
    private val mPathMeasure2: PathMeasure = PathMeasure()

    private var mCenterX: Float = 0f
    private var mCenterY: Float = 0f

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
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

        //mPath.lineTo(0f, 200f)
        //mPath.lineTo(200f, 200f)
        //mPath.lineTo(200f, 0f)

        //mPathMeasure1.setPath(mPath, false)
        //mPathMeasure2.setPath(mPath, true)

        //println("forceClosed ---> false, length ---> ${mPathMeasure1.length}, isClosed ---> ${mPathMeasure1.isClosed}")
        //println("forceClosed ---> true, length ---> ${mPathMeasure2.length}, isClosed ---> ${mPathMeasure2.isClosed}")

        //canvas.drawPath(mPath, mPaint)
        /**
         * getSegment 用于获取Path的一个片段
         *
         * boolean getSegment (float startD, float stopD, Path dst, boolean startWithMoveTo)
         *
         * 参数	            作用	                           备注
         * 返回值(boolean)   判断截取是否成功	               true 表示截取成功，结果存入dst中，false 截取失败，不会改变dst中内容
         * startD	        开始截取位置距离 Path 起点的长度  取值范围: 0 <= startD < stopD <= Path总长度
         * stopD	        结束截取位置距离 Path 起点的长度  取值范围: 0 <= startD < stopD <= Path总长度
         * dst	            截取的 Path 将会添加到 dst 中	   注意: 是添加，而不是替换
         * startWithMoveTo  起始点是否使用 moveTo	           用于保证截取的 Path 第一个点位置不变
         *
         * 如果 startD、stopD 的数值不在取值范围 [0, getLength] 内，或者 startD == stopD 则返回值为 false，不会改变 dst 内容。
         */

        //mPath.addRect(-200f, -200f, 200f, 200f, Path.Direction.CW)

        //mPathMeasure1.setPath(mPath,  false)
        //mPathMeasure1.getSegment(200f, 600f, mDst, true)

        //canvas.drawPath(mDst, mPaint)

        //当 dst 中有内容时会怎样呢？  被截取的 Path 片段会添加到 dst 中，而不是替换 dst 中到内容。

        //mPath.addRect(-200f, -200f, 200f, 200f, Path.Direction.CW)
        //mDst.lineTo(-300f, -300f)
        //mPathMeasure1.setPath(mPath,  false)
        //mPathMeasure1.getSegment(200f, 600f, mDst, false)
        //如果 startWithMoveTo 为 true, 则被截取出来到Path片段保持原状，如果 startWithMoveTo 为 false，
        // 则会将截取出来的 Path 片段的起始点移动到 dst 的最后一个点，以保证 dst 的连续性。
        //canvas.drawPath(mDst, mPaint)

        /**
         * 4.nextContour
         *
         * nextContour 就是用于跳转到下一条曲线到方法，如果跳转成功，则返回 true， 如果跳转失败，则返回 false。
         *
         * 1.曲线的顺序与 Path 中添加的顺序有关。
         * 2.getLength 获取到到是当前一条曲线分长度，而不是整个 Path 的长度。
         * 3.getLength 等方法是针对当前的曲线(其它方法请自行验证)。
         */

        mPath.addRect(-100f, -100f, 100f, 100f, Path.Direction.CW)
        mPath.addRect(-200f, -200f, 200f, 200f, Path.Direction.CW)

        canvas.drawPath(mPath, mPaint)

        mPathMeasure1.setPath(mPath, false)
        println("mPathMeasure1 ---> forceClosed ---> false, length ---> ${mPathMeasure1.length}")
        mPathMeasure1.nextContour()
        println("mPathMeasure2 ---> forceClosed ---> false, length ---> ${mPathMeasure1.length}")

        /**
         * 5.getPosTan
         *
         * 这个方法是用于得到路径上某一长度的位置以及该位置的正切值：
         *
         * 参数	            作用         	    备注
         * 返回值(boolean)  判断获取是否成功	    true表示成功，数据会存入 pos 和 tan 中，false 表示失败，pos 和 tan 不会改变
         * distance	       距离 Path 起点的长度	取值范围: 0 <= distance <= getLength
         * pos	           该点的坐标值	        当前点在画布上的位置，有两个数值，分别为x，y坐标。
         * tan	           该点的正切值	        当前点在曲线上的方向，使用 Math.atan2(tan[1], tan[0]) 获取到正切角的弧度值。
         */

    }

}