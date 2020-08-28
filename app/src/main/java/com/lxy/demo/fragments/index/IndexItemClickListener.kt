package com.lxy.demo.fragments.index

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.SimpleClickListener
import com.lxy.demo.fragments.MallMainFragment
import com.lxy.demo.fragments.detail.GoodsDetailFragment
import com.lxy.malllibrary.ui.recycler.MultipleFields
import com.lxy.malllibrary.ui.recycler.MultipleItemEntity


class IndexItemClickListener private constructor
    (private val fragment: MallMainFragment) : SimpleClickListener() {

    companion object {
        fun create(fragment: MallMainFragment): SimpleClickListener {
            return IndexItemClickListener(fragment)
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
    }

    override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
    }

    override fun onItemChildLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val entity = baseQuickAdapter.data[position] as MultipleItemEntity
        val goodsId = entity.getField<Int>(MultipleFields.ID)
        val goodsDetailFragment = GoodsDetailFragment.create(goodsId)
        fragment.start(goodsDetailFragment)
    }
}