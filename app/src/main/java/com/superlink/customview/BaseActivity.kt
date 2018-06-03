package com.superlink.customview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * 创建者      Created by Edwin
 * 创建时间    2018/6/3
 * 描述        ${TODO}
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBindView()
        initial(savedInstanceState)
        onLoadData()
    }

    /**
     * 绑定布局
     */
    abstract fun onBindView()

    /**
     * 初始化
     */
    protected open fun initial(savedInstanceState: Bundle?) {

    }

    /**
     * 加载数据
     */
    protected open fun onLoadData() {

    }

}