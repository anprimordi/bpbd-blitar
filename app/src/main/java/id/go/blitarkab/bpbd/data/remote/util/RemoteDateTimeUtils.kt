package id.go.blitarkab.bpbd.data.remote.util

import id.go.blitarkab.bpbd.domain.model.common.Constants
import java.text.SimpleDateFormat
import java.util.*

object RemoteDateTimeUtils {

    private const val SERVER_DATE_PATTERN: String = "yyyy-MM-dd"
    private const val SERVER_DATE_TIME_PATTERN: String = "yyyy-MM-dd HH:mm:ss"
    private const val EARTH_QUAKE_DATE_TIME_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ss"
    private const val NEWS_DATE_TIME_PATTERN: String = "yyyy-MM-dd HH:mm:ss"

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

    fun parseEarthQuakeDatetime(dateString: String): Date {
        return try {
            val formatter = SimpleDateFormat(EARTH_QUAKE_DATE_TIME_PATTERN, Locale.getDefault())
            formatter.parse(dateString) ?: throw Exception()
        } catch (ex: Exception) {
            Date(Constants.DEFAULT_DATE_TIME)
        }
    }

    fun parseNewsDatetime(dateString: String): Date {
        return try {
            val formatter = SimpleDateFormat(NEWS_DATE_TIME_PATTERN, Locale.getDefault())
            formatter.parse(dateString) ?: throw Exception()
        } catch (ex: Exception) {
            Date(Constants.DEFAULT_DATE_TIME)
        }
    }

    fun getCurrentTimeForWeather(): Int {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        return when(hour) {
            in 0..5 -> 0
            in 6..11 -> 6
            in 12..17 -> 12
            in 18..23 -> 18
            else -> 0
        }
    }

    fun getCurrentDateTimeForCreateReport(): String {
        val date = Date()
        return SimpleDateFormat(SERVER_DATE_TIME_PATTERN, Locale.getDefault()).format(date)
    }
    //endregion Parser
}