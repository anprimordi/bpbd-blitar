package id.go.blitarkab.bpbd.presentation.ui.operator.report.detail

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
class OperatorReportDetailViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource,
    @Repository private val reportDataSource: ReportDataSource
) : BaseViewModel(accountDataSource), OperatorReportDetailContract.Presenter {

    private val _reportDetailObservable = MutableLiveData<Result<Report>>()
    override val reportDetailObservable: LiveData<Result<Report>>
        get() = _reportDetailObservable

    private val _eventAcceptReportFinished = MutableLiveData<Event<Result<Unit>>>()
    override val eventAcceptReportFinished: LiveData<Event<Result<Unit>>>
        get() = _eventAcceptReportFinished

    private val _eventRejectReportFinished = MutableLiveData<Event<Result<Unit>>>()
    override val eventRejectReportFinished: LiveData<Event<Result<Unit>>>
        get() = _eventRejectReportFinished

    override fun loadData(reportId: Long) {
        viewModelScope.launch {
            setLoading()
            val result = reportDataSource.getOperatorReportDetail(reportId)
            _reportDetailObservable.postValue(result)
            setLoading(isLoading = false)
        }
    }

    override fun acceptReport(reportId: Long) {
        viewModelScope.launch {
            setLoading()
            val result = reportDataSource.submitOperatorReport(
                reportId = reportId,
                status = Report.Status.ACCEPTED
            )
            _eventAcceptReportFinished.postValue(Event(result))
            setLoading(isLoading = false)
        }
    }

    override fun rejectReport(reportId: Long) {
        viewModelScope.launch {
            setLoading()
            val result = reportDataSource.submitOperatorReport(
                reportId = reportId,
                status = Report.Status.REJECTED
            )
            _eventAcceptReportFinished.postValue(Event(result))
            setLoading(isLoading = false)
        }
    }

}