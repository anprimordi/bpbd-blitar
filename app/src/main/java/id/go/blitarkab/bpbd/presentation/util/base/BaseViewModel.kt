package id.go.blitarkab.bpbd.presentation.util.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.util.Event
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    @Repository private val accountDataSource: AccountDataSource
) : ViewModel(), BasePresenter {

    private val _stateLoading = MediatorLiveData<Boolean>()
    override val stateLoading: LiveData<Boolean>
        get() = _stateLoading

    private val _eventMessage = MutableLiveData<Event<String>>()
    override val eventMessage: LiveData<Event<String>>
        get() = _eventMessage

    private val _eventImportantMessage = MutableLiveData<Event<String>>()
    override val eventImportantMessage: LiveData<Event<String>>
        get() = _eventImportantMessage

    init {
        _stateLoading.value = false
    }

    override fun isLoading(): Boolean = stateLoading.value == true

    override fun setLoading(isLoading: Boolean) {
        _stateLoading.postValue(isLoading)
    }

    override fun postMessageEvent(message: String) {
        _eventMessage.postValue(Event(content = message))
    }

    override fun postImportantMessageEvent(message: String) {
        _eventImportantMessage.postValue(Event(content = message))
    }

    override suspend fun getCurrentAccount(): Account? {
        val result = accountDataSource.getAccount()
        return if (result is Success) result.data else null
    }

}