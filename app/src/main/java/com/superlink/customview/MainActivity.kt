package com.superlink.customview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.superlink.customview.sample.check.CheckView

class MainActivity : AppCompatActivity() {

    private lateinit var mCheckView: CheckView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCheckView = findViewById(R.id.checkView)
        mCheckView.setBgColor("#FF5317")
        //mPieView.setData(arrayList)
    }

    fun check(view: View) {
        mCheckView.check()
    }

    fun unCheck(view: View) {
        mCheckView.unCheck()
    }

}
