package com.superlink.customview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.superlink.customview.sample.sector.PieView

class MainActivity : AppCompatActivity() {

    private lateinit var mPieView: PieView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO 自定义扇形的操作
        //mPieView = findViewById(R.id.pieView)
        //var sectorData: SectorData
        //val arrayList: ArrayList<SectorData> = ArrayList()
        //for (index: Int in 0 until 9) {
        //    sectorData = SectorData(name = "edwin$index", value = Random().nextFloat())
        //    arrayList.add(sectorData)
        //}
        //mPieView.setData(arrayList)
    }
}
