package com.typ.sentinel.support

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.startup.Initializer

var appContext: Context? = null

class StartupInitializer : Initializer<Context> {
    override fun create(context: Context): Context = context.applicationContext.also { appContext = it }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}