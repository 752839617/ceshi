package com.lxy.malllibrary.ui.recycler

import com.choices.divider.Divider
import com.choices.divider.DividerItemDecoration

class DividerLookupImpl(private val color: Int, private val size: Int) :
    DividerItemDecoration.DividerLookup {

    override fun getHorizontalDivider(position: Int): Divider {
        return Divider.Builder()
            .size(size)
            .color(color)
            .build()
    }

    override fun getVerticalDivider(position: Int): Divider {
        return Divider.Builder()
            .size(size)
            .color(color)
            .build()
    }
}