package id.go.blitarkab.bpbd.presentation.ui.user.report.create

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.esafirm.imagepicker.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.datasource.ReportDataSource
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.InvalidDataError
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import id.go.blitarkab.bpbd.presentation.util.extensions.default
import id.go.blitarkab.bpbd.presentation.util.extensions.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserReportCreateViewModel @Inject constructor(
    @Repository accountDataSource: AccountDataSource,
    @Repository private val reportDataSource: ReportDataSource
) : BaseViewModel(accountDataSource), UserReportCreateContract.Presenter {

    override val titleObservable = MutableLiveData<String>()
    override val contentObservable = MutableLiveData<String>()
    override val addressObservable = MutableLiveData<String>()

    private val _coordinateObservable = MutableLiveData<String>()
    override val coordinateObservable: LiveData<String>
        get() = _coordinateObservable

    private val _latitudeObservable = MutableLiveData<Double>()
    override val latitudeObservable: LiveData<Double>
        get() = _latitudeObservable

    private val _longitudeObservable = MutableLiveData<Double>()
    override val longitudeObservable: LiveData<Double>
        get() = _longitudeObservable

    private val _imageListObservable =
        MutableLiveData<List<UserReportCreateContract.ReportImage>>().default(emptyList())
    override val imageListObservable: LiveData<List<UserReportCreateContract.ReportImage>>
        get() = _imageListObservable

    private val _eventSubmitReportObservable = MutableLiveData<Event<Result<Unit>>>()
    override val eventSubmitReportObservable: LiveData<Event<Result<Unit>>>
        get() = _eventSubmitReportObservable

    override fun setCoordinate(latitude: Double, longitude: Double) {
        _latitudeObservable.value = latitude
        _longitudeObservable.value = longitude
        _coordinateObservable.value = "$latitude, $longitude"
    }

    override fun addImages(context: Context, images: List<Image>) {
        val currentList = imageListObservable.value ?: emptyList()
        viewModelScope.launch(Dispatchers.IO) {
            val newImages = images.map {
                UserReportCreateContract.ReportImage(
                    id = it.id,
                    name = it.name,
                    content = it.uri.toByteArray(context) ?: byteArrayOf()
                )
            }
            _imageListObservable.postValue(currentList + newImages)
        }
    }

    override fun removeImage(image: UserReportCreateContract.ReportImage) {
        val currentList = imageListObservable.value ?: emptyList()
        _imageListObservable.value = currentList - image
    }

    override fun submitReport(context: Context) {
        val title = titleObservable.value
        val content = contentObservable.value
        val address = addressObservable.value
        val latitude = latitudeObservable.value
        val longitude = longitudeObservable.value
        val imageList = imageListObservable.value ?: emptyList()
        when {
            title.isNullOrBlank() -> _eventSubmitReportObservable.postValue(
                Event(InvalidDataError(message = "Bencana Alam"))
            )
            content.isNullOrBlank() -> _eventSubmitReportObservable.postValue(
                Event(InvalidDataError(message = "Deskripsi"))
            )
            address.isNullOrBlank() -> _eventSubmitReportObservable.postValue(
                Event(InvalidDataError(message = "Lokasi Kejadian"))
            )
            latitude == null -> _eventSubmitReportObservable.postValue(
                Event(InvalidDataError(message = "Koordinat"))
            )
            longitude == null -> _eventSubmitReportObservable.postValue(
                Event(InvalidDataError(message = "Koordinat"))
            )
            imageList.isEmpty() -> _eventSubmitReportObservable.postValue(
                Event(InvalidDataError(message = "Foto Kejadian"))
            )
            else -> viewModelScope.launch(Dispatchers.IO) {
                try {
                    setLoading()
                    val imageDataList = arrayListOf<Pair<String, ByteArray>>()
                    for (image in imageList) {
                        val photoFile = image.content
                        val photoFileName = image.name
                        imageDataList.add(photoFileName to photoFile)
                    }

                    val data = ReportDataSource.SubmitData(
                        title = title,
                        content = content,
                        address = address,
                        latitude = latitude,
                        longitude = longitude,
                        imageDataList = imageDataList
                    )
                    val result = reportDataSource.submitUserReport(data)
                    _eventSubmitReportObservable.postValue(Event(result))
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    _eventSubmitReportObservable.postValue(Event(Error.construct(ex)))
                } finally {
                    setLoading(isLoading = false)
                }
            }
        }
    }

}