package id.go.blitarkab.bpbd.presentation.ui.main.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.model.common.InvalidDataError
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    @Repository private val accountDataSource: AccountDataSource
) : BaseViewModel(accountDataSource), ForgotPasswordContract.Presenter {

    override val emailObservable = MutableLiveData<String>()

    private val _eventForgotPasswordObservable = MutableLiveData<Event<Result<Unit>>>()
    override val eventForgotPasswordObservable: LiveData<Event<Result<Unit>>>
        get() = _eventForgotPasswordObservable

    override fun forgotPassword() {
        val email = emailObservable.value
        when {
            email.isNullOrBlank() -> _eventForgotPasswordObservable.postValue(
                Event(InvalidDataError(message = "Email"))
            )
            else -> viewModelScope.launch {
                setLoading()
                val result = accountDataSource.forgotPassword(email)
                _eventForgotPasswordObservable.postValue(Event(result))
                setLoading(isLoading = false)
            }
        }
    }

}