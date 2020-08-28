package com.lxy.demo.fragments.index

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lxy.demo.R
import com.lxy.demo.fragments.MallMainFragment
import com.lxy.malllibrary.fragments.bottom.BottomItemFragment
import com.lxy.malllibrary.net.RestClient
import com.lxy.malllibrary.net.callback.ISuccess
import com.lxy.malllibrary.ui.recycler.BaseDecoration
import com.lxy.malllibrary.ui.recycler.MultipleRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_index.*


class IndexFragment : BottomItemFragment() {

//    private lateinit var mRecyclerView: RecyclerView

    override fun setLayout(): Any {
        return R.layout.fragment_index
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
//        mRecyclerView = findView(R.id.rv_index)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //让Toolbar初始时是透明的
//        val toolbar = view.findViewById<Toolbar>(R.id.tb_index)
        mIndexToolbar.background.alpha = 0
    }

    //就是惰性的加载数据和UI
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initRecyclerView()
        initData()
    }


    private fun initRecyclerView() {
        val manager = GridLayoutManager(context, 4)
        mIndexRecyclerView.layoutManager = manager
        mIndexRecyclerView.addItemDecoration(
            BaseDecoration.create(Color.LTGRAY, 5)
        )
    }

    private fun initData() {
        RestClient.builder()
            .url("index.php")
            .loader(context)
            .success(object : ISuccess {
                override fun onSuccess(response: String) {
                    val adapter = MultipleRecyclerAdapter
                        .create(IndexDataConverter().setJsonData(response))
                    mIndexRecyclerView.adapter = adapter
                    val mallBottom : MallMainFragment = parentFragment as MallMainFragment
                    mIndexRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(mallBottom))
                }

            })
            .build()
            .get()
    }
}











