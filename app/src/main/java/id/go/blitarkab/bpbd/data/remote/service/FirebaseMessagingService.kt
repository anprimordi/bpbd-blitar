package id.go.blitarkab.bpbd.data.remote.service

import id.go.blitarkab.bpbd.data.remote.model.request.MessagingRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FirebaseMessagingService {

    @Headers(
        value = [
            "Content-Type: application/json",
            "Authorization: key=AAAAJH9BeGo:APA91bFeqVcDQcreCP0n6UV734_McNYC_UOmMJ7cqVl5i691tRjAVWwIzAeHvwqUUpP1T9yLN4MamTw2XfiDjXwUSQy-HMgFDb__vR1UXVORic-Y1aq98YaUgBGkDxreu3sSA4_Y35D4"
        ]
    )
    @POST(value = "send")
    suspend fun sendMessage(@Body body: MessagingRequest)

}