package com.superlink.customview.sample.check

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.superlink.customview.R


/**
 * 创建者      Created by Edwin
 * 创建时间    2018/5/19
 * 描述        自定义选中与取消选中的view
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
class CheckView : View {

    companion object {
        private const val ANIM_NULL = 0         //动画状态-没有
        private const val ANIM_CHECK = 1        //动画状态-开启
        private const val ANIM_UNCHECK = 2      //动画状态-结束
    }

    private val TAG = CheckView.Companion.javaClass.canonicalName

    private var mWidth: Int = 0
    private var mHeight: Int = 0  // 宽高
    private lateinit var mHandler: Handler // handler

    private lateinit var mPaint: Paint
    private lateinit var okBitmap: Bitmap

    private var animCurrentPage = -1   // 当前页码
    private val animMaxPage = 13       // 总页数
    private var animDuration: Long = 200     // 动画时长
    private var animState = ANIM_NULL  // 动画状态

    private var isCheck = false        // 是否只选中状态

    // 绘制区域的位置
    private val mAreaRect: Rect = Rect()
    // 绘制图片的位置
    private val mBitmapRect: Rect = Rect()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPaint()
        initialBitmap(context)
        initialHandler()
    }

    private fun initialPaint() {
        mPaint = Paint()
        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
    }

    private fun initialBitmap(context: Context?) {
        okBitmap = BitmapFactory.decodeResource(context!!.resources, R.drawable.check_mark)
    }

    private fun initialHandler() {
        mHandler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                if (animCurrentPage in 0 until animMaxPage) {
                    invalidate()
                    if (animState == ANIM_NULL) {
                        return
                    }
                    if (animState == ANIM_CHECK) {
                        animCurrentPage++
                    } else if (animState == ANIM_UNCHECK) {
                        animCurrentPage--
                    }
                    this.sendEmptyMessageDelayed(0, animDuration / animMaxPage)
                    Log.e(TAG, "Count = $animCurrentPage")
                } else {
                    animCurrentPage = if (isCheck) {
                        animMaxPage - 1
                    } else {
                        -1
                    }
                    invalidate()
                    animState = ANIM_NULL
                }
            }
        }
    }

    /**
     * 确定View的大小
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mWidth = w
        this.mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //将画布的中心点移动到屏幕中心
        canvas!!.translate(mWidth / 2f, mHeight / 2f)
        //绘制背景
        canvas.drawCircle(0f, 0f, 120f, mPaint)
        //获取图片边长
        val bitmapHeight = okBitmap.height
        //设置图片展示的区域
        mBitmapRect.left = bitmapHeight * animCurrentPage
        mBitmapRect.top = 0
        mBitmapRect.right = bitmapHeight * (animCurrentPage + 1)
        mBitmapRect.bottom = bitmapHeight

        //设置控件绘制的区域
        mAreaRect.left = -60
        mAreaRect.top = -60
        mAreaRect.right = 60
        mAreaRect.bottom = 60

        canvas.drawBitmap(okBitmap, mBitmapRect, mAreaRect, null)
    }

    /**
     * 确认选择
     */
    fun check() {
        if (animState != ANIM_NULL || isCheck) {
            return
        }
        this.animState = ANIM_CHECK
        animCurrentPage = 0
        mHandler.sendEmptyMessageDelayed(0, animDuration / animMaxPage)
        isCheck = true
    }

    /**
     * 取消选择
     */
    fun unCheck() {
        if (animState != ANIM_NULL || !isCheck) {
            return
        }
        this.animState = ANIM_UNCHECK
        animCurrentPage = animMaxPage - 1
        mHandler.sendEmptyMessageDelayed(0, animDuration / animMaxPage)
        isCheck = false
    }

    /**
     * 设置动画时长
     *
     * @param duration
     */
    fun setDuration(duration: Long) {
        if (animDuration <= 0) {
            return
        }
        this.animDuration = duration
    }

    /**
     * 设置背景圆形颜色
     *
     * @param color
     */
    fun setBgColor(color: Int) {
        mPaint.color = color
    }

    fun setBgColor(color: String) {
        mPaint.color = Color.parseColor(color)
    }
}