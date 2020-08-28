package com.lxy.demo.fragments

import android.graphics.Color
import com.lxy.demo.FirstFragment
import com.lxy.demo.fragments.cart.ShopCartFragment

import com.lxy.demo.fragments.index.IndexFragment

import com.lxy.demo.fragments.personal.PersonalFragment
import com.lxy.demo.fragments.sort.SortFragment
import com.lxy.malllibrary.fragments.bottom.BaseBottomFragment
import com.lxy.malllibrary.fragments.bottom.BottomItemFragment
import com.lxy.malllibrary.fragments.bottom.BottomTabBean
import com.lxy.malllibrary.fragments.bottom.ItemBuilder


import java.util.*

class MallMainFragment : BaseBottomFragment() {

    override fun setItems(builder: ItemBuilder): LinkedHashMap<BottomTabBean, BottomItemFragment> {
        val items = LinkedHashMap<BottomTabBean, BottomItemFragment>()
        items[BottomTabBean("{fa-home}", "主页")] = IndexFragment()
        items[BottomTabBean("{fa-sort}", "分类")] = SortFragment()
        items[BottomTabBean("{fa-shopping-cart}", "购物车")] = ShopCartFragment()
        items[BottomTabBean("{fa-user}", "我的")] = PersonalFragment()
        return builder.addItems(items).build()
    }

    override fun setIndexFragment(): Int {
        return 0
    }

    override fun setClickedColor(): Int {
        return Color.RED
    }
}