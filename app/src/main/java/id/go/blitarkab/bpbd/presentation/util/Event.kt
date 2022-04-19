package id.go.blitarkab.bpbd.presentation.util

class Event<out T>(private val content: T) {

    data class Wrapper<T>(val data: T?)

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getAvailableEvent(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekEvent(): T = content

}