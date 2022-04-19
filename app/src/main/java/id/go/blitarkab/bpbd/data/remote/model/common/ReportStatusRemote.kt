package id.go.blitarkab.bpbd.data.remote.model.common

import com.google.gson.annotations.SerializedName
import id.go.blitarkab.bpbd.domain.model.Report

enum class ReportStatusRemote(val value: String) {

    @SerializedName(value = "waiting", alternate = [])
    WAITING(value = "waiting"),

    @SerializedName(value = "accepted", alternate = [])
    ACCEPTED(value = "accepted"),

    @SerializedName(value = "rejected", alternate = [])
    REJECTED(value = "rejected");

    companion object {
        fun fromDomain(model: Report.Status): ReportStatusRemote {
            return when (model) {
                Report.Status.WAITING -> WAITING
                Report.Status.ACCEPTED -> ACCEPTED
                Report.Status.REJECTED -> REJECTED
            }
        }
    }

    fun toDomain(): Report.Status {
        return when (this) {
            WAITING -> Report.Status.WAITING
            ACCEPTED -> Report.Status.ACCEPTED
            REJECTED -> Report.Status.REJECTED
        }
    }

}