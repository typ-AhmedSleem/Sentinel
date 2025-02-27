package com.typ.sentinel

actual class Logger actual constructor(private val tag: String) {

    actual fun log(message: Any) {
        println("[$tag]: $message")
    }

}