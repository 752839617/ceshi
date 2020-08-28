package com.lxy.demo.fragments.detail


import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.lxy.demo.R
import com.lxy.malllibrary.ui.recycler.MultipleFields
import com.lxy.malllibrary.ui.recycler.MultipleItemEntity
import com.lxy.malllibrary.ui.recycler.MultipleRecyclerAdapter
import com.lxy.malllibrary.ui.recycler.MultipleViewHolder

class DetailImageAdapter
constructor(data: List<MultipleItemEntity>) :
    MultipleRecyclerAdapter(data) {

    companion object {
        private val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
    }

    init {
        addItemType(R.id.id_goods_detail_image, R.layout.item_image)
    }

    override fun convert(holder: MultipleViewHolder, entity: MultipleItemEntity) {
        super.convert(holder, entity)
        val type = holder.itemViewType
        when (type) {
            R.id.id_goods_detail_image -> {
                val imageView = holder.getView<AppCompatImageView>(R.id.image_rv_item)
                val url = entity.getField<String>(MultipleFields.IMAGE_URL)
                Glide.with(mContext)
                    .load(url)
                    .apply(options)
                    .into(imageView)
            }
        }
    }

}