package com.typ.sentinel.systems.nis

data class InterceptedNotification(
    val title: String,
    val content: String,
    val packageName: String? = null
)
