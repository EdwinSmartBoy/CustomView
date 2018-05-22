package com.superlink.customview.sample.radar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * 创建者      Created by Edwin
 * 创建时间    2018/5/21
 * 描述        雷达自定义View
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
class RadarView : View {

    /**
     * 默认的雷达层数
     */
    private var mDefaultCount = 8

    /**
     * 默认的每个扇形的角度
     */
    private var angle = 0f

    /**
     * 网格最大半径
     */
    private var mMaxRadius: Float = 0.0f

    //画布的中心点
    private var mCenterWidth: Float = 0f
    private var mCenterHeight: Float = 0f

    /**
     * 默认的标题栏
     */
    private var mDefTitle: Array<String> = arrayOf("a", "b", "c", "d", "e", "f", "g", "h")
    /**
     * 各维度分值
     */
    private var mDefaultData = floatArrayOf(90f, 68f, 72f, 69.5f, 96f, 64f, 51.5f, 62.4f)

    /**
     * 默认最大值
     */
    private var mMaxValue: Float = 100f

    //线条的画笔
    private val mRadarPaint: Paint = Paint()
    //数据区的画笔
    private val mDataPaint: Paint = Paint()
    //文本的画笔
    private val mTextPaint: Paint = Paint()

    //路径的绘制Path
    private val mDrawPath: Path = Path()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPaint()
    }

    /**
     * 初始化画笔
     */
    private fun initialPaint() {
        //线条画笔
        mRadarPaint.color = Color.BLACK
        mRadarPaint.isAntiAlias = true
        mRadarPaint.style = Paint.Style.STROKE
        mRadarPaint.strokeWidth = 2f

        //数据区
        mDataPaint.color = Color.BLUE
        mDataPaint.isAntiAlias = true
        mDataPaint.style = Paint.Style.FILL

        //文本
        mTextPaint.textSize = 42f
        mTextPaint.color = Color.GRAY
        mTextPaint.isAntiAlias = true
        mTextPaint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mCenterWidth = w / 2f
        this.mCenterHeight = h / 2f
        this.mMaxRadius = min(w, h) / 2 * 0.9f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.translate(mCenterWidth, mCenterHeight)
        //设置每个角度的值
        angle = (Math.PI * 2 / mDefaultCount).toFloat()
        //绘制网格线
        drawPolygon(canvas)
        //绘制从中心到末端的直线
        drawLines(canvas)
        //绘制各个末端的文本数据
        drawTexts(canvas)
        //绘制覆盖区域
        drawRegion(canvas)
    }

    /**
     * 绘制覆盖区域
     */
    private fun drawRegion(canvas: Canvas) {
        mDataPaint.alpha = 255
        for (index in 0 until mDefaultCount) {
            val percent = mDefaultData[index] / mMaxValue
            val tempX = mMaxRadius * cos(angle * index) * percent
            val tempY = mMaxRadius * sin(angle * index) * percent
            if (index == 0) {
                mDrawPath.moveTo(tempX, 0f)
            } else {
                mDrawPath.lineTo(tempX, tempY)
            }
            canvas.drawCircle(tempX, tempY, 10f, mDataPaint)
        }
        //绘制边界线条
        mDataPaint.style = Paint.Style.STROKE
        //闭合线条
        mDrawPath.close()
        canvas.drawPath(mDrawPath, mDataPaint)
        //绘制填充区
        mDataPaint.alpha = 127
        mDataPaint.style = Paint.Style.FILL_AND_STROKE
        canvas.drawPath(mDrawPath, mDataPaint)
    }

    /**
     * 绘制各个末端的文本数据
     */
    private fun drawTexts(canvas: Canvas) {
        val fontMetrics = mTextPaint.fontMetrics
        //获取文本高度
        val fontHeight: Float = fontMetrics.descent - fontMetrics.ascent
        for (index: Int in 0 until mDefaultCount) {
            val tempX = (mMaxRadius + fontHeight / 2) * cos(angle * index)
            val tempY = (mMaxRadius + fontHeight / 2) * sin(angle * index)
            if (angle * index >= 0 && angle * index < PI / 2) {
                //第四象限
                canvas.drawText(mDefTitle[index], tempX, tempY, mTextPaint)
            } else if (angle * index >= 3 * PI / 2 && angle * index < PI * 2) {
                //第三象限
                canvas.drawText(mDefTitle[index], tempX, tempY, mTextPaint)
            } else if (angle * index > PI / 2 && angle * index <= PI) {
                //第二象限
                val dis = mTextPaint.measureText(mDefTitle[index])//文本长度
                canvas.drawText(mDefTitle[index], tempX - dis, tempY, mTextPaint)
            } else if (angle * index >= Math.PI && angle * index < 3 * Math.PI / 2) {
                //第1象限
                val dis = mTextPaint.measureText(mDefTitle[index])//文本长度
                canvas.drawText(mDefTitle[index], tempX - dis, tempY, mTextPaint)
            }
        }
    }

    //绘制中心到末端的直线
    private fun drawLines(canvas: Canvas) {
        for (index in 0..mDefaultCount) {
            mDrawPath.reset()
            mDrawPath.moveTo(0f, 0f)
            val tempX = mMaxRadius * cos(angle * index)
            val tempY = mMaxRadius * sin(angle * index)
            mDrawPath.lineTo(tempX, tempY)
            canvas.drawPath(mDrawPath, mRadarPaint)
        }
    }


    /**
     * 绘制网格线
     */
    private fun drawPolygon(canvas: Canvas) {
        //控制绘制的层级  中心点不需要绘制  所以从1开始
        //每一层之间的距离
        val distance = mMaxRadius / (mDefaultCount - 1)
        //计算每一层的绘制半径
        for (count in 1 until mDefaultCount) {
            // 计算当前的半径
            val tempRadius = distance * count
            //重置path的状态  复用
            mDrawPath.reset()
            //绘制每一层的线条
            for (index in 0 until mDefaultCount) {
                if (index == 0) {
                    mDrawPath.moveTo(tempRadius, 0f)
                } else {
                    val tempX = tempRadius * cos(angle * index)
                    val tempY = tempRadius * sin(angle * index)
                    mDrawPath.lineTo(tempX, tempY)
                }
            }
            //闭合路径
            mDrawPath.close()
            canvas.drawPath(mDrawPath, mRadarPaint)
        }
    }

    /*------------------------------------------- 对外公开的方法 --------------------------------------------*/

    /**
     * 设置数据
     *
     * @param data 数据的数组
     */
    fun setData(data: FloatArray) {
        if (data.size > mDefTitle.size) {
            this.mDefaultCount = mDefTitle.size
        } else {
            this.mDefaultCount = data.size
        }
        this.mDefaultData = data
        postInvalidate()
    }

    /**
     * 设置数据
     *
     * @param data 数据List
     */
    fun setData(data: List<Float>) {
        if (data.size > mDefTitle.size) {
            this.mDefaultCount = mDefTitle.size
        } else {
            this.mDefaultCount = data.size
        }
        this.mDefaultData = data.toFloatArray()
        postInvalidate()
    }

    /**
     * 设置标题
     */
    fun setTitle(data: Array<String>) {
        if (data.size > mDefaultData.size) {
            this.mDefaultCount = mDefaultData.size
        } else {
            this.mDefaultCount = data.size
        }
        this.mDefTitle = data
        postInvalidate()
    }

    /**
     * 设置标题
     */
    fun setTitle(data: List<String>) {
        if (data.size > mDefaultData.size) {
            this.mDefaultCount = mDefaultData.size
        } else {
            this.mDefaultCount = data.size
        }
        this.mDefTitle = data.toTypedArray()
        postInvalidate()
    }

    /**
     * 设置Value的最大值
     */
    fun setMaxValue(maxValue: Float) {
        this.mMaxValue = maxValue
        postInvalidate()
    }

    /**
     * 设置雷达的线条颜色
     */
    fun setLineColor(color: Int) {
        this.mRadarPaint.color = color
        postInvalidate()
    }

    /**
     * 设置表示点和覆盖区的颜色
     */
    fun setRegionColor(color: Int) {
        this.mDataPaint.color = color
        postInvalidate()
    }

    /**
     * 设置文本颜色
     */
    fun setTextColor(color: Int) {
        this.mTextPaint.color = color
        postInvalidate()
    }
}