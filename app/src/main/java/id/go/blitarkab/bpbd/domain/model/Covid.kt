package id.go.blitarkab.bpbd.domain.model

import id.go.blitarkab.bpbd.domain.model.common.Model

data class Covid(
    val area: String,
    val totalCase: String,
    val totalPositive: String,
    val totalRecovered: String,
    val totalDied: String
) : Model {

    companion object {
        val DEFAULT: Covid = Covid(
            area = "",
            totalCase = "-",
            totalPositive = "-",
            totalRecovered = "-",
            totalDied = "-"
        )
    }

    override fun isItemSameWith(value: Model): Boolean {
        return value is Covid && value.area == this.area
    }

    override fun isContentSameWith(value: Model): Boolean {
        return value is Covid && value == this
    }
}