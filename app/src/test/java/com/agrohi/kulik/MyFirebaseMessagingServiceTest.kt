package com.agrohi.kulik

import android.app.NotificationManager
import android.content.Context
import com.google.firebase.messaging.RemoteMessage
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], manifest = Config.NONE)
class MyFirebaseMessagingServiceTest {

    private lateinit var service: MyFirebaseMessagingService
    private lateinit var context: Context

    @Before
    fun setup() {
        context = RuntimeEnvironment.getApplication()
        service = spyk(MyFirebaseMessagingService())
        every { service.applicationContext } returns context
        every { service.getString(R.string.default_notification_channel_id) } returns "test_channel_id"
        every { service.getString(R.string.fcm_message) } returns "Test Message"
    }

    @Test
    fun onMessageReceived_withDataPayload_logsMessage() {
        val remoteMessage = mockk<RemoteMessage>(relaxed = true)
        val data = mapOf("key" to "value")
        every { remoteMessage.data } returns data
        every { remoteMessage.from } returns "test_sender"
        every { remoteMessage.notification } returns null

        service.onMessageReceived(remoteMessage)

        verify { remoteMessage.data }
    }

    @Test
    fun onMessageReceived_withNotificationButNoBody_handlesGracefully() {
        val notification = mockk<RemoteMessage.Notification>(relaxed = true)
        every { notification.body } returns null

        val remoteMessage = mockk<RemoteMessage>(relaxed = true)
        every { remoteMessage.data } returns emptyMap()
        every { remoteMessage.from } returns "test_sender"
        every { remoteMessage.notification } returns notification

        service.onMessageReceived(remoteMessage)

        verify { notification.body }
    }

    @Test
    fun onMessageReceived_withEmptyData_handlesGracefully() {
        val remoteMessage = mockk<RemoteMessage>(relaxed = true)
        every { remoteMessage.data } returns emptyMap()
        every { remoteMessage.from } returns "test_sender"
        every { remoteMessage.notification } returns null

        service.onMessageReceived(remoteMessage)

        verify { remoteMessage.data }
    }

    @Test
    fun onNewToken_receivesToken_logsToken() {
        val token = "test_token_123"
        service.onNewToken(token)
    }
}
