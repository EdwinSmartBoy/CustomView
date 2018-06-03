package com.superlink.customview

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.superlink.customview.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onBindView() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun initial(savedInstanceState: Bundle?) {

    }

    override fun onLoadData() {

    }
}
