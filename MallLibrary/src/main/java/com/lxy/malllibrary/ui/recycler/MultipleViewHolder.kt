package com.lxy.malllibrary.ui.recycler

import android.view.View
import com.chad.library.adapter.base.BaseViewHolder

class MultipleViewHolder constructor(view: View) : BaseViewHolder(view) {

    //简单工厂模式的创建
    companion object {
        fun create(view: View): MultipleViewHolder {
            return MultipleViewHolder(view)
        }
    }
}