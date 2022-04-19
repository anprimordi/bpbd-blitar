package id.go.blitarkab.bpbd.presentation.ui.user.profile.update

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.esafirm.imagepicker.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.InvalidDataError
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import id.go.blitarkab.bpbd.presentation.util.extensions.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileUpdateViewModel @Inject constructor(
    @Repository private val accountDataSource: AccountDataSource
) : BaseViewModel(accountDataSource), UserProfileUpdateContract.Presenter {

    private val _photoProfileUrlObservable = MutableLiveData<String?>()
    override val photoProfileUrlObservable: LiveData<String?>
        get() = _photoProfileUrlObservable

    override val nameObservable = MutableLiveData<String>()
    override val emailObservable = MutableLiveData<String>()
    override val genderObservable = MutableLiveData<String>()
    override val nikObservable = MutableLiveData<String>()
    override val phoneObservable = MutableLiveData<String>()
    override val passwordObservable = MutableLiveData<String>()

    private val _eventUpdatePhotoObservable = MutableLiveData<Event<Result<Unit>>>()
    override val eventUpdatePhotoObservable: LiveData<Event<Result<Unit>>>
        get() = _eventUpdatePhotoObservable

    private val _eventUpdateProfileObservable = MutableLiveData<Event<Result<Unit>>>()
    override val eventUpdateProfileObservable: LiveData<Event<Result<Unit>>>
        get() = _eventUpdateProfileObservable

    override fun initData() {
        viewModelScope.launch {
            setLoading()
            val result = accountDataSource.getAccount()
            if (result is Success) {
                val account = result.data
                _photoProfileUrlObservable.postValue(account.photoProfileUrl)
                nameObservable.postValue(account.fullName)
                emailObservable.postValue(account.email)
                genderObservable.postValue(account.gender)
                nikObservable.postValue(account.nik)
                phoneObservable.postValue(account.phoneNumber)
            }
            setLoading(isLoading = false)
        }
    }

    override fun updatePhoto(context: Context, image: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                setLoading()
                val photoFile =
                    image.uri.toByteArray(context) ?: throw Exception("Gagal memuat foto profil")
                val photoFileName = image.name
                val result = accountDataSource.updateUserPhoto(
                    fileName = photoFileName,
                    fileContent = photoFile
                )
                if (result is Success) {
                    val currentAccount = getCurrentAccount()
                    if (currentAccount != null) {
                        val account = result.data.copy(token = currentAccount.token)
                        val saveResult = accountDataSource.saveAccount(account)
                        _eventUpdatePhotoObservable.postValue(Event(saveResult))
                    }
                } else {
                    _eventUpdatePhotoObservable.postValue(Event(result.map { }))
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                _eventUpdatePhotoObservable.postValue(Event(Error.construct(ex)))
            } finally {
                setLoading(isLoading = false)
            }
        }
    }

    override fun updateProfile() {
        val name = nameObservable.value
        val email = emailObservable.value
        val gender = genderObservable.value
        val nik = nikObservable.value
        val phone = phoneObservable.value
        val password = passwordObservable.value
        when {
            name.isNullOrBlank() -> _eventUpdateProfileObservable.postValue(
                Event(InvalidDataError(message = "Nama Lengkap"))
            )
            email.isNullOrBlank() -> _eventUpdateProfileObservable.postValue(
                Event(InvalidDataError(message = "Email"))
            )
            gender.isNullOrBlank() -> _eventUpdateProfileObservable.postValue(
                Event(InvalidDataError(message = "Jenis Kelamin"))
            )
            nik.isNullOrBlank() -> _eventUpdateProfileObservable.postValue(
                Event(InvalidDataError(message = "NIK"))
            )
            phone.isNullOrBlank() -> _eventUpdateProfileObservable.postValue(
                Event(InvalidDataError(message = "Nomor Telepon"))
            )
            password.isNullOrBlank() -> _eventUpdateProfileObservable.postValue(
                Event(InvalidDataError(message = "Password"))
            )
            else -> viewModelScope.launch {
                setLoading()
                val data = AccountDataSource.AccountData(
                    fullName = name,
                    email = email,
                    gender = gender,
                    nik = nik,
                    phoneNumber = phone,
                    password = password
                )
                val result = accountDataSource.updateUserProfile(data)
                if (result is Success) {
                    val currentAccount = getCurrentAccount()
                    if (currentAccount != null) {
                        val account = result.data.copy(token = currentAccount.token)
                        val saveResult = accountDataSource.saveAccount(account)
                        _eventUpdateProfileObservable.postValue(Event(saveResult))
                    }
                } else {
                    _eventUpdateProfileObservable.postValue(Event(result.map { }))
                }
                setLoading(isLoading = false)
            }
        }
    }

}