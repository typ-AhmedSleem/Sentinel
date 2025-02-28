package com.typ.sentinel.systems.nis

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.typ.sentinel.support.appContext
import com.typ.sentinel.systems.ucs.UserControlSystem

actual class NotificationInterceptorService : NotificationListenerService() {

    override fun onCreate() {
        super.onCreate()
        UserControlSystem.updateSettings(
            UserControlSystem.getSettings().copy(
                isSentinelShieldEnabled = true
            )
        )
    }

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
            // Start the service
            val context = appContext ?: return
            if (!isServiceRunning(context)) {
                val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                context.startActivity(intent)
            }
        }

        fun isServiceRunning(context: Context): Boolean {
            val componentName = ComponentName(context, NotificationInterceptorService::class.java)
            val enabledListeners = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
            return enabledListeners?.contains(componentName.flattenToString()) == true
        }

        actual fun stopService() {
            // todo: stop the service
        }
    }

}