package com.typ.sentinel.systems.nis

expect class NotificationInterceptorService {

    companion object {
        fun startService()
        fun stopService()
    }

}