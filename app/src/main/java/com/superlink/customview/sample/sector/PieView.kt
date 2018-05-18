package com.superlink.customview.sample.sector

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.min

/**
 * 创 建 者   edwin
 * 创建时间   2018/5/18 0018
 * 描    述   自定义扇形View
 *
 * <p>
 * 更 新 者    edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */

class PieView : View {

    //扇形颜色值
    private val colorArray = arrayListOf("CCFF00", "6495ED", "E32636", "800000", "808000",
            "FF8C69", "808080", "E6B800", "7CFC00")

    // 饼状图初始绘制角度
    private var mStartAngle = 0f
    // 数据
    private var mData: ArrayList<SectorData>? = null
    // 宽高
    private var mWidth: Int = 0
    // 高度
    private var mHeight: Int = 0
    // 画笔
    private val mPaint = Paint()

    private val rectF = RectF()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mPaint.style = Paint.Style.FILL
        //抗锯齿
        mPaint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mData == null || mData!!.size == 0) {
            return
        }
        println(mData)
        // 保存当前已经扫描过的角度
        var currentAngle = mStartAngle
        // 将画布的原点移动到屏幕中心
        canvas!!.translate(mWidth / 2f, mHeight / 2f)
        // 设置饼状图的半径  取宽度的半径(防止导致屏幕出现的问题)
        val arcR = min(mWidth, mHeight) / 2 * 0.8f
        // 绘制饼状图的区域
        rectF.left = -arcR
        rectF.right = arcR
        rectF.top = -arcR
        rectF.bottom = arcR
        //根据给定的数据绘制扇形区域
        mData!!.forEach {
            mPaint.color = Color.parseColor("#${it.color}")
            canvas.drawArc(rectF, currentAngle, it.angle, true, mPaint)
            currentAngle += it.angle
        }
    }

    /**
     * 设置起始角度
     *
     * @param startAngle 起始角度
     */
    fun setStartAngle(startAngle: Float) {
        mStartAngle = startAngle
        // 更改了数据需要重绘界面时要调用invalidate()这个函数重新绘制。
        invalidate()
    }

    /**
     * 设置数据
     */
    fun setData(datas: ArrayList<SectorData>) {
        initialData(datas)
    }

    private fun initialData(data: ArrayList<SectorData>?) {
        if (data == null || data.size == 0) {
            return
        }
        var sumValue = 0f
        for (index: Int in 0 until data.size) {
            val sectorData = data[index]
            sumValue += sectorData.value
            sectorData.color = colorArray[index % data.size]
        }
        var sumAngle = 0f
        data.forEach {
            //计算每个所占的百分比
            val percentage = it.value / sumValue
            //计算每个扇形的角度
            val angle = percentage * 360
            it.percentage = percentage
            it.angle = angle
            sumAngle += angle
            Log.i("angle", "angle = ${it.angle}")
        }
        // 更改了数据需要重绘界面时要调用invalidate()这个函数重新绘制。
        mData = data
        invalidate()
    }

}