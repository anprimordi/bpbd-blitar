package id.go.blitarkab.bpbd.presentation.util

import id.go.blitarkab.bpbd.domain.model.common.Constants
import java.text.SimpleDateFormat
import java.util.*

object DateTimeFormatter {

    private const val DATE_TIME_PATTERN: String = "d MMMM yyyy HH:mm"
    private const val DATE_PATTERN: String = "d MMMM yyyy"
    private const val TIME_PATTERN: String = "HH:mm"
    private const val YEAR_PATTERN: String = "yyyy"

    private const val REPORT_DAY_DATE_TIME_PATTERN: String = "EEEE, d MMMM yyyy 'Pukul' HH:mm"

    private const val HOME_DATE_PATTERN: String = "EEEE, d MMM"
    private const val HOME_EARTH_QUAKE_DATE_PATTERN: String = "d MMM yyyy"
    private const val HOME_EARTH_QUAKE_TIME_PATTERN: String = "HH:mm:ss"

    fun get7DaysBeforeNow(): Date {
        val now = Calendar.getInstance()
        now.add(Calendar.WEEK_OF_YEAR, -1)
        return now.time
    }

    @JvmStatic
    fun getCurrentYear(): String {
        return SimpleDateFormat(YEAR_PATTERN, Locale.getDefault()).format(Date())
    }

    //region Formatter
    @JvmStatic
    fun formatReportDayDateTime(date: Date?): String {
        return if (date != null) SimpleDateFormat(REPORT_DAY_DATE_TIME_PATTERN, Locale.getDefault()).format(
            date
        ) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatReportDayDateTime(millis: Long?): String {
        return if (millis != null) SimpleDateFormat(REPORT_DAY_DATE_TIME_PATTERN, Locale.getDefault()).format(
            Date(millis)
        ) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatDateTime(date: Date?): String {
        return if (date != null) SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault()).format(
            date
        ) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatDateTime(millis: Long?): String {
        return if (millis != null) SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault()).format(
            Date(millis)
        ) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatDate(date: Date?): String {
        return if (date != null) SimpleDateFormat(
            DATE_PATTERN,
            Locale.getDefault()
        ).format(date) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatDate(millis: Long?): String {
        return if (millis != null) SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(
            Date(
                millis
            )
        ) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatHomeDate(millis: Long?): String {
        return if (millis != null) SimpleDateFormat(HOME_DATE_PATTERN, Locale.getDefault()).format(
            Date(
                millis
            )
        ) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatHomeDate(date: Date?): String {
        return if (date != null) SimpleDateFormat(
            HOME_DATE_PATTERN,
            Locale.getDefault()
        ).format(date) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatHomeEarthQuakeDate(millis: Long?): String {
        return if (millis != null) SimpleDateFormat(HOME_EARTH_QUAKE_DATE_PATTERN, Locale.getDefault()).format(
            Date(
                millis
            )
        ) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatHomeEarthQuakeDate(date: Date?): String {
        return if (date != null) SimpleDateFormat(
            HOME_EARTH_QUAKE_DATE_PATTERN,
            Locale.getDefault()
        ).format(date) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatHomeEarthQuakeTime(millis: Long?): String {
        return if (millis != null) SimpleDateFormat(HOME_EARTH_QUAKE_TIME_PATTERN, Locale.getDefault()).format(
            Date(
                millis
            )
        ) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatHomeEarthQuakeTime(date: Date?): String {
        return if (date != null) SimpleDateFormat(
            HOME_EARTH_QUAKE_TIME_PATTERN,
            Locale.getDefault()
        ).format(date) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatTime(date: Date?): String {
        return if (date != null) SimpleDateFormat(
            TIME_PATTERN,
            Locale.getDefault()
        ).format(date) else Constants.DEFAULT_CONTENT
    }

    @JvmStatic
    fun formatTime(millis: Long?): String {
        return if (millis != null) SimpleDateFormat(TIME_PATTERN, Locale.getDefault()).format(
            Date(
                millis
            )
        ) else Constants.DEFAULT_CONTENT
    }
    //endregion Formatter

}