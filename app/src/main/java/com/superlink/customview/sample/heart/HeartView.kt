package com.superlink.customview.sample.heart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * 创建者      Created by Edwin
 * 创建时间    2018/5/23
 * 描述        二阶贝塞尔曲线实现动态绘制圆
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
class HeartView : View {

    private val C = 0.551915024494f     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置

    private val mPaint: Paint = Paint()
    private val mGuidePaint: Paint = Paint()

    private val mPath: Path = Path()

    private var mCenterX: Float = 0f
    private var mCenterY: Float = 0f

    private val mCircleRadius = 200f                  // 圆的半径
    private val mDifference = mCircleRadius * C        // 圆形的控制点与数据点的差值

    private val mData = FloatArray(8)               // 顺时针记录绘制圆形的四个数据点
    private val mCtrl = FloatArray(16)              // 顺时针记录绘制圆形的八个控制点

    private val mDuration = 1000f                     // 变化总时长
    private var mCurrent = 0f                         // 当前已进行时长
    private val mCount = 100f                         // 将时长总共划分多少份
    private val mPiece = mDuration / mCount            // 每一份的时长

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPaint()
        initDataPoint()
        initialControlPoint()
    }

    private fun initialControlPoint() {
        // 初始化控制点

        mCtrl[0] = mData[0] + mDifference
        mCtrl[1] = mData[1]

        mCtrl[2] = mData[2]
        mCtrl[3] = mData[3] + mDifference

        mCtrl[4] = mData[2]
        mCtrl[5] = mData[3] - mDifference

        mCtrl[6] = mData[4] + mDifference
        mCtrl[7] = mData[5]

        mCtrl[8] = mData[4] - mDifference
        mCtrl[9] = mData[5]

        mCtrl[10] = mData[6]
        mCtrl[11] = mData[7] - mDifference

        mCtrl[12] = mData[6]
        mCtrl[13] = mData[7] + mDifference

        mCtrl[14] = mData[0] - mDifference
        mCtrl[15] = mData[1]
    }

    private fun initDataPoint() {
        // 初始化数据点
        mData[0] = 0f
        mData[1] = mCircleRadius

        mData[2] = mCircleRadius
        mData[3] = 0f

        mData[4] = 0f
        mData[5] = -mCircleRadius

        mData[6] = -mCircleRadius
        mData[7] = 0f
    }

    private fun initialPaint() {
        mPaint.textSize = 60f
        mPaint.strokeWidth = 8f
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
        //绘制坐标系
        drawCoordinateSystem(canvas)

        canvas!!.translate(mCenterX, mCenterY)
        canvas.scale(1f, -1f)

        drawAuxiliaryLine(canvas)

        // 绘制贝塞尔曲线
        mPaint.color = Color.RED
        mPaint.strokeWidth = 8f

        mPath.reset()
        mPath.moveTo(mData[0], mData[1])

        mPath.cubicTo(mCtrl[0], mCtrl[1], mCtrl[2], mCtrl[3], mData[2], mData[3])
        mPath.cubicTo(mCtrl[4], mCtrl[5], mCtrl[6], mCtrl[7], mData[4], mData[5])
        mPath.cubicTo(mCtrl[8], mCtrl[9], mCtrl[10], mCtrl[11], mData[6], mData[7])
        mPath.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15], mData[0], mData[1])

        canvas.drawPath(mPath, mPaint)

        mCurrent += mPiece
        if (mCurrent < mDuration) {

            mData[1] -= 120 / mCount
            mCtrl[7] += 80 / mCount
            mCtrl[9] += 80 / mCount

            mCtrl[4] -= 20 / mCount
            mCtrl[10] += 20 / mCount

            postInvalidateDelayed(mPiece.toLong())
        }
    }

    private fun drawAuxiliaryLine(canvas: Canvas) {
        // 绘制数据点和控制点
        mPaint.color = Color.GRAY
        mPaint.strokeWidth = 20f

        run {
            var i = 0
            while (i < 8) {
                canvas.drawPoint(mData[i], mData[i + 1], mPaint)
                i += 2
            }
        }

        run {
            var i = 0
            while (i < 16) {
                canvas.drawPoint(mCtrl[i], mCtrl[i + 1], mPaint)
                i += 2
            }
        }
        // 绘制辅助线
        mPaint.strokeWidth = 4f

        var i = 2
        var j = 2
        while (i < 8) {
            canvas.drawLine(mData[i], mData[i + 1], mCtrl[j], mCtrl[j + 1], mPaint)
            canvas.drawLine(mData[i], mData[i + 1], mCtrl[j + 2], mCtrl[j + 3], mPaint)
            i += 2
            j += 4
        }
        canvas.drawLine(mData[0], mData[1], mCtrl[0], mCtrl[1], mPaint)
        canvas.drawLine(mData[0], mData[1], mCtrl[14], mCtrl[15], mPaint)
    }

    private fun drawCoordinateSystem(canvas: Canvas?) {
        canvas!!.save()
        canvas.translate(mCenterX, mCenterY)

        canvas.scale(1f, -1f)

        mGuidePaint.strokeWidth = 2f
        mGuidePaint.color = Color.RED
        mGuidePaint.isAntiAlias = true
        mGuidePaint.style = Paint.Style.STROKE
        canvas.drawLine(0f, -2000f, 0f, 2000f, mGuidePaint)
        canvas.drawLine(-2000f, 0f, 2000f, 0f, mGuidePaint)

        canvas.restore()
    }

}