package com.lxy.demo.fragments.detail

import android.os.Bundle

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lxy.demo.R
import com.lxy.malllibrary.fragments.MallFragment
import com.lxy.malllibrary.ui.recycler.MultipleFields
import com.lxy.malllibrary.ui.recycler.MultipleItemEntity


class ImageFragment : MallFragment() {

    private lateinit var mRecyclerView: RecyclerView

    companion object {
        private const val ARG_PICTURES = "ARG_PICTURES"

        fun create(pictures: ArrayList<String>): ImageFragment {
            val args = Bundle()
            args.putStringArrayList(ARG_PICTURES, pictures)
            val fragment = ImageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private fun initImages() {
        val arguments = arguments
        if (arguments != null) {
            val pictures = arguments.getStringArrayList(ARG_PICTURES)
            val entities = ArrayList<MultipleItemEntity>()
            val size: Int
            if (pictures != null) {
                size = pictures.size
                for (i in 0 until size) {
                    val imageUrl = pictures[i]
                    val entity = MultipleItemEntity
                        .builder()
                        .setItemType(R.id.id_goods_detail_image)
                        .setField(MultipleFields.IMAGE_URL, imageUrl)
                        .build()
                    entities.add(entity)
                    val adapter = DetailImageAdapter(entities)
                    mRecyclerView.adapter = adapter
                }
            }

        }
    }

    override fun setLayout(): Any {
        return R.layout.fragment_image
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mRecyclerView = findView(R.id.rv_image_container)
        val manager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = manager
        initImages()
    }
}