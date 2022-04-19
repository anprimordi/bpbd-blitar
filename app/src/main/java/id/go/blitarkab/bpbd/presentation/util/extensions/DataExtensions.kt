package id.go.blitarkab.bpbd.presentation.util.extensions

import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.domain.model.Report

fun Boolean.toInt() = if (this) 1 else 0

fun <T> ArrayList<T>.freshInsert(data: List<T>) {
    clear()
    addAll(data)
}

fun Report.Status.getDisplayNameRes(): Int {
    return when (this) {
        Report.Status.WAITING -> R.string.plain_report_status_waiting
        Report.Status.ACCEPTED -> R.string.plain_report_status_accepted
        Report.Status.REJECTED -> R.string.plain_report_status_rejected
    }
}

fun Report.Status.getDisplayColorRes(): Int {
    return when (this) {
        Report.Status.WAITING -> R.attr.colorStatus1
        Report.Status.ACCEPTED -> R.attr.colorStatus2
        Report.Status.REJECTED -> R.attr.colorStatus3
    }
}