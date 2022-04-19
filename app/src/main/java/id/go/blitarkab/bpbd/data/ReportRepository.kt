package id.go.blitarkab.bpbd.data

import id.go.blitarkab.bpbd.di.datasource.RemoteDataSource
import id.go.blitarkab.bpbd.domain.datasource.ReportDataSource
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Result
import javax.inject.Inject

class ReportRepository @Inject constructor(
    @RemoteDataSource private val remoteSource: ReportDataSource
) : ReportDataSource {

    override suspend fun getUserReportList(): Result<List<Report>> {
        return remoteSource.getUserReportList()
    }

    override suspend fun getUserReportHistoryList(): Result<List<Report>> {
        return remoteSource.getUserReportHistoryList()
    }

    override suspend fun getUserReportDetail(reportId: Long): Result<Report> {
        return remoteSource.getUserReportDetail(reportId)
    }

    override suspend fun submitUserReport(data: ReportDataSource.SubmitData): Result<Unit> {
        return remoteSource.submitUserReport(data)
    }

    override suspend fun getOperatorReportList(): Result<List<Report>> {
        return remoteSource.getOperatorReportList()
    }

    override suspend fun getOperatorReportHistoryWaitingList(): Result<List<Report>> {
        return remoteSource.getOperatorReportHistoryWaitingList()
    }

    override suspend fun getOperatorReportHistoryFinishedList(): Result<List<Report>> {
        return remoteSource.getOperatorReportHistoryFinishedList()
    }

    override suspend fun getOperatorReportDetail(reportId: Long): Result<Report> {
        return remoteSource.getOperatorReportDetail(reportId)
    }

    override suspend fun submitOperatorReport(
        reportId: Long,
        status: Report.Status
    ): Result<Unit> {
        return remoteSource.submitOperatorReport(reportId, status)
    }

}