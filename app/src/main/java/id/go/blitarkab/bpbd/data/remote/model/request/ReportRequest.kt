package id.go.blitarkab.bpbd.data.remote.model.request

import com.google.gson.annotations.SerializedName
import id.go.blitarkab.bpbd.data.remote.model.common.ReportStatusRemote
import id.go.blitarkab.bpbd.domain.model.Report

class ReportRequest {

    companion object {
        fun createOperatorSubmit(reportId: Long, status: Report.Status): OperatorSubmit {
            return OperatorSubmit(
                id = reportId,
                status = ReportStatusRemote.fromDomain(status).value
            )
        }
    }

    data class OperatorSubmit(
        @SerializedName(value = "id") val id: Long,
        @SerializedName(value = "status") val status: String
    )

}