package com.typ.sentinel.systems.nis

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.typ.sentinel.support.appContext

actual class NotificationInterceptorService : NotificationListenerService() {

    override fun onNotificationPosted(sbnNullable: StatusBarNotification?) {
        sbnNullable?.let { sbn ->
            val packageName = sbn.packageName
            val extras = sbn.notification.extras
            val title = extras.getString("android.title") ?: ""
            val content = extras.getString("android.text") ?: return@let

            InterceptedNotification(title, content, packageName).also {
                // * Emit the notification into the flow
                NotificationsInterceptor.emitInterception(it)
            }
        }
    }


    actual companion object {
        actual fun startService() {
            // todo: Get context
            val context = appContext ?: return
            // todo: start the service
            val componentName = ComponentName(context, NotificationInterceptorService::class.java)
            val enabledListeners = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
            if (enabledListeners?.contains(componentName.flattenToString()) == false) {
                val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                context.startActivity(intent)
            }
        }

        actual fun stopService() {
            // todo: stop the service
        }
    }

}