package com.lxy.malllibrary.fragments

import android.widget.Toast

abstract class MallFragment : BaseFragment() {

    private var mTouchTime: Long = 0

    companion object {
        private const val WAIT_TIME = 2000L
    }

    protected fun exitWithDoubleClick(): Boolean {

        if (System.currentTimeMillis() - mTouchTime < WAIT_TIME) {
            _mActivity.finish()
        } else {
            mTouchTime = System.currentTimeMillis()
            Toast.makeText(_mActivity, "双击退出", Toast.LENGTH_SHORT).show()
        }

        return true
    }

    //关于文件或者Camera权限的内容
}