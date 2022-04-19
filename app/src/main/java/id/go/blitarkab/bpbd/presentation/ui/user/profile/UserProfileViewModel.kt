package id.go.blitarkab.bpbd.presentation.ui.user.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    @Repository private val accountDataSource: AccountDataSource
) : BaseViewModel(accountDataSource), UserProfileContract.Presenter {

    private val _accountObservable = MutableLiveData<Result<Account>>()
    override val accountObservable: LiveData<Result<Account>>
        get() = _accountObservable

    private val _eventLogoutObservable = MutableLiveData<Event<Result<Unit>>>()
    override val eventLogoutObservable: LiveData<Event<Result<Unit>>>
        get() = _eventLogoutObservable

    override fun loadData() {
        viewModelScope.launch {
            setLoading()
            val result = accountDataSource.getAccount()
            _accountObservable.postValue(result)
            setLoading(isLoading = false)
        }
    }

    override fun logout() {
        viewModelScope.launch {
            setLoading()
            val result = accountDataSource.logout()
            _eventLogoutObservable.postValue(Event(result))
            setLoading(isLoading = false)
        }
    }

}