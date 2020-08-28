package com.lxy.demo.fragments.personal

import android.os.Bundle
import android.view.View
import com.lxy.demo.R
import com.lxy.malllibrary.fragments.bottom.BottomItemFragment


class PersonalFragment : BottomItemFragment() {

    override fun setLayout(): Any {
        return R.layout.fragment_personal
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
    }
}