package id.go.blitarkab.bpbd.data.remote.model.request

data class MessagingRequest(
    val to: String,
    val notification: Map<String, String>,
    val priority: Int,
    val android: Map<String, String>? = null,
    val data: Map<String, String>? = null
)
