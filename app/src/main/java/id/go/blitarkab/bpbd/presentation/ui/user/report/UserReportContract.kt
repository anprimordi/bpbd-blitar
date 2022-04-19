package id.go.blitarkab.bpbd.presentation.ui.user.report

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface UserReportContract {

    interface View : BaseView {
        fun fetchReportHistoryList(result: Result<List<Report>>)
        fun openReportDetailPage(report: Report)
    }

    interface Presenter : BasePresenter {
        val reportHistoryListObservable: LiveData<Result<List<Report>>>
        fun loadData()
    }

}