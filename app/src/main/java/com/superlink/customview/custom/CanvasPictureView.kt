package com.superlink.customview.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.PictureDrawable
import android.util.AttributeSet
import android.view.View
import com.superlink.customview.R

/**
 * 创建者      Created by Edwin
 * 创建时间    2018/5/19
 * 描述        画布Canvas的drawPicture操作
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
class CanvasPictureView : View {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //startRecording()
        readBitmapFromLocal()
    }

    /**
     * (2)drawBitmap
     *
     * 通过Bitmap创建	      复制一个已有的Bitmap(新Bitmap状态和原有的一致) 或者 创建一个空白的Bitmap(内容可改变)
     * 通过BitmapDrawable获取  从资源文件 内存卡 网络等地方获取一张图片并转换为内容不可变的Bitmap
     * 通过BitmapFactory获取	  从资源文件 内存卡 网络等地方获取一张图片并转换为内容不可变的Bitmap
     *
     * 主要推荐第三种获取图片的方式
     */

    private val mPaint: Paint = Paint()

    private val mRectF: RectF = RectF()
    // 1.创建Picture
    private val mPicture: Picture = Picture()
    private lateinit var pictureDrawable: PictureDrawable

    private lateinit var mBitmap: Bitmap
    private val mMatrix: Matrix = Matrix()

    // 指定图片绘制区域(左上角的四分之一)
    private val mAreaRect: Rect = Rect()
    // 指定图片在屏幕上显示的区域
    private val mBitmapRect: Rect = Rect()

    private var mWith: Int = 0
    private var mHeight: Int = 0

    // 2.录制内容方法
    private fun startRecording() {
        // 开始录制 返回canvas对象
        val canvas: Canvas = mPicture.beginRecording(500, 500)
        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.FILL
        // canvas位移
        canvas.translate(250f, 250f)
        // 画圆
        canvas.drawCircle(0f, 0f, 100f, mPaint)
        // 结束录制
        mPicture.endRecording()
        pictureDrawable = PictureDrawable(mPicture)
    }

    /**
     * PS：你可以把Picture看作是一个录制Canvas操作的录像机。
     *
     * 相关方法	简介
     * public int getWidth ()	                                    获取宽度
     * public int getHeight ()	                                    获取高度
     * public Canvas beginRecording (int width, int height)	        开始录制 (返回一个Canvas，在Canvas中所有的绘制都会存储在Picture中)
     * public void endRecording ()	                                结束录制
     * public void draw (Canvas canvas)	                            将Picture中内容绘制到Canvas中
     * public static Picture createFromStream (InputStream stream)	(已废弃)通过输入流创建一个Picture
     * public void writeToStream (OutputStream stream)	            (已废弃)将Picture中内容写出到输出流中

     * beginRecording 和 endRecording 是成对使用的，一个开始录制，一个是结束录制，两者之间的操作将会存储在Picture中。
     *
     * 录制内容，即将一些Canvas操作用Picture存储起来，录制的内容是不会直接显示在屏幕上的，只是存储起来了而已。
     *
     * 想要将Picture中的内容显示出来就需要手动调用播放(绘制)，将Picture中的内容绘制出来可以有以下几种方法：
     *
     * 1	使用Picture提供的draw方法绘制。
     * 2	使用Canvas提供的drawPicture方法绘制。
     * 3	将Picture包装成为PictureDrawable，使用PictureDrawable的draw方法绘制。
     *
     * 以上几种方法主要区别：
     *
     * 主要区别	          分类  	          简介
     * 是否对Canvas有影响  1有影响
     *                    2,3不影响	      此处指绘制完成后是否会影响Canvas的状态(Matrix clip等)
     * 可操作性强弱	      1可操作性较弱
     *                    2,3可操作性较强  此处的可操作性可以简单理解为对绘制结果可控程度。
     */

    private fun readBitmapFromLocal() {
        mBitmap = BitmapFactory.decodeResource(this.context.resources, R.raw.test)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mWith = w
        this.mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // PS：这种方法在比较低版本的系统上绘制后可能会影响Canvas状态，所以这种方法一般不会使用。
        //mPicture.draw(canvas)

        // 和使用Picture的draw方法不同，Canvas的drawPicture不会影响Canvas状态。
        //mRectF.left = 0f
        //mRectF.top = 0f
        //mRectF.right = mPicture.width.toFloat()
        //mRectF.bottom = 200f
        //canvas!!.drawPicture(mPicture, mRectF)

        //将Picture包装成为PictureDrawable，使用PictureDrawable的draw方法绘制。
        // PS:此处setBounds是设置在画布上的绘制区域，并非根据该区域进行缩放，也不是剪裁Picture，每次都从Picture的左上角开始绘制。
        //pictureDrawable.setBounds(0, 0, 250, mPicture.height)
        //pictureDrawable.draw(canvas)

        //canvas!!.drawBitmap(mBitmap, mMatrix, mPaint)

        // 绘制时指定了图片左上角的坐标(距离坐标原点的距离)：
        // 注意：此处指定的是与坐标原点的距离，并非是与屏幕顶部和左侧的距离, 虽然默认状态下两者是重合的，但是也请注意分别两者的不同。
        //canvas!!.drawBitmap(mBitmap, 200f, 500f, mPaint)

        //// 第三种
        // public void drawBitmap (Bitmap bitmap, Rect src, Rect dst, Paint paint)
        // public void drawBitmap (Bitmap bitmap, Rect src, RectF dst, Paint paint)
        /**
         * 名称	    作用
         * Rect src	指定绘制图片的区域
         * Rect dst 或RectF dst	指定图片在屏幕上显示(绘制)的区域
         */
        canvas!!.translate(mWith / 2f, mHeight / 2f)
        //指定图片绘制区域(左上角的四分之一)
        mBitmapRect.left = 0
        mBitmapRect.top = 0
        mBitmapRect.right = mBitmap.width / 2
        mBitmapRect.bottom = mBitmap.height / 2

        //指定图片在屏幕上显示的区域
        mAreaRect.left = 0
        mAreaRect.top = 0
        mAreaRect.right = 200
        mAreaRect.bottom = 400

        canvas.drawBitmap(mBitmap, mBitmapRect, mAreaRect, null)
    }

}