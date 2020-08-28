package com.lxy.demo

import android.app.Application
import com.joanzapata.iconify.fonts.FontAwesomeModule
import com.lxy.malllibrary.global.Mall


class MallExampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Mall.init(this)
            //假装网络有两秒钟的延迟，方便观察loading
            .withLoaderDelayed(0)
            .withIcon(FontAwesomeModule())
            //之后使用我远程部署的测试数据
            .withApiHost("http://mock.fulingjie.com/mock/api/")
            .configure()
    }
}