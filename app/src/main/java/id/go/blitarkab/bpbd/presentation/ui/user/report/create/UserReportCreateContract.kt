package id.go.blitarkab.bpbd.presentation.ui.user.report.create

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esafirm.imagepicker.model.Image
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface UserReportCreateContract {

    class ReportImage(
        val id: Long,
        val name: String,
        val content: ByteArray
    )

    interface View : BaseView {
        fun submitReport()
        fun onSubmitReportFinished(result: Result<Unit>)
        fun showImageChooserSheet()
        fun showImageFullScreenView(startPosition: Int)
        fun openSelectLocationPage()
        fun openCameraPage()
        fun openGalleryPage()
    }

    interface Presenter : BasePresenter {
        val titleObservable: MutableLiveData<String>
        val contentObservable: MutableLiveData<String>
        val addressObservable: MutableLiveData<String>
        val coordinateObservable: LiveData<String>
        val latitudeObservable: LiveData<Double>
        val longitudeObservable: LiveData<Double>
        val imageListObservable: LiveData<List<ReportImage>>

        val eventSubmitReportObservable: LiveData<Event<Result<Unit>>>

        fun setCoordinate(latitude: Double, longitude: Double)
        fun addImages(context: Context, images: List<Image>)
        fun removeImage(image: ReportImage)
        fun submitReport(context: Context)
    }

}