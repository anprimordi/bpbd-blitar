package id.go.blitarkab.bpbd.presentation.ui.operator.report.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.datasource.ReportDataSource
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperatorReportListViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource,
    @Repository private val reportDataSource: ReportDataSource
) : BaseViewModel(accountDataSource), OperatorReportListContract.Presenter {

    private val _reportListObservable = MutableLiveData<Result<List<Report>>>()
    override val reportListObservable: LiveData<Result<List<Report>>>
        get() = _reportListObservable

    override fun loadData() {
        viewModelScope.launch {
            setLoading()
            val result = reportDataSource.getOperatorReportList()
            _reportListObservable.postValue(result)
            setLoading(isLoading = false)
        }
    }

}