package id.go.blitarkab.bpbd.domain.model.common

interface Model {
    fun contains(someValue: String): Boolean = false
    fun isItemSameWith(value: Model): Boolean = false
    fun isContentSameWith(value: Model): Boolean = false
}