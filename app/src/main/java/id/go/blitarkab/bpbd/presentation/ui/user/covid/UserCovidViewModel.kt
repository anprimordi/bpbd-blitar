package id.go.blitarkab.bpbd.presentation.ui.user.covid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.datasource.DashboardDataSource
import id.go.blitarkab.bpbd.domain.model.Covid
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCovidViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource,
    @Repository private val dashboardDataSource: DashboardDataSource
) : BaseViewModel(accountDataSource), UserCovidContract.Presenter {

    private val _covidDetailObservable = MutableLiveData<Result<Pair<Covid, List<Covid>>>>()
    override val covidDetailObservable: LiveData<Result<Pair<Covid, List<Covid>>>>
        get() = _covidDetailObservable

    override fun loadData() {
        viewModelScope.launch {
            setLoading()
            val result = dashboardDataSource.getCovidInfoDetail()
            _covidDetailObservable.postValue(result)
            setLoading(isLoading = false)
        }
    }

}