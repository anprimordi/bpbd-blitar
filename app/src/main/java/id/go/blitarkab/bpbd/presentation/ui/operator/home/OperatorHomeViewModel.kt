package id.go.blitarkab.bpbd.presentation.ui.operator.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.datasource.DashboardDataSource
import id.go.blitarkab.bpbd.domain.datasource.ReportDataSource
import id.go.blitarkab.bpbd.domain.model.*
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.DateTimeFormatter
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OperatorHomeViewModel @Inject constructor(
    @Repository private val accountDataSource: AccountDataSource,
    @Repository private val reportDataSource: ReportDataSource,
    @Repository private val dashboardDataSource: DashboardDataSource
) : BaseViewModel(accountDataSource), OperatorHomeContract.Presenter {

    private val _todayObservable = MutableLiveData<String>()
    override val todayObservable: LiveData<String>
        get() = _todayObservable

    private val _accountObservable = MutableLiveData<Result<Account>>()
    override val accountObservable: LiveData<Result<Account>>
        get() = _accountObservable

    private val _dashboardInfoObservable = MutableLiveData<Result<Dashboard>>()
    override val dashboardInfoObservable: LiveData<Result<Dashboard>>
        get() = _dashboardInfoObservable

    private val _reportListObservable = MutableLiveData<Result<List<Report>>>()
    override val reportListObservable: LiveData<Result<List<Report>>>
        get() = _reportListObservable

    private val _weatherListObservable = MutableLiveData<Result<List<Weather>>>()
    override val weatherListObservable: LiveData<Result<List<Weather>>>
        get() = _weatherListObservable

    override fun loadData() {
        setLoading()
        val todayJob = viewModelScope.launch {
            val today = Date()
            val formatted = DateTimeFormatter.formatHomeDate(today)
            _todayObservable.postValue(formatted)
        }

        val accountJob = viewModelScope.launch {
            val result = accountDataSource.getAccount()
            _accountObservable.postValue(result)
        }

        val dashboardJob = viewModelScope.launch {
            val result = dashboardDataSource.getDashboardInfo()
            _dashboardInfoObservable.postValue(result)
        }

        val reportJob = viewModelScope.launch {
            val result = reportDataSource.getOperatorReportList()
            _reportListObservable.postValue(result)
        }

        val weatherJob = viewModelScope.launch {
            val result = dashboardDataSource.getWeatherList()
            _weatherListObservable.postValue(result)
        }

        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                if (todayJob.isCompleted &&
                    accountJob.isCompleted &&
                    dashboardJob.isCompleted &&
                    reportJob.isCompleted &&
                    weatherJob.isCompleted
                ) break
            }
            setLoading(isLoading = false)
        }
    }

}