package com.lxy.malllibrary.global

import android.content.Context
import com.blankj.utilcode.util.Utils
import com.lxy.malllibrary.global.storage.MemoryStore



object Mall {

    val configurator: Configurator
        get() = Configurator.instance

    fun init(context: Context): Configurator {
        MemoryStore.instance
            .addData(
                GlobalKeys.APPLICATION_CONTEXT,
                context.applicationContext
            )

        Utils.init(context)
        return Configurator.instance
    }

    fun <T> getConfiguration(key: String): T {
        return configurator.getConfiguration(key)
    }

    fun <T> getConfiguration(key: Enum<GlobalKeys>): T {
        return getConfiguration(key.name)
    }
}