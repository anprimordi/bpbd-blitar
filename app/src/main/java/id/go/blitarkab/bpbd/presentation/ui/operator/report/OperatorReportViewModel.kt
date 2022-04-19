package id.go.blitarkab.bpbd.presentation.ui.operator.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.datasource.ReportDataSource
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperatorReportViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource,
    @Repository private val reportDataSource: ReportDataSource
) : BaseViewModel(accountDataSource), OperatorReportContract.Presenter {

    private val _reportWaitingListObservable = MutableLiveData<Result<List<Report>>>()
    override val reportWaitingListObservable: LiveData<Result<List<Report>>>
        get() = _reportWaitingListObservable

    private val _reportFinishedListObservable = MutableLiveData<Result<List<Report>>>()
    override val reportFinishedListObservable: LiveData<Result<List<Report>>>
        get() = _reportFinishedListObservable

    private val _eventOpenDetailReportPageObservable = MutableLiveData<Event<Report>>()
    override val eventOpenDetailReportPageObservable: LiveData<Event<Report>>
        get() = _eventOpenDetailReportPageObservable

    override fun loadReportWaitingList() {
        viewModelScope.launch {
            setLoading()
            val result = reportDataSource.getOperatorReportHistoryWaitingList()
            _reportWaitingListObservable.postValue(result)
            setLoading(isLoading = false)
        }
    }

    override fun loadReportFinishedList() {
        viewModelScope.launch {
            setLoading()
            val result = reportDataSource.getOperatorReportHistoryFinishedList()
            _reportFinishedListObservable.postValue(result)
            setLoading(isLoading = false)
        }
    }

    override fun triggerEventOpenDetailReportPage(report: Report) {
        _eventOpenDetailReportPageObservable.value = Event(report)
    }

}