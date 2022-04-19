package id.go.blitarkab.bpbd.presentation.ui.main.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.InvalidDataError
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @Repository private val accountDataSource: AccountDataSource
) : BaseViewModel(accountDataSource), LoginContract.Presenter {

    override val emailObservable = MutableLiveData<String>()
    override val passwordObservable = MutableLiveData<String>()

    private val _eventLoginObservable = MutableLiveData<Event<Result<Account>>>()
    override val eventLoginObservable: LiveData<Event<Result<Account>>>
        get() = _eventLoginObservable

    override fun login() {
        val email = emailObservable.value
        val password = passwordObservable.value
        when {
            email.isNullOrBlank() -> _eventLoginObservable.postValue(
                Event(InvalidDataError(message = "Email"))
            )
            password.isNullOrBlank() -> _eventLoginObservable.postValue(
                Event(InvalidDataError(message = "Password"))
            )
            else -> viewModelScope.launch {
                setLoading()
                val result = accountDataSource.login(email, password)
                if (result is Success) {
                    val account = result.data
                    val saveResult = accountDataSource.saveAccount(account)
                    _eventLoginObservable.postValue(Event(saveResult.map { account }))
                } else {
                    _eventLoginObservable.postValue(Event(result))
                }
                setLoading(isLoading = false)
            }
        }
    }

}