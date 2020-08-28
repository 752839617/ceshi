package com.lxy.demo.fragments.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.joanzapata.iconify.widget.IconTextView
import com.lxy.demo.R
import com.lxy.demo.fragments.detail.animation.BezierAnimation
import com.lxy.demo.fragments.detail.animation.BezierUtil
import com.lxy.demo.fragments.detail.animation.ScaleUpAnimator
import com.lxy.malllibrary.fragments.MallFragment
import com.lxy.malllibrary.net.RestClient
import com.lxy.malllibrary.net.callback.ISuccess
import com.lxy.malllibrary.ui.banner.BannerCreator

import com.mall.library.ui.widget.CircleTextView
import com.youth.banner.Banner
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList

class GoodsDetailFragment : MallFragment(),
    AppBarLayout.OnOffsetChangedListener,
    View.OnClickListener, BezierUtil.AnimationListener {

    private var mGoodsId = -1
    private lateinit var mBanner: Banner
    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager
    private lateinit var mBtnAddShopCart: AppCompatButton
    private lateinit var mIconShopCart: IconTextView
    private lateinit var mIconBack: IconTextView
    private lateinit var mGoodsThumbUrl: String
    private lateinit var mCircleTextView: CircleTextView
    private var mGoodsCount: Int = 0

    companion object {
        private const val ARG_GOODS_ID = "ARG_GOODS_ID"
        private val options = RequestOptions
            .diskCacheStrategyOf(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
            .override(100, 100)

        fun create(goodsId: Int): GoodsDetailFragment {
            val args = Bundle()
            args.putInt(ARG_GOODS_ID, goodsId)
            val fragment = GoodsDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            mGoodsId = args.getInt(ARG_GOODS_ID)
        }
    }

    private fun onClickAddShopCart() {
        val animImg = CircleImageView(context)
        Glide.with(this)
            .load(mGoodsThumbUrl)
            .apply(options)
            .into(animImg)
        BezierAnimation.addCart(
            this,
            mBtnAddShopCart,
            mIconShopCart,
            animImg,
            this
        )
    }

    override fun onAnimationEnd() {
        YoYo.with(ScaleUpAnimator())
            .duration(500)
            .playOn(mIconShopCart)
        //需要通知服务器，修改了购物车的数目
        RestClient
            .builder()
            .url("add_shop_cart_count.php")
            .loader(context)
            .success(object : ISuccess {
                override fun onSuccess(response: String) {
                    //根据不同的服务器逻辑和返回值去判断
                    //这里只是个参考
                    val isAdded = JSON.parseObject(response).getBoolean("data")
                    if (isAdded) {
                        mGoodsCount++
                        mCircleTextView.visibility = View.VISIBLE
                        mCircleTextView.text = mGoodsCount.toString()
                    }
                }
            })
            .params("count", mGoodsCount)
            .build()
            .post()

    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.btn_add_shop_cart -> {
                onClickAddShopCart()
            }
            R.id.icon_goods_back -> {
                fragmentManager?.popBackStack()

            }
        }
    }

    //服务器是可以返回当前购物数量的，具体看不同后台的逻辑和公司规范
    private fun setShopCartCount(data: JSONObject) {
        mGoodsThumbUrl = data.getString("thumb")
        //TODO:我们可以根据自己业务的服务器，解析data获取count
        if (mGoodsCount == 0) {
            mCircleTextView.visibility = View.GONE
        }
    }

    private fun initData() {
        RestClient
            .builder()
            .url("goods_detail.php")
            .params("goods_id",mGoodsId)
            .loader(context)
            .success(object : ISuccess {
                override fun onSuccess(response: String) {
                    if(response!=null){
                    val data = JSON.parseObject(response).getJSONObject("data")
                    initBanner(data)
                    initGoodsInfo(data)
                    initPager(data)
                    setShopCartCount(data)
                    }
                }
            })
            .build()
            .get()
    }

    private fun initTabLayout() {
        mTabLayout.tabMode = TabLayout.MODE_FIXED
        val context = context
        if (context != null) {
            mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.app_main))
        }
        mTabLayout.tabTextColors = ColorStateList.valueOf(Color.BLACK)
        mTabLayout.setBackgroundColor(Color.WHITE)
        //关联TabLayout和ViewPager
        mTabLayout.setupWithViewPager(mViewPager)
    }

    private fun initPager(data: JSONObject) {
        val adapter = TabPagerAdapter(fragmentManager, data)
        mViewPager.adapter = adapter
    }

    private fun initGoodsInfo(data: JSONObject) {
        val goodsData = data.toJSONString()
        //关键
        supportDelegate.loadRootFragment(
            R.id.frame_goods_info,
            GoodsInfoFragment.create(goodsData)
        )
    }

    private fun initBanner(data: JSONObject) {
        val array = data.getJSONArray("banners")
        val size = array.size
        val images = ArrayList<String>()
        for (i in 0 until size) {
            images.add(array.getString(i))
        }
        BannerCreator.setDefault(mBanner, images)
    }

    override fun setLayout(): Any {
        return R.layout.fragment_goods_detail
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mBanner = findView(R.id.detail_banner)
        mTabLayout = findView(R.id.tab_layout)
        mViewPager = findView(R.id.view_pager)
        val collapsingToolbarLayout = findView<CollapsingToolbarLayout>(R.id.collapsing_toolbar_detail)
        val appBarLayout = findView<AppBarLayout>(R.id.app_bar_detail)
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE)
        appBarLayout.addOnOffsetChangedListener(this)
        mBtnAddShopCart = findView(R.id.btn_add_shop_cart)
        mBtnAddShopCart.setOnClickListener(this)
        mCircleTextView = findView(R.id.tv_shopping_cart_amount)
        mCircleTextView.setCircleBackground(Color.RED)
        mIconShopCart = findView(R.id.icon_shop_cart)
        mIconShopCart.setOnClickListener(this)
        mIconBack = findView(R.id.icon_goods_back)
        mIconBack.setOnClickListener(this)
        initData()
        initTabLayout()
    }

    override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
    }
}