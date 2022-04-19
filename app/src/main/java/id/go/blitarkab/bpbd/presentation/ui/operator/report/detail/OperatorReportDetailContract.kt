package id.go.blitarkab.bpbd.presentation.ui.operator.report.detail

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface OperatorReportDetailContract {

    interface View : BaseView {
        fun loadReport()
        fun fetchReport(result: Result<Report>)
        fun onAcceptReportFinished(result: Result<Unit>)
        fun onRejectReportFinished(result: Result<Unit>)
        fun showAcceptReportConfirmationDialog()
        fun showRejectReportConfirmationDialog()
        fun showImageFullScreenView(startPosition: Int)
        fun copyToClipboard()
        fun openDialPhonePage()
    }

    interface Presenter : BasePresenter {
        val reportDetailObservable: LiveData<Result<Report>>

        val eventAcceptReportFinished: LiveData<Event<Result<Unit>>>
        val eventRejectReportFinished: LiveData<Event<Result<Unit>>>

        fun loadData(reportId: Long)
        fun acceptReport(reportId: Long)
        fun rejectReport(reportId: Long)
    }

}