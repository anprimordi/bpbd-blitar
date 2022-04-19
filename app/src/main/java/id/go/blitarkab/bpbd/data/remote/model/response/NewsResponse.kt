package id.go.blitarkab.bpbd.data.remote.model.response

import com.google.gson.annotations.SerializedName
import id.go.blitarkab.bpbd.data.remote.util.RemoteDateTimeUtils
import id.go.blitarkab.bpbd.domain.model.News

data class NewsResponse(
    @SerializedName(value = "id") val id: Long? = null,
    @SerializedName(value = "title") val title: String? = null,
    @SerializedName(value = "content") val content: String? = null,
    @SerializedName(value = "thumbnail") val imageUrl: String? = null,
    @SerializedName(value = "date") val datetime: String? = null,
) {

    data class Wrapper(
        @SerializedName(value = "posts") val data: List<NewsResponse>? = null
    )

    fun toDomain(): News {
        return News(
            id = id ?: News.DEFAULT.id,
            title = title ?: News.DEFAULT.title,
            content = content ?: News.DEFAULT.content,
            imageUrl = imageUrl,
            datetime = if (datetime != null) RemoteDateTimeUtils.parseNewsDatetime(datetime).time else News.DEFAULT.datetime,
        )
    }

}