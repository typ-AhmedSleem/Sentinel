package com.typ.sentinel

import android.util.Log

actual class Logger actual constructor(private val tag: String) {

    actual fun log(message: Any) {
        Log.i(tag, message.toString())
    }

}