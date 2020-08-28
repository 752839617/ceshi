package com.lxy.malllibrary.activities

import android.app.Activity
import android.os.Bundle

import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.lxy.malllibrary.R
import com.lxy.malllibrary.fragments.MallFragment

import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportActivity
import me.yokeyword.fragmentation.SupportActivityDelegate
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

abstract class ProxyActivity :
   AppCompatActivity(),ISupportActivity {
    private lateinit var mDelegate: SupportActivityDelegate
    abstract fun setRootFragment(): MallFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDelegate = SupportActivityDelegate(this)
        mDelegate.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
    }

    private fun initContainer(savedInstanceState: Bundle?) {
        val container = FrameLayout(this)
        container.id = R.id.fragment_container
        setContentView(container)
        if (savedInstanceState == null) {
            mDelegate.loadRootFragment(
                R.id.fragment_container,
                setRootFragment()
            )
        }
    }

    override fun onDestroy() {
        mDelegate.onDestroy()
        super.onDestroy()
        System.gc()
        System.runFinalization()
    }

    override fun getSupportDelegate(): SupportActivityDelegate {
        return mDelegate
    }

    override fun post(runnable: Runnable?) {
        mDelegate.post(runnable)
    }

    override fun extraTransaction(): ExtraTransaction {
        return mDelegate.extraTransaction()
    }

    override fun getFragmentAnimator(): FragmentAnimator {
        return mDelegate.fragmentAnimator
    }

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator) {
        mDelegate.fragmentAnimator = DefaultHorizontalAnimator()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return mDelegate.onCreateFragmentAnimator()
    }

    override fun onBackPressedSupport() {
        mDelegate.onBackPressedSupport()
    }

    override fun onBackPressed() {
        mDelegate.onBackPressed()
    }
}











