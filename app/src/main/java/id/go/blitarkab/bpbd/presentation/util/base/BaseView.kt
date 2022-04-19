package id.go.blitarkab.bpbd.presentation.util.base

import android.view.View
import id.go.blitarkab.bpbd.domain.model.common.Error

interface BaseView {
    fun <T> getErrorMessage(result: Error<T>): String
    fun <T> showErrorMessage(result: Error<T>, view: View)
    fun showUnderDevelopmentMessage()
    fun closePage()
}