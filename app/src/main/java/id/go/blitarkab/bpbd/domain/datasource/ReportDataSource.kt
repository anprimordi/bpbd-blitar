package id.go.blitarkab.bpbd.domain.datasource

import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Result

interface ReportDataSource {

    suspend fun getUserReportList(): Result<List<Report>>
    suspend fun getUserReportHistoryList(): Result<List<Report>>
    suspend fun getUserReportDetail(reportId: Long): Result<Report>
    suspend fun submitUserReport(data: SubmitData): Result<Unit>

    suspend fun getOperatorReportList(): Result<List<Report>>
    suspend fun getOperatorReportHistoryWaitingList(): Result<List<Report>>
    suspend fun getOperatorReportHistoryFinishedList(): Result<List<Report>>
    suspend fun getOperatorReportDetail(reportId: Long): Result<Report>
    suspend fun submitOperatorReport(reportId: Long, status: Report.Status): Result<Unit>

    class SubmitData(
        val title: String,
        val content: String,
        val address: String,
        val latitude: Double,
        val longitude: Double,
        val imageDataList: List<Pair<String, ByteArray>>
    )

}