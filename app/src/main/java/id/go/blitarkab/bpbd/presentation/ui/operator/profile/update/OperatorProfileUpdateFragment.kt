package id.go.blitarkab.bpbd.presentation.ui.operator.profile.update

import android.content.Context
import androidx.fragment.app.viewModels
import com.esafirm.imagepicker.features.ImagePickerLauncher
import com.esafirm.imagepicker.features.registerImagePicker
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentOperatorProfileUpdateBinding
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.util.AppImagePickerConfig
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.showLongSnackbar
import id.go.blitarkab.bpbd.presentation.util.extensions.showMessageDialog

@AndroidEntryPoint
class OperatorProfileUpdateFragment :
    BaseFragment<FragmentOperatorProfileUpdateBinding, OperatorProfileUpdateContract.Presenter>(),
    OperatorProfileUpdateContract.View {

    override val layoutResourceId: Int = R.layout.fragment_operator_profile_update
    override val presenter: OperatorProfileUpdateContract.Presenter by viewModels<OperatorProfileUpdateViewModel>()

    private lateinit var imagePickerLauncher: ImagePickerLauncher

    override fun onAttach(context: Context) {
        super.onAttach(context)
        imagePickerLauncher = registerImagePicker { results ->
            val image = results.getOrNull(index = 0)
            if (image != null) presenter.updatePhoto(requireContext(), image)
        }
    }

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter

        /*binding.choiceGender.apply {
            threshold = 1000
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.item_drop_down_menu,
                    resources.getStringArray(R.array.gender_list)
                )
            )
        }*/

        presenter.initData()
        presenter.eventUpdatePhotoObservable.observe(viewLifecycleOwner) { event ->
            event.getAvailableEvent()?.let { onUpdatePhotoFinished(it) }
        }
        presenter.eventUpdateProfileObservable.observe(viewLifecycleOwner) { event ->
            event.getAvailableEvent()?.let { onUpdateProfileFinished(it) }
        }
    }

    override fun onUpdatePhotoFinished(result: Result<Unit>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                showLongSnackbar(binding.root, R.string.msg_success_update_photo_profile)
                presenter.initData()
            }
            is Error -> showMessageDialog(result.message)
        }
    }

    override fun onUpdateProfileFinished(result: Result<Unit>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                showLongSnackbar(binding.root, R.string.msg_success_update_profile)
                closePage()
            }
            is Error -> {
                val errorMessage = getErrorMessage(result)
                showMessageDialog(errorMessage)
            }
        }
    }

    override fun openImagePickerPage() {
        imagePickerLauncher.launch(AppImagePickerConfig.DEFAULT)
    }

}