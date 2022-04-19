package id.go.blitarkab.bpbd.presentation.ui.user.volcano

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.datasource.DashboardDataSource
import id.go.blitarkab.bpbd.domain.model.Volcano
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserVolcanoViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource,
    @Repository private val dashboardDataSource: DashboardDataSource
) : BaseViewModel(accountDataSource), UserVolcanoContract.Presenter {

    private val _volcanoListObservable = MutableLiveData<Result<List<Volcano>>>()
    override val volcanoListObservable: LiveData<Result<List<Volcano>>>
        get() = _volcanoListObservable

    override fun loadData() {
        viewModelScope.launch {
            setLoading()
            val result = dashboardDataSource.getVolcanoList()
            _volcanoListObservable.postValue(result)
            setLoading(isLoading = false)
        }
    }

}