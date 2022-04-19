package id.go.blitarkab.bpbd.presentation.ui.user.report

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
class UserReportViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource,
    @Repository private val reportDataSource: ReportDataSource
) : BaseViewModel(accountDataSource), UserReportContract.Presenter {

    private val _reportHistoryListObservable = MutableLiveData<Result<List<Report>>>()
    override val reportHistoryListObservable: LiveData<Result<List<Report>>>
        get() = _reportHistoryListObservable

    override fun loadData() {
        viewModelScope.launch {
            setLoading()
            val result = reportDataSource.getUserReportHistoryList()
            _reportHistoryListObservable.postValue(result)
            setLoading(isLoading = false)
        }
    }

}