package id.go.blitarkab.bpbd.data.remote.source

import id.go.blitarkab.bpbd.data.remote.AppRemoteClient
import id.go.blitarkab.bpbd.data.remote.FirebaseClient
import id.go.blitarkab.bpbd.data.remote.model.request.MessagingRequest
import id.go.blitarkab.bpbd.data.remote.model.request.ReportRequest
import id.go.blitarkab.bpbd.data.remote.service.FirebaseMessagingService
import id.go.blitarkab.bpbd.data.remote.service.ReportService
import id.go.blitarkab.bpbd.data.remote.util.RemoteDateTimeUtils
import id.go.blitarkab.bpbd.data.remote.util.execute
import id.go.blitarkab.bpbd.domain.datasource.ReportDataSource
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.NotFoundError
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ReportRemoteDataSource @Inject constructor(
    private val remoteClient: AppRemoteClient,
    private val firebaseClient: FirebaseClient
) : ReportDataSource {

    override suspend fun getUserReportList(): Result<List<Report>> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(ReportService::class.java)
                service.getUserReportList()
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.map { it.toDomain() })
                else NotFoundError()
            }
        }
    }

    override suspend fun getUserReportHistoryList(): Result<List<Report>> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(ReportService::class.java)
                service.getUserReportHistoryList()
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.map { it.toDomain() })
                else NotFoundError()
            }
        }
    }

    override suspend fun getUserReportDetail(reportId: Long): Result<Report> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(ReportService::class.java)
                service.getUserReportDetail(reportId)
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.toDomain())
                else NotFoundError()
            }
        }
    }

    override suspend fun submitUserReport(data: ReportDataSource.SubmitData): Result<Unit> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(ReportService::class.java)

                val bodyTitle = data.title.toRequestBody(MultipartBody.FORM)
                val bodyContent = data.content.toRequestBody(MultipartBody.FORM)
                val bodyAddress = data.address.toRequestBody(MultipartBody.FORM)
                val bodyLatitude = data.latitude.toString().toRequestBody(MultipartBody.FORM)
                val bodyLongitude = data.longitude.toString().toRequestBody(MultipartBody.FORM)
                val bodyDatetime = RemoteDateTimeUtils.getCurrentDateTimeForCreateReport().toRequestBody(MultipartBody.FORM)
                val bodyReportData = hashMapOf(
                    Pair("type", bodyTitle),
                    Pair("content", bodyContent),
                    Pair("address", bodyAddress),
                    Pair("latitude", bodyLatitude),
                    Pair("longitude", bodyLongitude),
                    Pair("datetime", bodyDatetime),
                )

                val bodyReportImageList = arrayListOf<MultipartBody.Part>()
                for (item in data.imageDataList.withIndex()) {
                    val index = item.index
                    val image = item.value
                    val reportImage = image.second.toRequestBody(
                        contentType = "image/*".toMediaType()
                    )
                    val bodyReportImage = MultipartBody.Part.createFormData(
                        name = "image[$index]",
                        filename = image.first,
                        body = reportImage
                    )
                    bodyReportImageList.add(bodyReportImage)
                }

                val submitResult = service.submitUserReport(
                    data = bodyReportData,
                    reportImage = bodyReportImageList
                )

                val messagingService = firebaseClient.create(FirebaseMessagingService::class.java)
                val messagingRequest = MessagingRequest(
                    to = "/topics/operator_new_reports",
                    notification = mapOf(
                        "title" to "BPBD Kab. Blitar",
                        "body" to "Ada laporan bencana alam baru dari masyarakat.",
                        "sound" to "default"
                    ),
                    priority = 10,
                    android = mapOf(
                        "priority" to "high"
                    )
                )
                messagingService.sendMessage(messagingRequest)

                return@execute submitResult
            }.mapTo { response ->
                if (response.isSuccess) Success(Unit)
                else NotFoundError()
            }
        }
    }

    override suspend fun getOperatorReportList(): Result<List<Report>> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(ReportService::class.java)
                service.getOperatorReportList()
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.map { it.toDomain() })
                else NotFoundError()
            }
        }
    }

    override suspend fun getOperatorReportHistoryWaitingList(): Result<List<Report>> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(ReportService::class.java)
                service.getOperatorReportHistoryWaitingList()
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.map { it.toDomain() })
                else NotFoundError()
            }
        }
    }

    override suspend fun getOperatorReportHistoryFinishedList(): Result<List<Report>> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(ReportService::class.java)
                service.getOperatorReportHistoryFinishedList()
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.map { it.toDomain() })
                else NotFoundError()
            }
        }
    }

    override suspend fun getOperatorReportDetail(reportId: Long): Result<Report> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(ReportService::class.java)
                service.getOperatorReportDetail(reportId)
            }.mapTo { response ->
                if (response.isSuccess && response.data != null) Success(response.data.toDomain())
                else NotFoundError()
            }
        }
    }

    override suspend fun submitOperatorReport(
        reportId: Long,
        status: Report.Status
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {
            execute {
                val service = remoteClient.create(ReportService::class.java)
                val request = ReportRequest.createOperatorSubmit(reportId, status)
                service.submitOperatorReport(request)
            }.mapTo { response ->
                if (response.isSuccess) Success(Unit)
                else NotFoundError()
            }
        }
    }

}