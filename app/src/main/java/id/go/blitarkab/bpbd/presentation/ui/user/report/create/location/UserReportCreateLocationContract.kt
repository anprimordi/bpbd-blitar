package id.go.blitarkab.bpbd.presentation.ui.user.report.create.location

import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface UserReportCreateLocationContract {

    companion object {
        const val REQUEST_GET_COORDINATE: String = "request_key_get_coordinate"
        const val ARG_KEY_LATITUDE: String = "argument_key_latitude"
        const val ARG_KEY_LONGITUDE: String = "argument_key_longitude"
    }

    interface View : BaseView {

    }

    interface Presenter : BasePresenter {

    }

}