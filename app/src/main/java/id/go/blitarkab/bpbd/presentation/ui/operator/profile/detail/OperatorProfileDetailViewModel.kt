package id.go.blitarkab.bpbd.presentation.ui.operator.profile.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperatorProfileDetailViewModel @Inject constructor(
    @Repository private val accountDataSource: AccountDataSource
) : BaseViewModel(accountDataSource), OperatorProfileDetailContract.Presenter {

    private val _accountObservable = MutableLiveData<Result<Account>>()
    override val accountObservable: LiveData<Result<Account>>
        get() = _accountObservable

    override fun loadData() {
        viewModelScope.launch {
            setLoading()
            val result = accountDataSource.getOfficerDetail()
            _accountObservable.postValue(result)
            setLoading(isLoading = false)
        }
    }

}