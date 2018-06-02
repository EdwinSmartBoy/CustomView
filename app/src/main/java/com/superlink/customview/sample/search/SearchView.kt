package com.superlink.customview.sample.search

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View

/**
 * 创建者      Created by Edwin
 * 创建时间    2018/5/30
 * 描述        使用path实现查询的View
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
class SearchView : View, AnimatorUpdateListener, AnimatorListener {

    enum class State {
        NONE, STARTING, SEARCHING, ENDING
    }

    //画笔
    private val mPaint: Paint = Paint()

    //画布中心
    private var mCenterX: Float = 0f
    private var mCenterY: Float = 0f

    //当前View的状态
    private var mCurrentState: State = State.NONE

    //外圆path
    private val mCirclePath: Path = Path()
    //搜索图标的path
    private val mSearchPath: Path = Path()

    //截取的部分圆弧
    private val mDst: Path = Path()

    //测量并截取path的工具
    private val mPathMeasure: PathMeasure = PathMeasure()

    //动画执行过程的值
    private var mAnimatorValue: Float = 0f

    // 默认的动效周期 2s
    private var defaultDuration: Long = 2000L

    //handler 更新界面
    private lateinit var mHandler: Handler

    // 判断是否已经搜索结束
    private var isOver = false

    private var count = 0

    // 控制各个过程的动画
    private val mStartingAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(defaultDuration)
    private val mSearchingAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(defaultDuration)
    private val mEndingAnimator: ValueAnimator = ValueAnimator.ofFloat(1f, 0f).setDuration(defaultDuration)

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //初始画笔
        initialPaint()
        //初始化各个属性动画的监听器
        initialAnimator()
        //初始化Handler
        initialHandler()

        mHandler.postDelayed({
            this.mCurrentState = State.STARTING
            //延迟五百毫秒执行动画
            mStartingAnimator.start()
        }, 500)
    }

    private fun initialAnimator() {
        mStartingAnimator.addUpdateListener(this)
        mSearchingAnimator.addUpdateListener(this)
        mEndingAnimator.addUpdateListener(this)

        mStartingAnimator.addListener(this)
        mSearchingAnimator.addListener(this)
        mEndingAnimator.addListener(this)
    }

    private fun initialHandler() {
        mHandler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when (mCurrentState) {
                    State.STARTING -> {
                        // 从开始动画转换好搜索动画
                        isOver = false
                        mCurrentState = State.SEARCHING
                        mStartingAnimator.removeAllListeners()
                        mSearchingAnimator.start()
                    }
                    State.SEARCHING -> {
                        // 如果搜索未结束 则继续执行搜索动画
                        if (!isOver) {
                            mSearchingAnimator.start()
                            count++
                            // count大于2则进入结束状态
                            if (count > 2) {
                                isOver = true
                            }
                        } else {
                            // 如果搜索已经结束 则进入结束动画
                            mCurrentState = State.ENDING
                            mEndingAnimator.start()
                        }
                    }
                    State.ENDING -> {
                        mCurrentState = State.NONE
                    }
                }
            }
        }
    }

    private fun initialPaint() {
        mPaint.strokeWidth = 10f
        mPaint.isAntiAlias = true
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.STROKE
        //边角圆形
        mPaint.strokeCap = Paint.Cap.ROUND

        //初始化两个path
        //放大镜
        val oval1 = RectF(-40f, -40f, 40f, 40f)
        mSearchPath.addArc(oval1, 45f, 359.9f)

        //圆环
        val oval2 = RectF(-100f, -100f, 100f, 100f)
        mCirclePath.addArc(oval2, 45f, 359.9f)

        val pos = FloatArray(2)

        //获取外环的点
        mPathMeasure.setPath(mCirclePath, false)
        mPathMeasure.getPosTan(0f, pos, null)
        //绘制搜索框的柄
        mSearchPath.lineTo(pos[0], pos[1])

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mCenterX = w / 2f
        this.mCenterY = h / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //将画布移到中心点
        canvas!!.translate(mCenterX, mCenterY)

        //动态绘制搜索框与外环
        drawSearch(canvas)
    }

    private fun drawSearch(canvas: Canvas) {
        when (mCurrentState) {
            State.NONE -> {
                canvas.drawPath(mSearchPath, mPaint)
            }
            State.STARTING -> {
                mDst.reset()
                mPathMeasure.setPath(mSearchPath, false)
                mPathMeasure.getSegment(mPathMeasure.length * mAnimatorValue, mPathMeasure.length, mDst, true)
                canvas.drawPath(mDst, mPaint)
            }
            State.SEARCHING -> {
                mDst.reset()
                mPathMeasure.setPath(mCirclePath, false)
                val stop = mPathMeasure.length * mAnimatorValue
                val start = (stop - (0.5f - Math.abs(mAnimatorValue - 0.5f)) * 200f)
                mPathMeasure.getSegment(start, stop, mDst, true)
                canvas.drawPath(mDst, mPaint)
            }
            State.ENDING -> {
                mDst.reset()
                mPathMeasure.setPath(mSearchPath, false)
                mPathMeasure.getSegment(mPathMeasure.length * mAnimatorValue, mPathMeasure.length, mDst, true)
                canvas.drawPath(mDst, mPaint)
            }
        }
    }

    override fun onAnimationUpdate(animation: ValueAnimator?) {
        this.mAnimatorValue = animation!!.animatedValue as Float
        postInvalidate()
    }

    override fun onAnimationEnd(animation: Animator?) {
        mHandler.sendEmptyMessage(0)
    }

    override fun onAnimationStart(animation: Animator?) {

    }

    override fun onAnimationRepeat(animation: Animator?) {

    }

    override fun onAnimationCancel(animation: Animator?) {

    }

}