package com.lxy.demo.fragments.cart

import android.graphics.Color
import android.os.Bundle

import android.view.View
import android.view.ViewStub
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joanzapata.iconify.widget.IconTextView
import com.lxy.demo.R
import com.lxy.malllibrary.fragments.bottom.BottomItemFragment
import com.lxy.malllibrary.net.RestClient
import com.lxy.malllibrary.net.callback.ISuccess
import com.lxy.malllibrary.ui.recycler.MultipleItemEntity
import kotlinx.android.synthetic.main.fragment_shop_cart.*
import kotlinx.android.synthetic.main.stub_shop_cart_no_item.*
import kotlinx.android.synthetic.main.stub_shop_cart_no_item.view.*
import kotlinx.android.synthetic.main.stub_shop_cart_no_item.view.mTvToBuy


class ShopCartFragment :
    BottomItemFragment(),
    ICartItemPriceListener,
    View.OnClickListener {

    private lateinit var mAdapter: ShopCartAdapter
//    private lateinit var mRecyclerView: RecyclerView
//    private lateinit var mTvTotalPrice: AppCompatTextView
//    private lateinit var mIconSelectAll: IconTextView
//    private lateinit var mStubNoItem: ViewStub
    private var mTotalPrice: Double = 0.00

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        mIconSelectAll.setOnClickListener(this)
        mTextClear.setOnClickListener(this)
        mTextRemove.setOnClickListener(this)
        //表示是否已经全选了
        mIconSelectAll.tag = false

        initData()
    }

    private fun initData() {
        RestClient
            .builder()
            .url("shop_cart.php")
            .loader(context)
            .success(object : ISuccess {
                override fun onSuccess(response: String) {
                    val data = ShopCartDataConverter()
                        .setJsonData(response)
                        .convert()
                    mAdapter = ShopCartAdapter(data)
                    mAdapter.setCartItemPriceListener(this@ShopCartFragment)
                    val manager = LinearLayoutManager(context)
                    mRecyclerViewCart.layoutManager = manager
                    mRecyclerViewCart.adapter = mAdapter
                    mTotalPrice = mAdapter.totPrice
                    mTvTotalPrice.text = mTotalPrice.toString()
                }
            })
            .build()
            .get()
    }

    override fun setLayout(): Any {
        return R.layout.fragment_shop_cart
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
//        mRecyclerView = findView(R.id.rv_shop_cart)
//        mTvTotalPrice = findView(R.id.tv_total_price)
//        mIconSelectAll = findView(R.id.icon_shop_cart_select_all)

//        findView<AppCompatTextView>(R.id.tv_top_shop_cart_clear).setOnClickListener(this)
//        findView<AppCompatTextView>(R.id.tv_shop_cart_remove_selected).setOnClickListener(this)
//        mStubNoItem = findView(R.id.stub_no_item)
    }

    override fun onItemPriceChanged(itemTotalPrice: Double) {
        val price = mAdapter.totPrice
        mTvTotalPrice.text = price.toString()
        //我们可以在外部随时获取到每个条目的总价变化
    }

    private fun onClickSelectAll() {
        val tag = mIconSelectAll.tag as Boolean
        if (!tag) {
            val context = context
            if (context !== null) {
                mIconSelectAll.setTextColor(ContextCompat.getColor(context, R.color.app_main))
                mAdapter.setIsSelectAll(true)
                mAdapter.notifyItemRangeChanged(0, mAdapter.itemCount)
                //如果改变的范围是全部，上下两个方法等效
//                mAdapter.notifyDataSetChanged()
                mIconSelectAll.tag = true
            }
        } else {
            mIconSelectAll.setTextColor(Color.GRAY)
            mAdapter.setIsSelectAll(false)
            mAdapter.notifyItemRangeChanged(0, mAdapter.itemCount)
            //如果改变的范围是全部，上下两个方法等效
//                mAdapter.notifyDataSetChanged()
            mIconSelectAll.tag = false
        }
    }

    private fun onClickClear() {
        mAdapter.data.clear()
        mAdapter.notifyDataSetChanged()
        //适合局部更新
//        mAdapter.notifyItemRangeChanged(0, mAdapter.itemCount)
        //购物车已经空了
        checkItemCount()
    }

    private fun onClickRemoveSelectedItem() {
        val entities = mAdapter.data
        //要删除的数据集合
        val deleteEntities = ArrayList<MultipleItemEntity>()
        entities.forEach {
            val entity = it
            val isSelected = entity.getField<Boolean>(ShopCartItemFields.IS_SELECTED)
            if (isSelected) {
                deleteEntities.add(entity)
            }
        }
        deleteEntities.forEach {
            val entity = it
            mAdapter.data.remove(entity)
            mAdapter.notifyDataSetChanged()
        }
        checkItemCount()
    }

    private fun checkItemCount() {
        val count = mAdapter.itemCount
        if (count == 0) {
//            val stubView = mStubNoItem.inflate()

            mTvToBuy.setOnClickListener {
                //添加自己的点击事件
                Toast.makeText(context, "去购物", Toast.LENGTH_SHORT).show()
            }
            mRecyclerViewCart.visibility = View.GONE
        } else {
            mRecyclerViewCart.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.mIconSelectAll -> onClickSelectAll()
            R.id.mTextClear -> onClickClear()
            R.id.mTextRemove -> onClickRemoveSelectedItem()
        }

    }
}