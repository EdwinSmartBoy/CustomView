package com.superlink.customview.sample.elastic

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation


/**
 * 创 建 者   edwin
 * 创建时间   2018/5/24 0024
 * 描    述   使用三阶贝塞尔曲线实现弹性的圆
 *
 * <p>
 * 更 新 者    edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */

class ElasticCircleView : View {

    private val mPaint: Paint = Paint()
    private val mPath: Path = Path()

    private var mCenterX: Float = 0f
    private var mCenterY: Float = 0f

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var maxLength: Float = 0f
    private var mInterpolatedTime: Float = 0f
    private var stretchDistance: Float = 0f
    private var moveDistance: Float = 0f
    private var cDistance: Float = 0f
    private var radius: Float = 0f
    private var C: Float = 0f

    private val blackMagic = 0.551915024494f

    private val p2: VPoint = VPoint()
    private val p4: VPoint = VPoint()

    private val p1: HPoint = HPoint()
    private val p3: HPoint = HPoint()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPaint()
    }

    private fun initialPaint() {
        mPaint.strokeWidth = 1f
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.parseColor("#FE626D")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mCenterX = w / 2f
        this.mCenterY = h / 2f
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        this.mWidth = width
        this.mHeight = height

        this.radius = 50f
        this.C = radius * blackMagic

        this.stretchDistance = radius
        this.moveDistance = radius * (3 / 5)

        this.cDistance = C * 0.45f
        this.maxLength = width - 2 * radius
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPath.reset()
        canvas!!.translate(0f, mCenterY)

        if (mInterpolatedTime in 0.0..0.2) {
            model1(mInterpolatedTime)
        } else if (mInterpolatedTime > 0.2 && mInterpolatedTime <= 0.5) {
            model2(mInterpolatedTime)
        } else if (mInterpolatedTime > 0.5 && mInterpolatedTime <= 0.8) {
            model3(mInterpolatedTime)
        } else if (mInterpolatedTime > 0.8 && mInterpolatedTime <= 0.9) {
            model4(mInterpolatedTime)
        } else if (mInterpolatedTime > 0.9 && mInterpolatedTime <= 1) {
            model5(mInterpolatedTime)
        }

        var offset = maxLength * (mInterpolatedTime - 0.2f)
        offset = if (offset > 0) offset else 0f

        p1.adjustAllX(offset)
        p2.adjustAllX(offset)
        p3.adjustAllX(offset)
        p4.adjustAllX(offset)

        mPath.moveTo(p1.hX, p1.hY)
        mPath.cubicTo(p1.right.x, p1.right.y, p2.bottom.x, p2.bottom.y, p2.vX, p2.vY)
        mPath.cubicTo(p2.top.x, p2.top.y, p3.right.x, p3.right.y, p3.hX, p3.hY)
        mPath.cubicTo(p3.left.x, p3.left.y, p4.top.x, p4.top.y, p4.vX, p4.vY)
        mPath.cubicTo(p4.bottom.x, p4.bottom.y, p1.left.x, p1.left.y, p1.hX, p1.hY)

        canvas.drawPath(mPath, mPaint)
    }

    private fun model0() {
        p1.setY(radius)
        p3.setY(-radius)
        p1.hX = 0f
        p3.hX = p1.hX
        p1.left.x = -C
        p3.left.x = p1.left.x
        p1.right.x = C
        p3.right.x = p1.right.x

        p2.setX(radius)
        p4.setX(-radius)
        p4.vY = 0f
        p2.vY = p4.vY
        p4.top.y = -C
        p2.top.y = p4.top.y
        p4.bottom.y = C
        p2.bottom.y = p4.bottom.y
    }

    private fun model1(time: Float) {
        model0()
        p2.setX(radius + stretchDistance * time * 5)
    }

    private fun model2(time: Float) {//0.2~0.5
        var time = time
        model1(0.2f)
        time = (time - 0.2f) * (10f / 3)
        p1.adjustAllX(stretchDistance / 2 * time)
        p3.adjustAllX(stretchDistance / 2 * time)
        p2.adjustY(cDistance * time)
        p4.adjustY(cDistance * time)
    }

    private fun model3(time: Float) {//0.5~0.8
        var time = time
        model2(0.5f)
        time = (time - 0.5f) * (10f / 3)
        p1.adjustAllX(stretchDistance / 2 * time)
        p3.adjustAllX(stretchDistance / 2 * time)
        p2.adjustY(-cDistance * time)
        p4.adjustY(-cDistance * time)

        p4.adjustAllX(stretchDistance / 2 * time)

    }

    private fun model4(time: Float) {//0.8~0.9
        var time = time
        model3(0.8f)
        time = (time - 0.8f) * 10
        p4.adjustAllX(stretchDistance / 2 * time)
    }

    private fun model5(time: Float) {
        var time = time
        model4(0.9f)
        time -= 0.9f
        p4.adjustAllX((Math.sin(Math.PI * time.toDouble() * 10.0) * (2 / 10f * radius)).toFloat())
    }

    internal inner class VPoint {

        var vX: Float = 0f
        var vY: Float = 0f

        var top = PointF()
        var bottom = PointF()

        fun setX(x: Float) {
            this.vX = x
            top.x = x
            bottom.x = x
        }

        fun adjustY(offset: Float) {
            top.y -= offset
            bottom.y += offset
        }

        fun adjustAllX(offset: Float) {
            this.vX += offset
            top.x += offset
            bottom.x += offset
        }
    }

    internal inner class HPoint {

        var hX: Float = 0f
        var hY: Float = 0f

        var left = PointF()
        var right = PointF()

        fun setY(y: Float) {
            this.hY = y
            left.y = y
            right.y = y
        }

        fun adjustAllX(offset: Float) {
            this.hX += offset
            left.x += offset
            right.x += offset
        }
    }

    private inner class MoveAnimation : Animation() {

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            mInterpolatedTime = interpolatedTime
            invalidate()
        }
    }

    fun startAnimation() {
        mPath.reset()
        mInterpolatedTime = 0f
        val move = MoveAnimation()
        move.duration = 3000
        move.interpolator = AccelerateDecelerateInterpolator()
        move.repeatCount = Animation.INFINITE
        move.repeatMode = Animation.REVERSE
        startAnimation(move)
    }

}