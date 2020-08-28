package com.lxy.demo.fragments.sort

import android.os.Bundle
import android.view.View
import com.lxy.demo.R
import com.lxy.demo.fragments.sort.content.ContentFragment
import com.lxy.demo.fragments.sort.list.VerticalListFragment
import com.lxy.malllibrary.fragments.bottom.BottomItemFragment


class SortFragment : BottomItemFragment() {

    override fun setLayout(): Any {
        return R.layout.fragment_sort
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val listFragment = VerticalListFragment()
        //默认显示contentId是1的content
        val contentFragment = ContentFragment.newInstance(1)
        supportDelegate.loadRootFragment(
            R.id.vertical_list_container, listFragment
        )
        supportDelegate.loadRootFragment(
            R.id.sort_content_container, contentFragment
        )
    }
}