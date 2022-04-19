package id.go.blitarkab.bpbd.data.remote.model.response

import com.google.gson.annotations.SerializedName
import id.go.blitarkab.bpbd.data.remote.model.common.ReportStatusRemote
import id.go.blitarkab.bpbd.data.remote.util.RemoteDateTimeUtils
import id.go.blitarkab.bpbd.domain.model.News
import id.go.blitarkab.bpbd.domain.model.Report

data class ReportResponse(
    @SerializedName(value = "id") val id: Long? = null,
    @SerializedName(value = "user_id") val userId: Long? = null,
    @SerializedName(value = "officer_id") val operatorId: Long? = null,
    @SerializedName(value = "user") val user: AccountResponse? = null,
    @SerializedName(value = "officer") val operator: AccountResponse? = null,
    @SerializedName(value = "status") val status: ReportStatusRemote? = null,
    @SerializedName(value = "type") val title: String? = null,
    @SerializedName(value = "content") val content: String? = null,
    @SerializedName(value = "address") val address: String? = null,
    @SerializedName(value = "latitude") val latitude: String? = null,
    @SerializedName(value = "longitude") val longitude: String? = null,
    @SerializedName(value = "thumbnail") val thumbnailUrl: String? = null,
    @SerializedName(value = "images") val imageUrls: List<ImageResponse>? = null,
    @SerializedName(value = "datetime") val datetime: String? = null,
) {

    fun toDomain(): Report {
        return Report(
            id = id ?: Report.DEFAULT.id,
            user = user?.toDomain() ?: Report.DEFAULT.user.copy(
                id = userId ?: Report.DEFAULT.user.id
            ),
            operator = operator?.toDomain() ?: Report.DEFAULT.operator.copy(
                id = operatorId ?: Report.DEFAULT.user.id
            ),
            status = status?.toDomain() ?: Report.DEFAULT.status,
            title = title ?: Report.DEFAULT.title,
            content = content ?: Report.DEFAULT.content,
            datetime = if (datetime != null) RemoteDateTimeUtils.parseNewsDatetime(datetime).time else News.DEFAULT.datetime,
            address = address ?: Report.DEFAULT.address,
            latitude = latitude?.toDoubleOrNull() ?: Report.DEFAULT.latitude,
            longitude = longitude?.toDoubleOrNull() ?: Report.DEFAULT.longitude,
            thumbnailUrl = thumbnailUrl ?: Report.DEFAULT.thumbnailUrl,
            imageUrls = imageUrls?.map { it.imageUrl ?: "-" } ?: Report.DEFAULT.imageUrls
        )
    }

    data class ImageResponse(
        @SerializedName(value = "image") val imageUrl: String? = null
    )

}
