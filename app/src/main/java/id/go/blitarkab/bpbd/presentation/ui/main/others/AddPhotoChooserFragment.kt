package id.go.blitarkab.bpbd.presentation.ui.main.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentAddPhotoChooserBinding
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.presentation.ui.main.others.AddPhotoChooserContract.Companion.BUNDLE_KEY_CHOOSER_TYPE
import id.go.blitarkab.bpbd.presentation.ui.main.others.AddPhotoChooserContract.Companion.REQUEST_GET_CHOOSER_TYPE

class AddPhotoChooserFragment : BottomSheetDialogFragment(), AddPhotoChooserContract.View {

    private lateinit var binding: FragmentAddPhotoChooserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_BPBD_User_BottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_photo_chooser, container, false
        )
        binding.view = this
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onCameraChosen() {
        setFragmentResult(REQUEST_GET_CHOOSER_TYPE, bundleOf(Pair(BUNDLE_KEY_CHOOSER_TYPE, true)))
        closePage()
    }

    override fun onGalleryChosen() {
        setFragmentResult(REQUEST_GET_CHOOSER_TYPE, bundleOf(Pair(BUNDLE_KEY_CHOOSER_TYPE, false)))
        closePage()
    }

    override fun <T> getErrorMessage(result: Error<T>): String = ""

    override fun <T> showErrorMessage(result: Error<T>, view: View) {}

    override fun showUnderDevelopmentMessage() {}

    override fun closePage() {
        findNavController().navigateUp()
    }

}