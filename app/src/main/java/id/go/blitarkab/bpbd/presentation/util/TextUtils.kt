package id.go.blitarkab.bpbd.presentation.util

import java.text.NumberFormat
import java.util.*

object TextUtils {

    private val localeIdn = Locale("in", "ID")
    private val currencyFormatterIdn = NumberFormat.getCurrencyInstance(localeIdn)

    @JvmStatic
    @JvmOverloads
    fun formatCurrencyIdr(nominal: Double?, withPrefix: Boolean = false): String {
        val formatted = currencyFormatterIdn
            .format(nominal ?: 0.0)
            .substringBeforeLast(delimiter = ",00")
        return if (withPrefix) formatted.replace(oldValue = "Rp", newValue = "Rp. ")
        else formatted.substringAfter(delimiter = "Rp")
    }

    @JvmStatic
    @JvmOverloads
    fun formatCurrencyIdr(nominal: Long?, withPrefix: Boolean = false): String {
        val formatted = currencyFormatterIdn
            .format(nominal ?: 0L)
            .substringBeforeLast(delimiter = ",00")
        return if (withPrefix) formatted.replace(oldValue = "Rp", newValue = "Rp. ")
        else formatted.substringAfter(delimiter = "Rp")
    }

}