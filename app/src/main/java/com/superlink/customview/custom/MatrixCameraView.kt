package com.superlink.customview.custom

import android.content.Context
import android.graphics.Camera
import android.util.AttributeSet
import android.view.View

/**
 * 创 建 者   edwin
 * 创建时间   2018/6/4 0004
 * 描    述   Matrix Camera 基本操作
 *
 * <p>
 * 更 新 者    edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */

class MatrixCameraView: View {

    private lateinit var mCamera: Camera

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initialCamera()
    }

    /**
     * Camera常用方法表
     *
     * 方法类别	  相关API	                                 简介
     * 基本方法	  save、restore	                             保存、 回滚
     * 常用方法	  getMatrix、applyToCanvas	                 获取Matrix、应用到画布
     * 平移	      translate	                                 位移
     * 旋转	      rotate (API 12)、rotateX、rotateY、rotateZ	 各种旋转
     * 相机位置	  setLocation (API 12)
     *            getLocationX (API 16)
     *            getLocationY (API 16)
     *            getLocationZ (API 16)	                     设置与获取相机位置
     *
     *
     * 坐标系	    2D坐标系	  3D坐标系
     *
     * 原点默认位置	左上角	  左上角
     * X 轴默认方向	右	      右
     * Y 轴默认方向	下	      上
     * Z 轴默认方向  无	     垂直屏幕向内
     *
     *
     * 平移	重点内容
     *
     * x轴	2D 和 3D 相同。
     * y轴	2D 和 3D 相反。
     * z轴	近大远小、视线相交。
     *
     * 要点
     *
     * View显示状态取决于View和摄像机之间的相对位置
     * View和相机的Z轴距离不能为0
     */

    private fun initialCamera() {
        mCamera = Camera()

    }

}