package id.go.blitarkab.bpbd.presentation.ui.main.others

import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface AddPhotoChooserContract {

    companion object {
        const val REQUEST_GET_CHOOSER_TYPE: String = "request_key_get_chooser_type"
        const val BUNDLE_KEY_CHOOSER_TYPE: String = "bundle_key_chooser_type"
    }

    interface View : BaseView {
        fun onCameraChosen()
        fun onGalleryChosen()
    }

    interface Presenter : BasePresenter {

    }

}