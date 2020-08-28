package com.lxy.demo

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View
import android.widget.Toast

import com.lxy.malllibrary.fragments.MallFragment
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : MallFragment() {
    override fun setLayout(): Any {
        return R.layout.fragment_test
    }



    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        Toast.makeText(context,"chusd",Toast.LENGTH_LONG).show()

    }

}