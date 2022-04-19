package id.go.blitarkab.bpbd.data.remote.service

import id.go.blitarkab.bpbd.data.remote.model.common.Wrapper
import id.go.blitarkab.bpbd.data.remote.model.request.ReportRequest
import id.go.blitarkab.bpbd.data.remote.model.response.ReportResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

@JvmSuppressWildcards
interface ReportService {

    @GET(value = "user/report/get-list")
    suspend fun getUserReportList(): Wrapper<List<ReportResponse>>

    @GET(value = "user/report/get-history")
    suspend fun getUserReportHistoryList(): Wrapper<List<ReportResponse>>

    @GET(value = "user/report/detail")
    suspend fun getUserReportDetail(@Query(value = "id") reportId: Long): Wrapper<ReportResponse>

    @Headers(value = ["Enctype: multipart/form-data"])
    @Multipart
    @POST(value = "user/report/submit")
    suspend fun submitUserReport(
        @PartMap data: Map<String, RequestBody>,
        @Part reportImage: List<MultipartBody.Part>
    ): Wrapper<Unit>

    @GET(value = "officer/report/get-list")
    suspend fun getOperatorReportList(): Wrapper<List<ReportResponse>>

    @GET(value = "officer/report/get-new-report")
    suspend fun getOperatorReportHistoryWaitingList(): Wrapper<List<ReportResponse>>

    @GET(value = "officer/report/get-history")
    suspend fun getOperatorReportHistoryFinishedList(): Wrapper<List<ReportResponse>>

    @GET(value = "officer/report/detail")
    suspend fun getOperatorReportDetail(@Query(value = "id") reportId: Long): Wrapper<ReportResponse>

    @POST(value = "officer/report/submit-status")
    suspend fun submitOperatorReport(@Body body: ReportRequest.OperatorSubmit): Wrapper<Unit>

}