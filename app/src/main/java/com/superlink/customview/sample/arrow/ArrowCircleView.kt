package com.superlink.customview.sample.arrow

import android.content.Context
import android.graphics.*
import android.graphics.PathMeasure.POSITION_MATRIX_FLAG
import android.graphics.PathMeasure.TANGENT_MATRIX_FLAG
import android.util.AttributeSet
import android.view.View
import com.superlink.customview.R


/**
 * 创建者      Created by Edwin
 * 创建时间    2018/5/26
 * 描述        箭头旋转View
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
class ArrowCircleView : View {

    private var currentValue = 0f     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度
    private val pos: FloatArray? = FloatArray(2)                // 当前点的实际位置
    private val tan: FloatArray = FloatArray(2)                // 当前点的tangent值,用于计算图片所需旋转的角度
    private lateinit var mBitmap: Bitmap             // 箭头图片
    private val mOptions: BitmapFactory.Options = BitmapFactory.Options()
    private val mMatrix: Matrix = Matrix()             // 矩阵,用于对图片进行一些操作

    private var mCenterX: Float = 0f
    private var mCenterY: Float = 0f

    private val mPath: Path = Path()

    private val mPaint: Paint = Paint()

    private val mPathMeasure: PathMeasure = PathMeasure()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initial(context)
    }

    private fun initial(context: Context?) {

        mPaint.strokeWidth = 4f
        mPaint.color = Color.RED
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE

        mOptions.inSampleSize = 2
        mBitmap = BitmapFactory.decodeResource(context!!.resources, R.mipmap.arrow)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mCenterX = w / 2f
        this.mCenterY = h / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.translate(mCenterX, mCenterY)

        this.currentValue += 0.1f
        if (currentValue >= 1) {
            currentValue = 0f
        }

        mPath.addCircle(0f, 0f, 200f, Path.Direction.CW)
        mPathMeasure.setPath(mPath, false)

        //mPathMeasure.getPosTan(mPathMeasure.length * currentValue, pos, tan)

        // 通过 Matrix 来设置图片对旋转角度和位移，
        //mMatrix.reset()
        // 两个数值 tan[0] 和 tan[1] 来描述这个切线的方向(切线方向与x轴夹角) ，
        // tan[0] = cos = 邻边(单位圆x坐标)
        // tan[1] = sin = 对边(单位圆y坐标)
        // 使用 Math.atan2(tan[1], tan[0]) 将 tan 转化为角(单位为弧度)的时候要注意参数顺序。
        //val degrees: Double = Math.atan2(tan[1].toDouble(), tan[0].toDouble()) * 180.0 / PI
        //mMatrix.postRotate(degrees.toFloat(), mBitmap.width / 2f, mBitmap.height / 2f)
        //mMatrix.postTranslate(pos!![0] - mBitmap.width / 2, pos[1] - mBitmap.height / 2)

        //canvas.drawPath(mPath, mPaint)
        //canvas.drawBitmap(mBitmap, mMatrix, mPaint)

        //postInvalidate()

        /**
         * 6.getMatrix
         *
         * 这个方法是用于得到路径上某一长度的位置以及该位置的正切值的矩阵：
         *
         * boolean getMatrix (float distance, Matrix matrix, int flags)
         *
         * 参数	            作用                      	备注
         * 返回值(boolean)  判断获取是否成功	            true表示成功，数据会存入matrix中，false 失败，matrix内容不会改变
         * distance	        距离 Path 起点的长度	        取值范围: 0 <= distance <= getLength
         * matrix	        根据flags封装好的matrix	    会根据 flags 的设置而存入不同的内容
         * flags	        规定哪些内容会存入到matrix中	可选择POSITION_MATRIX_FLAG(位置)    ANGENT_MATRIX_FLAG(正切)
         *
         * measure.getMatrix(distance, matrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
         * 两个选项都想选择，可以将两个选项之间用 | 连接起来
         */

        // 使用 getMatrix 方法的确可以节省一些代码，不过这里依旧需要注意一些内容:
        // 1.对 matrix 的操作必须要在 getMatrix 之后进行，否则会被 getMatrix 重置而导致无效。
        // 2.矩阵对旋转角度默认为图片的左上角，我们此处需要使用 preTranslate 调整为图片中心。
        // 3.pre(矩阵前乘) 与 post(矩阵后乘) 的区别，此处请等待后续的文章或者自行搜索。

        mPathMeasure.getMatrix(mPathMeasure.length * currentValue, mMatrix, POSITION_MATRIX_FLAG or TANGENT_MATRIX_FLAG)
        mMatrix.preTranslate(-mBitmap.width / 2f, -mBitmap.height / 2f)

        canvas.drawPath(mPath, mPaint)
        canvas.drawBitmap(mBitmap, mMatrix, mPaint)

        postInvalidate()
    }
}