package com.lxy.demo.fragments.cart

import com.alibaba.fastjson.JSON
import com.lxy.demo.R
import com.lxy.malllibrary.ui.recycler.DataConverter
import com.lxy.malllibrary.ui.recycler.MultipleFields
import com.lxy.malllibrary.ui.recycler.MultipleItemEntity


class ShopCartDataConverter : DataConverter() {

    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataList = ArrayList<MultipleItemEntity>()
        val dataArray = JSON.parseObject(jsonData).getJSONArray("data")
        val size = dataArray.size
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)
            val thumb = data.getString("thumb")
            val desc = data.getString("desc")
            val title = data.getString("title")
            val id = data.getInteger("id")
            val count = data.getInteger("count")
            val price = data.getDouble("price")

            val entity = MultipleItemEntity
                .builder()
                .setField(MultipleFields.ITEM_TYPE, R.id.id_shop_cart)
                .setField(MultipleFields.ID, id)
                .setField(MultipleFields.IMAGE_URL, thumb)
                .setField(ShopCartItemFields.TITLE, title)
                .setField(ShopCartItemFields.DESC, desc)
                .setField(ShopCartItemFields.COUNT, count)
                .setField(ShopCartItemFields.PRICE, price)
                .setField(ShopCartItemFields.IS_SELECTED, false)
                .setField(ShopCartItemFields.POSITION, i)
                .build()

            dataList.add(entity)
        }
        return dataList
    }
}