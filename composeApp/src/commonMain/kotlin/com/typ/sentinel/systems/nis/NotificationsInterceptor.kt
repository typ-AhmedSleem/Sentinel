package com.typ.sentinel.systems.nis

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object NotificationsInterceptor {

    private val _notifications = MutableSharedFlow<InterceptedNotification>(
        extraBufferCapacity = 10
    )
    val notifications = _notifications.asSharedFlow()

    fun intercept(notification: InterceptedNotification) {
        _notifications.tryEmit(notification)
    }
}