package id.go.blitarkab.bpbd.data.remote.util

import id.go.blitarkab.bpbd.domain.model.common.Constants
import java.text.SimpleDateFormat
import java.util.*

object RemoteConstants {

    private const val SERVER_DATE_PATTERN: String = "yyyy-MM-dd"
    private const val SERVER_DATE_TIME_PATTERN: String = "yyyy-MM-dd HH:mm:ss"

    //region Formatter
    fun formatServerDate(date: Date): String {
        return SimpleDateFormat(SERVER_DATE_PATTERN, Locale.getDefault()).format(date)
    }

    fun formatServerDate(millis: Long): String {
        return SimpleDateFormat(SERVER_DATE_PATTERN, Locale.getDefault()).format(Date(millis))
    }

    fun formatServerDateTime(date: Date): String {
        return SimpleDateFormat(SERVER_DATE_TIME_PATTERN, Locale.getDefault()).format(date)
    }

    fun formatServerDateTime(millis: Long): String {
        return SimpleDateFormat(SERVER_DATE_TIME_PATTERN, Locale.getDefault()).format(Date(millis))
    }
    //endregion Formatter

    //region Parser
    fun parseServerDate(dateString: String): Date {
        return try {
            val formatter = SimpleDateFormat(SERVER_DATE_PATTERN, Locale.getDefault())
            formatter.parse(dateString) ?: throw Exception()
        } catch (ex: Exception) {
            Date(Constants.DEFAULT_DATE_TIME)
        }
    }

    fun parseServerDatetime(dateString: String): Date {
        return try {
            val formatter = SimpleDateFormat(SERVER_DATE_TIME_PATTERN, Locale.getDefault())
            formatter.parse(dateString) ?: throw Exception()
        } catch (ex: Exception) {
            Date(Constants.DEFAULT_DATE_TIME)
        }
    }
    //endregion Parser
}