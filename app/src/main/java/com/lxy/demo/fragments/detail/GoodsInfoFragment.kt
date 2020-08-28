package com.lxy.demo.fragments.detail

import android.os.Bundle

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.lxy.demo.R
import com.lxy.malllibrary.fragments.MallFragment


class GoodsInfoFragment : MallFragment() {

    private lateinit var mData: JSONObject

    companion object {
        private const val ARG_GOODS_DATA = "ARG_GOODS_DATA"

        fun create(goodsInfo: String): GoodsInfoFragment {
            val args = Bundle()
            args.putString(ARG_GOODS_DATA, goodsInfo)
            val fragment = GoodsInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val goodsData = arguments?.getString(ARG_GOODS_DATA)
        mData = JSON.parseObject(goodsData)
    }

    override fun setLayout(): Any {
        return R.layout.fragment_goods_info
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        val goodsInfoTitle = findView<AppCompatTextView>(R.id.tv_goods_info_title)
        val goodsInfoDesc = findView<AppCompatTextView>(R.id.tv_goods_info_desc)
        val goodsInfoPrice = findView<AppCompatTextView>(R.id.tv_goods_info_price)
        val name = mData.getString("name")
        val desc = mData.getString("description")
        val price = mData.getDouble("price")
        goodsInfoTitle.text = name
        goodsInfoPrice.text = price.toString()
        goodsInfoDesc.text = desc
    }
}