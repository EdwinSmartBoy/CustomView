package com.superlink.customview.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * 创 建 者   edwin
 * 创建时间   2018/5/23 0023
 * 描    述   贝塞尔曲线操作
 *
 * <p>
 * 更 新 者    edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */

class PathBesselView : View {

    /**
     * 类型      作用
     * 数据点    确定曲线的起始和结束位置
     * 控制点	确定曲线的弯曲程度
     *
     *
     * 一阶曲线原理：
     * 一阶曲线是没有控制点的，仅有两个数据点(A 和 B)，最终效果一个线段。
     *
     * 二阶曲线原理：
     * 二阶曲线由两个数据点(A 和 C)，一个控制点(B)来描述曲线状态
     * 二阶曲线对应的方法是quadTo
     *
     * 三阶曲线原理：
     * 三阶曲线由两个数据点(A 和 D)，两个控制点(B 和 C)来描述曲线状态
     * 三阶曲线对应的方法是cubicTo
     *
     *
     * 类型	释义	                                                        变化
     * 降阶	在保持曲线形状与方向不变的情况下，减少控制点数量，即降低曲线阶数	方法变得简单，数据点变多，控制点可能减少，灵活性变弱
     * 升阶	在保持曲线形状与方向不变的情况下，增加控制点数量，即升高曲线阶数	方法更加复杂，数据点不变，控制点增加，灵活性变强
     *
     * 序号	内容	                                      用例
     * 1	事先不知道曲线状态，需要实时计算时	          天气预报气温变化的平滑折线图
     * 2	显示状态会根据用户操作改变时	              QQ小红点，仿真翻书效果
     * 3	一些比较复杂的运动状态(配合PathMeasure使用)	  复杂运动状态的动画效果
     *
     * 贝塞尔曲线的主要优点是可以实时控制曲线状态，并可以通过改变控制点的状态实时让曲线进行平滑的状态变化。
     */

    private val mPaint: Paint = Paint()

    private val mPath: Path = Path()

    private val end: PointF = PointF()
    private val start: PointF = PointF()
    private val control: PointF = PointF()
    private val control1: PointF = PointF()

    private var mCenterX: Float = 0f
    private var mCenterY: Float = 0f

    private var mMode: Boolean = false

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPaint()
    }

    private fun initialPaint() {
        mPaint.textSize = 60f
        mPaint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mCenterX = w / 2f
        this.mCenterY = h / 2f

        initialPoint()
    }

    private fun initialPoint() {
        start.x = mCenterX - 200f
        start.y = mCenterY

        end.x = mCenterX + 200f
        end.y = mCenterY

        control.x = mCenterX - 160f
        control.y = mCenterY - 200f

        //三阶贝塞尔曲线控制点
        control1.x = mCenterX + 160f
        control1.y = mCenterY - 200f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //二阶贝塞尔曲线
        //control.x = event!!.x
        //control.y = event.y

        //三阶贝塞尔曲线
        if (mMode) {
            control.x = event!!.x
            control.y = event.y
        } else {
            control1.x = event!!.x
            control1.y = event.y
        }
        postInvalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        /**
         * 二阶贝塞尔曲线绘制
         */
        mPaint.color = Color.GRAY
        mPaint.style = Paint.Style.FILL
        //绘制数据点和控制点
        canvas!!.drawCircle(end.x, end.y, 12f, mPaint)
        canvas.drawCircle(start.x, start.y, 12f, mPaint)
        canvas.drawCircle(control.x, control.y, 12f, mPaint)
        //三阶贝塞尔控制点
        canvas.drawCircle(control1.x, control1.y, 12f, mPaint)

        //绘制辅助线
        mPaint.strokeWidth = 4f
        mPaint.style = Paint.Style.STROKE

        //二阶贝塞尔曲线
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint)
        //canvas.drawLine(control.x, control.y, end.x, end.y, mPaint)
        //三阶贝塞尔辅助线
        canvas.drawLine(control.x, control.y, control1.x, control1.y, mPaint)
        canvas.drawLine(control1.x, control1.y, end.x, end.y, mPaint)

        //绘制贝塞尔曲线
        mPaint.color = Color.RED
        mPaint.strokeWidth = 8f

        mPath.reset()
        mPath.moveTo(start.x, start.y)
        //二阶贝塞尔曲线
        //mPath.quadTo(control.x, control.y, end.x, end.y)
        //三阶贝塞尔曲线
        mPath.cubicTo(control.x, control.y, control1.x, control1.y, end.x, end.y)
        canvas.drawPath(mPath, mPaint)
    }

    fun setMode(mode: Boolean) {
        this.mMode = mode
    }

}