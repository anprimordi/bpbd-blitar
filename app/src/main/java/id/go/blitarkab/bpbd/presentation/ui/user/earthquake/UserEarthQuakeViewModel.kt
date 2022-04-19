package id.go.blitarkab.bpbd.presentation.ui.user.earthquake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.datasource.DashboardDataSource
import id.go.blitarkab.bpbd.domain.model.EarthQuake
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserEarthQuakeViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource,
    @Repository private val dashboardDataSource: DashboardDataSource
) : BaseViewModel(accountDataSource), UserEarthQuakeContract.Presenter {

    private val _earthQuakeDetailObservable = MutableLiveData<Result<EarthQuake>>()
    override val earthQuakeDetailObservable: LiveData<Result<EarthQuake>>
        get() = _earthQuakeDetailObservable

    override fun loadData() {
        viewModelScope.launch {
            setLoading()
            val result = dashboardDataSource.getEarthQuakeDetail()
            _earthQuakeDetailObservable.postValue(result)
            setLoading(isLoading = false)
        }
    }

}