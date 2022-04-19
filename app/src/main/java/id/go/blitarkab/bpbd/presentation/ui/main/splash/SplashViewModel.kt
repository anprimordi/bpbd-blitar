package id.go.blitarkab.bpbd.presentation.ui.main.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @Repository private val accountDataSource: AccountDataSource
) : BaseViewModel(accountDataSource), SplashContract.Presenter {

    private val _accountStateObservable = MutableLiveData<Account?>()
    override val accountStateObservable: LiveData<Account?>
        get() = _accountStateObservable

    override fun loadAccount() {
        setLoading()
        viewModelScope.launch {
            val currentAccount = getCurrentAccount()
            _accountStateObservable.postValue(currentAccount)
            setLoading(isLoading = false)
        }
    }

}