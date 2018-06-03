package com.superlink.customview

import android.databinding.DataBindingUtil
import android.widget.RadioGroup
import com.superlink.customview.databinding.ActivityMatrixOperaBinding

/**
 * 创建者      Created by Edwin
 * 创建时间    2018/6/3
 * 描述        矩阵操作实例
 *
 * 更新者      Edwin
 * 更新时间    ${TODO}
 * 更新描述    ${TODO}
 */
class MatrixOperaActivity : BaseActivity(), RadioGroup.OnCheckedChangeListener {

    private lateinit var mBinding: ActivityMatrixOperaBinding

    override fun onBindView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_matrix_opera)

        mBinding.group.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (group!!.checkedRadioButtonId) {
            R.id.point1 -> mBinding.matrixBasic.setPointCount(1)
            R.id.point2 -> mBinding.matrixBasic.setPointCount(2)
            R.id.point3 -> mBinding.matrixBasic.setPointCount(3)
            R.id.point4 -> mBinding.matrixBasic.setPointCount(4)
        }
    }
}
