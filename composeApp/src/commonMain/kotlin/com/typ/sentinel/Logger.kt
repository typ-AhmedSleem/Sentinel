package com.typ.sentinel

expect class Logger(tag: String) {
    fun log(message: Any)
}