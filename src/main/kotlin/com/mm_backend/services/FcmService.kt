package com.mm_backend.services

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Service

@Service
class FcmService(private val firebaseMessaging: FirebaseMessaging) {
    @Throws(FirebaseMessagingException::class)
    fun sendNotification(token: String?): String {
        val notification: Notification = Notification
            .builder()
            .setTitle("Chuẩn bị hết tiền anh zai ơi")
            .setBody("Nạp lần đầu đi anh")
            .build()
        val message: Message = Message
            .builder()
            .setToken(token)
            .setNotification(notification)
            .putAllData(mapOf("title" to "Chuẩn bị hết tiền anh zai ơi", "content" to "Nạp lần đầu đi anh"))
            .build()
        return firebaseMessaging.send(message)
    }
}