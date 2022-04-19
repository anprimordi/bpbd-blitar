package id.go.blitarkab.bpbd.presentation.ui.user.report.detail

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
class UserReportDetailViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource,
    @Repository private val reportDataSource: ReportDataSource
) : BaseViewModel(accountDataSource), UserReportDetailContract.Presenter {

    private val _reportDetailObservable = MutableLiveData<Result<Report>>()
    override val reportDetailObservable: LiveData<Result<Report>>
        get() = _reportDetailObservable

    override fun loadData(reportId: Long) {
        viewModelScope.launch {
            setLoading()
            val result = reportDataSource.getUserReportDetail(reportId)
            _reportDetailObservable.postValue(result)
            setLoading(isLoading = false)
        }
    }

}