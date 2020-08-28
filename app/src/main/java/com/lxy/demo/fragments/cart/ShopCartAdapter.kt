package com.lxy.demo.fragments.cart

import android.graphics.Color
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.joanzapata.iconify.widget.IconTextView
import com.lxy.demo.R
import com.lxy.malllibrary.net.RestClient
import com.lxy.malllibrary.net.callback.ISuccess
import com.lxy.malllibrary.ui.recycler.MultipleFields
import com.lxy.malllibrary.ui.recycler.MultipleItemEntity
import com.lxy.malllibrary.ui.recycler.MultipleRecyclerAdapter
import com.lxy.malllibrary.ui.recycler.MultipleViewHolder

class ShopCartAdapter internal constructor(data: List<MultipleItemEntity>) : MultipleRecyclerAdapter(data) {

    private var mIsSelectAll = false
    var totPrice = 0.00
        private set

    private lateinit var mICartItemPriceListener: ICartItemPriceListener

    fun setCartItemPriceListener(listener: ICartItemPriceListener) {
        this.mICartItemPriceListener = listener
    }

    fun setIsSelectAll(isSelectAll: Boolean) {
        this.mIsSelectAll = isSelectAll
    }

    init {
        addItemType(R.id.id_shop_cart, R.layout.item_shop_cart)
        data.forEach {
            val entity: MultipleItemEntity = it
            val price = entity.getField<Double>(ShopCartItemFields.PRICE)
            val count = entity.getField<Int>(ShopCartItemFields.COUNT)
            val total = price * count
            totPrice += total
        }
    }

    companion object {
        private val OPTIONS = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
    }

    override fun convert(holder: MultipleViewHolder, entity: MultipleItemEntity) {
        super.convert(holder, entity)
        when (holder.itemViewType) {
            R.id.id_shop_cart -> {
                val thumb = entity.getField<String>(MultipleFields.IMAGE_URL)
                val title = entity.getField<String>(ShopCartItemFields.TITLE)
                val desc = entity.getField<String>(ShopCartItemFields.DESC)
                val count = entity.getField<Int>(ShopCartItemFields.COUNT)
                val price = entity.getField<Double>(ShopCartItemFields.PRICE)

                //取出所以控件
                val imgThumb = holder.getView<AppCompatImageView>(R.id.image_item_shop_cart)
                val tvTitle = holder.getView<AppCompatTextView>(R.id.tv_item_shop_cart_title)
                val tvDesc = holder.getView<AppCompatTextView>(R.id.tv_item_shop_cart_desc)
                val tvPrice = holder.getView<AppCompatTextView>(R.id.tv_item_shop_cart_price)
                val iconMinus = holder.getView<IconTextView>(R.id.icon_item_minus)
                val iconPlus = holder.getView<IconTextView>(R.id.icon_item_plus)
                val tvCount = holder.getView<AppCompatTextView>(R.id.tv_shop_cart_count)
                val iconIsSelected = holder.getView<IconTextView>(R.id.icon_item_shop_cart)

                tvTitle.text = title
                tvDesc.text = desc
                tvPrice.text = price.toString()
                tvCount.text = count.toString()

                Glide.with(mContext)
                    .load(thumb)
                    .apply(OPTIONS)
                    .into(imgThumb)

                entity.setField(ShopCartItemFields.IS_SELECTED, mIsSelectAll)

                val isSelected = entity.getField<Boolean>(ShopCartItemFields.IS_SELECTED)
                if (isSelected) {
                    iconIsSelected.setTextColor(ContextCompat.getColor(mContext, R.color.app_main))
                } else {
                    iconIsSelected.setTextColor(Color.GRAY)
                }
                //设置左侧勾选的点击事件
                iconIsSelected.setOnClickListener {
                    val currentSelected = entity.getField<Boolean>(ShopCartItemFields.IS_SELECTED)
                    if (currentSelected) {
                        iconIsSelected.setTextColor(Color.GRAY)
                        entity.setField(ShopCartItemFields.IS_SELECTED, false)
                    } else {
                        iconIsSelected.setTextColor(ContextCompat.getColor(mContext, R.color.app_main))
                        entity.setField(ShopCartItemFields.IS_SELECTED, true)
                    }
                }

                iconMinus.setOnClickListener {
                    //添加加减事件
                    if (Integer.parseInt(tvCount.text.toString()) > 1) {
                        val currentCount = entity.getField<Int>(ShopCartItemFields.COUNT)
                        RestClient.builder()
                            .url("shop_cart_count.php")
//                            .loader(mContext)
                            .params("count", currentCount)
                            .success(object : ISuccess {
                                override fun onSuccess(response: String) {
                                    var countNum = Integer.parseInt(tvCount.text.toString())
                                    countNum--
                                    tvCount.text = countNum.toString()
                                    totPrice -= price
                                    val itemTotal = countNum * price
                                    mICartItemPriceListener.onItemPriceChanged(itemTotal)
                                }
                            })
                            .build()
                            .post()
                    }
                }

                iconPlus.setOnClickListener {
                    val currentCount = entity.getField<Int>(ShopCartItemFields.COUNT)
                    //加减货物之后，会立刻调用一次API
                    RestClient
                        .builder()
                        .url("shop_cart_count.php")
//                        .loader(mContext)
                        //真实项目中可能会用到类似的参数
                        //.params("id", "")
                        .params("count", currentCount)
                        .success(object : ISuccess {
                            override fun onSuccess(response: String) {
                                var countNum = Integer.parseInt(tvCount.text.toString())
                                countNum++
                                tvCount.text = countNum.toString()
                                totPrice += price
                                val itemTotal = countNum * price
                                mICartItemPriceListener.onItemPriceChanged(itemTotal)
                            }
                        })
                        .build()
                        .post()
                }
            }
        }
    }
}