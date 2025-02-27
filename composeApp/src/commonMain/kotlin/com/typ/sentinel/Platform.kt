package com.typ.sentinel

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform