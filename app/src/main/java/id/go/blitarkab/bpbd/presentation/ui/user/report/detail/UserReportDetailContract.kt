package id.go.blitarkab.bpbd.presentation.ui.user.report.detail

import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface UserReportDetailContract {

    interface View : BaseView {
        fun loadReport()
        fun fetchReport(result: Result<Report>)
        fun showImageFullScreenView(startPosition: Int)
        fun copyToClipboard()
        fun openDialPhonePage()
    }

    interface Presenter : BasePresenter {
        val reportDetailObservable: LiveData<Result<Report>>
        fun loadData(reportId: Long)
    }

}