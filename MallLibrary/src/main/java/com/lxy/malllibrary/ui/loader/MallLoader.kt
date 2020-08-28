package com.lxy.malllibrary.ui.loader

import android.content.Context

import android.view.Gravity
import androidx.appcompat.app.AppCompatDialog
import com.blankj.utilcode.util.ScreenUtils
import com.lxy.malllibrary.R

import com.wang.avi.AVLoadingIndicatorView
import com.wang.avi.Indicator
import com.wang.avi.indicators.BallClipRotateMultipleIndicator

object MallLoader {

    private val LOADER_SIZE_SCALE = 8
    private val LOADER_OFFSET_SCALE = 10

    private val LOADERS = ArrayList<AppCompatDialog>()
    //默认的loader
    private val DEFAULT_LOADER = BallClipRotateMultipleIndicator()

    private fun createDialog(
        context: Context?,
        avLoadingIndicatorView: AVLoadingIndicatorView
    ): AppCompatDialog {

        val dialog = AppCompatDialog(context, R.style.dialog)
        //获取屏幕的宽高
        val deviceWidth = ScreenUtils.getScreenWidth()
        val deviceHeight = ScreenUtils.getScreenHeight()
        val dialogWindow = dialog.window
        dialog.setContentView(avLoadingIndicatorView)

        if (dialogWindow != null) {
            val lp = dialogWindow.attributes
            lp.width = deviceWidth / LOADER_SIZE_SCALE
            lp.height = deviceHeight / LOADER_SIZE_SCALE
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE
            lp.gravity = Gravity.CENTER
        }

        LOADERS.add(dialog)
        return dialog
    }

    fun showLoading(context: Context?, type: Enum<LoaderStyles>) {
        showLoading(context, type.name)
    }

    fun showLoading(context: Context?, type: String) {
        val avLoadingIndicatorView = AVLoadingIndicatorView(context)
        avLoadingIndicatorView.setIndicator(type)
        createDialog(context, avLoadingIndicatorView).show()
    }

    @JvmOverloads
    fun showLoading(context: Context?, indicator: Indicator = DEFAULT_LOADER) {
        val avLoadingIndicatorView = AVLoadingIndicatorView(context)
        avLoadingIndicatorView.indicator = indicator
        createDialog(context, avLoadingIndicatorView).show()
    }

    fun stopLoading() {
        for (dialog in LOADERS) {
            if (dialog.isShowing) {
                dialog.cancel()
            }
        }
        LOADERS.clear()
    }
}