package id.go.blitarkab.bpbd.presentation.ui.user.profile.update

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esafirm.imagepicker.model.Image
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BasePresenter
import id.go.blitarkab.bpbd.presentation.util.base.BaseView

interface UserProfileUpdateContract {

    interface View : BaseView {
        fun onUpdatePhotoFinished(result: Result<Unit>)
        fun onUpdateProfileFinished(result: Result<Unit>)
        fun openImagePickerPage()
    }

    interface Presenter : BasePresenter {
        val photoProfileUrlObservable: LiveData<String?>
        val nameObservable: MutableLiveData<String>
        val emailObservable: MutableLiveData<String>
        val genderObservable: MutableLiveData<String>
        val nikObservable: MutableLiveData<String>
        val phoneObservable: MutableLiveData<String>
        val passwordObservable: MutableLiveData<String>

        val eventUpdatePhotoObservable: LiveData<Event<Result<Unit>>>
        val eventUpdateProfileObservable: LiveData<Event<Result<Unit>>>

        fun initData()
        fun updatePhoto(context: Context, image: Image)
        fun updateProfile()
    }

}