package id.go.blitarkab.bpbd.presentation.util.base

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.presentation.util.Event

interface BasePresenter {

    val stateLoading: LiveData<Boolean>
    val eventMessage: LiveData<Event<String>>
    val eventImportantMessage: LiveData<Event<String>>
    fun isLoading(): Boolean
    fun setLoading(isLoading: Boolean = true)
    fun postMessageEvent(message: String)
    fun postImportantMessageEvent(message: String)
    suspend fun getCurrentAccount(): Account?

}