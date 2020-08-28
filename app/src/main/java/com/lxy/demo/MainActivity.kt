package com.lxy.demo

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentController
import com.lxy.demo.fragments.MallMainFragment
import com.lxy.malllibrary.activities.ProxyActivity
import com.lxy.malllibrary.fragments.MallFragment
import com.lxy.malllibrary.net.RestClient
import com.lxy.malllibrary.net.callback.IFailure
import com.lxy.malllibrary.net.callback.ISuccess
import com.lxy.malllibrary.ui.loader.LoaderStyles

class MainActivity : ProxyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
    override fun setRootFragment(): MallFragment {
        return MallMainFragment()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        RestClient.builder()
//            .url("index.php")
//            .loader(this@MainActivity,LoaderStyles.LineScalePulseOutRapidIndicator)
//            .success(object : ISuccess{
//                override fun onSuccess(response: String) {
//                    Toast.makeText(baseContext,response,Toast.LENGTH_LONG).show()
//                }
//            })
//
//            //.params("","")
//        .build()
//         .get()
//
//
//
//
//
//    }


}