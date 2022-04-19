package id.go.blitarkab.bpbd.domain.model

import id.go.blitarkab.bpbd.domain.model.common.Constants
import id.go.blitarkab.bpbd.domain.model.common.Model

data class Report(
    val id: Long,
    val user: Account,
    val operator: Account,
    val status: Status,
    val title: String,
    val content: String,
    val datetime: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val thumbnailUrl: String,
    val imageUrls: List<String>
) : Model {

    companion object {
        val DEFAULT: Report = Report(
            id = 0L,
            user = Account.DEFAULT,
            operator = Account.DEFAULT,
            status = Status.WAITING,
            title = "",
            content = "",
            datetime = Constants.DEFAULT_DATE_TIME,
            address = "",
            latitude = 0.0,
            longitude = 0.0,
            thumbnailUrl = "",
            imageUrls = emptyList(),
        )
    }

    override fun isItemSameWith(value: Model): Boolean {
        return value is Report && value.id == this.id
    }

    override fun isContentSameWith(value: Model): Boolean {
        return value is Report && value == this
    }

    enum class Status {
        WAITING, ACCEPTED, REJECTED
    }

}