package com.lxy.demo.fragments.sort.list

import com.alibaba.fastjson.JSON
import com.lxy.demo.R
import com.lxy.malllibrary.ui.recycler.DataConverter
import com.lxy.malllibrary.ui.recycler.ItemType
import com.lxy.malllibrary.ui.recycler.MultipleFields
import com.lxy.malllibrary.ui.recycler.MultipleItemEntity


class VerticalListDataConverter : DataConverter() {

    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataList = ArrayList<MultipleItemEntity>()
        var dataArray = JSON
            .parseObject(jsonData)
            .getJSONObject("data")
            .getJSONArray("list")

        val size = dataArray.size
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)
            val id = data.getInteger("id")
            val name = data.getString("name")

            val entity = MultipleItemEntity
                .builder()
                .setField(MultipleFields.ITEM_TYPE, R.id.vertical_list_menu)
                .setField(MultipleFields.ID, id)
                .setField(MultipleFields.TEXT, name)
                .setField(MultipleFields.TAG, false)
                .build()

            dataList.add(entity)

            //默认设置第一个类型被选中
            dataList[0].setField(MultipleFields.TAG, true)
        }
        return dataList
    }
}