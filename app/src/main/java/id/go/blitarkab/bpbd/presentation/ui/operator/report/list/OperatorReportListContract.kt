package id.go.blitarkab.bpbd.presentation.ui.operator.report.list

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface OperatorReportListContract {

    interface View : BaseView {
        fun fetchReportList(result: Result<List<Report>>)
        fun openReportDetailPage(report: Report)
    }

    interface Presenter : BasePresenter {
        val reportListObservable: LiveData<Result<List<Report>>>
        fun loadData()
    }

}