package id.go.blitarkab.bpbd.domain.model

import id.go.blitarkab.bpbd.domain.model.common.Model

data class Volcano(
    val mountain: String,
    val status: String,
    val date: String
) : Model {

    companion object {
        val DEFAULT: Volcano = Volcano(
            mountain = "",
            status = "",
            date = ""
        )
    }

    override fun isItemSameWith(value: Model): Boolean {
        return value is Volcano && value.mountain == this.mountain
    }

    override fun isContentSameWith(value: Model): Boolean {
        return value is Volcano && value == this
    }

}