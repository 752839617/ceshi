package com.lxy.demo.fragments.sort.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lxy.demo.R
import com.lxy.demo.fragments.sort.SortFragment
import com.lxy.malllibrary.fragments.MallFragment
import com.lxy.malllibrary.net.RestClient
import com.lxy.malllibrary.net.callback.ISuccess
import com.lxy.malllibrary.ui.loader.LoaderStyles
import kotlinx.android.synthetic.main.fragment_vertical_list.*


class VerticalListFragment : MallFragment() {

//    private lateinit var mRecyclerView: RecyclerView

    override fun setLayout(): Any {
        return R.layout.fragment_vertical_list
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
//        mRecyclerView = findView(R.id.rv_vertical_menu_list)

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        val manager = LinearLayoutManager(context)
        mRecyclerViewListMenu.layoutManager = manager
        //屏蔽动画
        mRecyclerViewListMenu.itemAnimator = null

        initData()

    }

    private fun initData() {
        RestClient.builder()
            .url("sort_list.php")
            .loader(context!!,LoaderStyles.LineScalePartyIndicator)
            .success(object : ISuccess {

                override fun onSuccess(response: String) {
                    val data = VerticalListDataConverter()
                        .setJsonData(response)
                        .convert()
                    val adapter =
                        SortRecyclerAdapter(
                            data, parentFragment as SortFragment
                        )
                    mRecyclerViewListMenu.adapter = adapter
                }

            })
            .build()
            .get()
    }


}











