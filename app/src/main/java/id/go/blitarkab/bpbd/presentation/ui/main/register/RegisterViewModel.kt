package id.go.blitarkab.bpbd.presentation.ui.main.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.go.blitarkab.bpbd.di.datasource.Repository
import id.go.blitarkab.bpbd.domain.datasource.AccountDataSource
import id.go.blitarkab.bpbd.domain.model.Account
import id.go.blitarkab.bpbd.domain.model.common.InvalidDataError
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.util.Event
import id.go.blitarkab.bpbd.presentation.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @Repository private val accountDataSource: AccountDataSource
) : BaseViewModel(accountDataSource), RegisterContract.Presenter {

    override val nameObservable = MutableLiveData<String>()
    override val emailObservable = MutableLiveData<String>()
    override val genderObservable = MutableLiveData<String>()
    override val nikObservable = MutableLiveData<String>()
    override val phoneObservable = MutableLiveData<String>()
    override val passwordObservable = MutableLiveData<String>()

    private val _eventRegisterObservable = MutableLiveData<Event<Result<Account>>>()
    override val eventRegisterObservable: LiveData<Event<Result<Account>>>
        get() = _eventRegisterObservable

    override fun register() {
        val name = nameObservable.value
        val email = emailObservable.value
        val gender = genderObservable.value
        val nik = nikObservable.value
        val phone = phoneObservable.value
        val password = passwordObservable.value
        when {
            name.isNullOrBlank() -> _eventRegisterObservable.postValue(
                Event(InvalidDataError(message = "Nama Lengkap"))
            )
            email.isNullOrBlank() -> _eventRegisterObservable.postValue(
                Event(InvalidDataError(message = "Email"))
            )
            gender.isNullOrBlank() -> _eventRegisterObservable.postValue(
                Event(InvalidDataError(message = "Jenis Kelamin"))
            )
            nik.isNullOrBlank() -> _eventRegisterObservable.postValue(
                Event(InvalidDataError(message = "NIK"))
            )
            phone.isNullOrBlank() -> _eventRegisterObservable.postValue(
                Event(InvalidDataError(message = "Nomor Telepon"))
            )
            password.isNullOrBlank() -> _eventRegisterObservable.postValue(
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
                val result = accountDataSource.register(data)
                if (result is Success) {
                    val account = result.data
                    val saveResult = accountDataSource.saveAccount(account)
                    _eventRegisterObservable.postValue(Event(saveResult.map { account }))
                } else {
                    _eventRegisterObservable.postValue(Event(result))
                }
                setLoading(isLoading = false)
            }
        }
    }

}