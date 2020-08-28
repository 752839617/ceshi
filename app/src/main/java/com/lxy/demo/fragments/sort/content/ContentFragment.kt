package com.lxy.demo.fragments.sort.content

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lxy.demo.R
import com.lxy.malllibrary.fragments.MallFragment
import com.lxy.malllibrary.net.RestClient
import com.lxy.malllibrary.net.callback.ISuccess
import kotlinx.android.synthetic.main.fragment_list_content.*


class ContentFragment : MallFragment() {

//    private lateinit var mRecyclerView: RecyclerView
    private var mContentId = -1

    //简单工厂
    companion object {

        private const val ARGS_CONTENT_ID = "CONTENT_ID"

        fun newInstance(contentId: Int): ContentFragment {

            val args = Bundle()
            args.putInt(ARGS_CONTENT_ID, contentId)
            val fragment = ContentFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            mContentId = args.getInt(ARGS_CONTENT_ID)
        }
    }

    override fun setLayout(): Any {
        return R.layout.fragment_list_content
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
//        mRecyclerView = findView(R.id.rv_list_content)

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        val manager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        mRecyclerViewListContent.layoutManager = manager
        //初始化数据
        initData()

    }

    private fun initData() {
        RestClient.builder()
            //标准的Restful API是不应该这样命名的
            //标准的Restful .../.../content   .../.../list .../.../detail
            //标准的Restful是面向资源的名词组合
            //扩展下，RPC是动词集合
            .url("sort_content_list.php?contentId=$mContentId")
            .success(object : ISuccess {

                override fun onSuccess(response: String) {
                    val data =
                        SectionDataConverter().convert(response)
                    val sectionAdapter =
                        SectionAdapter(
                            R.layout.item_section_content,
                            R.layout.item_section_header,
                            data
                        )
                    mRecyclerViewListContent.adapter = sectionAdapter
                }
            })
            .build()
            .get()
    }

}











