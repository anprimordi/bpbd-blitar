package id.go.blitarkab.bpbd.presentation.ui.main.landing

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface LandingContract {

    companion object {
        const val ARG_CONTENT_POSITION: String = "fragment_argument_landing_content_position"
    }

    data class Content(
        @StringRes val titleRes: Int,
        @StringRes val messageRes: Int,
        @DrawableRes val imageRes: Int
    )

    data class ContentIndicator(
        val position: Int,
        val isSelected: Boolean
    )

    interface View : BaseView {
        fun onPrevPageClicked()
        fun onNextPageClicked()
        fun fetchContentIndicatorList(contentIndicators: List<ContentIndicator>)
        fun openLoginPage()
    }

    interface ViewContent : BaseView {
        fun fetchContent(content: Content)
    }

    interface Presenter : BasePresenter {
        val contentIndicatorListObservable: LiveData<List<ContentIndicator>>

        fun setContentPosition(position: Int)
        fun getContentObservable(position: Int): LiveData<Content>
    }

}