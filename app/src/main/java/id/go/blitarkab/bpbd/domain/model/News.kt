package id.go.blitarkab.bpbd.domain.model

import android.os.Parcelable
import id.go.blitarkab.bpbd.domain.model.common.Constants
import id.go.blitarkab.bpbd.domain.model.common.Model
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val id: Long,
    val title: String,
    val content: String,
    val imageUrl: String?,
    val datetime: Long
) : Parcelable, Model {

    companion object {
        val DEFAULT: News = News(
            id = 0,
            title = "",
            content = "",
            imageUrl = null,
            datetime = Constants.DEFAULT_DATE_TIME
        )
    }

    override fun isItemSameWith(value: Model): Boolean {
        return value is News && value.id == this.id
    }

    override fun isContentSameWith(value: Model): Boolean {
        return value is News && value == this
    }

}