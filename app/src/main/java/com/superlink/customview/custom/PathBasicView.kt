package com.superlink.customview.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 创建者      Created by Edwin
 * 创建时间    2018/5/20
 * 描述        基本的path操作View
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
class PathBasicView : View {

    /**
     * 使用Path不仅能够绘制简单图形，也可以绘制这些比较复杂的图形。另外，根据路径绘制文本和剪裁画布都会用到Path。
     */

    private val mPaint: Paint = Paint()

    private val mPath: Path = Path()
    private val mSrcPath: Path = Path()

    private val mRectF: RectF = RectF()

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialPaint()
    }

    private fun initialPaint() {
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 10f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mWidth = w
        this.mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //将画布中心移动到屏幕中心
        canvas!!.translate(mWidth / 2f, mHeight / 2f)

        /**
         * 第1组: moveTo、 setLastPoint、 lineTo 和 close
         */
        // 创建两条线
        // 第一次由于之前没有过操作，所以默认点就是坐标原点O，结果就是坐标原点O到A(200,200)之间连直线(用蓝色圈1标注)。
        // mPath.lineTo(200f, 200f)
        // 第二次lineTo的时候，由于上次的结束位置是A(200,200),所以就是A(200,200)到B(200,0)之间的连线(用蓝色圈2标注)。
        // mPath.lineTo(200f, 0f)
        // 绘制线条到画布中
        // canvas.drawPath(mPath, mPaint)

        // 方法名	        简介	                        是否影响之前的操作	是否影响之后操作
        // moveTo	    移动下一次操作的起点位置	    否	                是
        // setLastPoint	设置之前操作的最后一个点位置	是	                是

        // 移动到（200f, 100f）
        // mPath.moveTo(200f, 100f)
        // mPath.lineTo(200f, 0f)
        // canvas.drawPath(mPath, mPaint)

        // etLastPoint是重置上一次操作的最后一个点，在执行完第一次的lineTo的时候，
        // 最后一个点是A(200,200),而setLastPoint更改最后一个点为C(200,100),所以在实际执行的时候，
        // 第一次的lineTo就不是从原点O到A(200,200)的连线了，而变成了从原点O到C(200,100)之间的连线了。
        // mPath.lineTo(200f, 200f)
        // mPath.setLastPoint(200f, 100f)
        // mPath.lineTo(200f, 0f)
        // canvas.drawPath(mPath, mPaint)

        // close方法用于连接当前最后一个点和最初的一个点(如果两个点不重合的话)，最终形成一个封闭的图形。
        // mPath.lineTo(200f, 200f)
        // mPath.lineTo(200f, 0f)
        // mPath.close()
        // canvas.drawPath(mPath, mPaint)

        /**
         * 第2组: addXxx与arcTo
         *
         * 第一类(基本形状) 这一类就是在path中添加一个基本形状，基本形状部分和前面所讲的绘制基本形状并无太大差别，
         *
         * // 第一类(基本形状)
         *  圆形
         * public void addCircle (float x, float y, float radius, Path.Direction dir)
         *  椭圆
         * public void addOval (RectF oval, Path.Direction dir)
         *  矩形
         * public void addRect (float left, float top, float right, float bottom, Path.Direction dir)
         * public void addRect (RectF rect, Path.Direction dir)
         *  圆角矩形
         * public void addRoundRect (RectF rect, float[] radii, Path.Direction dir)
         * public void addRoundRect (RectF rect, float rx, float ry, Path.Direction dir)
         *
         * Direction的意思是 方向，趋势。
         *
         * 类型	解释	              翻译   作用
         * CW	clockwise	      顺时针 在添加图形时确定闭合顺序(各个点的记录顺序)
         * CCW	counter-clockwise 逆时针 对图形的渲染结果有影响(是判断图形渲染的重要条件)
         */
        // mPath.addRect(-200f, -200f, 200f, 200f, Path.Direction.CW)
        // canvas.drawPath(mPath, mPaint)

        // mPath.addRect(-200f, -200f, 200f, 200f, Path.Direction.CCW)
        // mPath.setLastPoint(-300f, 300f)
        // canvas.drawPath(mPath, mPaint)

        /**
         * 第二类(Path)
         *
         * 将两个Path合并成为一个。
         * public void addPath (Path src)
         *
         * 将src进行了位移之后再添加进当前path中。
         * public void addPath (Path src, float dx, float dy)
         *
         * 将src添加到当前path之前先使用Matrix进行变换。
         * public void addPath (Path src, Matrix matrix)
         */
        // canvas.scale(1f, -1f)
        // mPath.addRect(-200f, -200f, 200f, 200f, Path.Direction.CW)
        // mSrcPath.addCircle(0f, 0f, 100f, Path.Direction.CW)
        // //进行第二种变换
        // mPath.addPath(mSrcPath, 0f, 200f)
        // // 新建的两个Path(矩形和圆形)中心都是坐标原点，我们在将包含圆形的path添加到包含矩形的path之前将其进行移动了一段距离
        // canvas.drawPath(mPath, mPaint)

        /**
         * 第三类(addArc与arcTo)
         *
         * addArc
         * public void addArc (RectF oval, float startAngle, float sweepAngle)
         *
         * arcTo
         * public void arcTo (RectF oval, float startAngle, float sweepAngle)
         * public void arcTo (RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo)
         *
         * forceMoveTo是什么作用呢？
         *
         * forceMoveTo	含义	                                                等价方法
         * true	        将最后一个点移动到圆弧起点，即不连接最后一个点与圆弧起点	public void addArc (RectF oval, float startAngle, float sweepAngle)
         * false	    不移动，而是连接最后一个点与圆弧起点	                public void arcTo (RectF oval, float startAngle, float sweepAngle)
         *
         * 名称	    作用                	区别
         * addArc	添加一个圆弧到path	直接添加一个圆弧到path中
         * arcTo	添加一个圆弧到path	添加一个圆弧到path，如果圆弧的起点和上次最后一个坐标点不相同，就连接两个点
         */
        // canvas.scale(1f, -1f)
        // mPath.lineTo(100f, 100f)

        // mRectF.left = 0f
        // mRectF.top = 0f
        // mRectF.right = 300f
        // mRectF.bottom = 300f

        // 当forceMoveTo为true时，如下两个方法等价
        //mPath.addArc(mRectF, 0f, 270f)
        //等价于该方法 mPath.arcTo(mRectF, 0f, 270f, true)

        // 当forceMoveTo为false时，如下两个方法等价
        //mPath.arcTo(mRectF, 0f, 270f, false)
        //等价于该方法 mPath.arcTo(mRectF, 0f, 270f)

        //canvas.drawPath(mPath, mPaint)

        /**
         * 第3组：isEmpty、 isRect、isConvex、 set 和 offset
         */

        // public boolean isEmpty () 判断path中是否包含内容
        // println("Path isEmpty = ${mPath.isEmpty}")
        // mPath.lineTo(100f, 100f)
        // println("Path isEmpty = ${mPath.isEmpty}")

        // public boolean isRect (RectF rect) 判断path是否是一个矩形，如果是一个矩形的话，会将矩形的信息存放进参数rect中。
        // mPath.lineTo(0f, 400f)
        // mPath.lineTo(400f, 400f)
        // mPath.lineTo(400f, 0f)
        // mPath.lineTo(0f, 0f)

        // val isRect = mPath.isRect(mRectF)
        // println("isRect = $isRect, left = ${mRectF.left}, top = ${mRectF.top}, right = ${mRectF.right}, bottom = ${mRectF.bottom}")

        // public void set (Path src) 将新的path赋值到现有path。
        canvas.scale(1f, -1f)

        mPath.addRect(-200f, -200f, 200f, 200f, Path.Direction.CW)
        mSrcPath.addCircle(0f, 0f, 100f, Path.Direction.CW)

        mPath.set(mSrcPath)

        canvas.drawPath(mPath, mPaint)

        // public void offset (float dx, float dy)
        // public void offset (float dx, float dy, Path dst)

        // dst状态	      效果
        // dst不为空	      将当前path平移后的状态存入dst中，不会影响当前path
        // dst为空(null)	  平移将作用于当前path，相当于第一种方法
    }
}