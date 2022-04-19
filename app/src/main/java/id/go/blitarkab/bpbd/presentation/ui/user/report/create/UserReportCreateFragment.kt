package id.go.blitarkab.bpbd.presentation.ui.user.report.create

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.stfalcon.imageviewer.StfalconImageViewer
import com.esafirm.imagepicker.features.ImagePickerLauncher
import com.esafirm.imagepicker.features.cameraonly.CameraOnlyConfig
import com.esafirm.imagepicker.features.registerImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.DialogConfirmationCreateReportBinding
import id.go.blitarkab.bpbd.databinding.FragmentUserReportCreateBinding
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.ui.main.others.AddPhotoChooserContract
import id.go.blitarkab.bpbd.presentation.ui.user.report.create.location.UserReportCreateLocationContract
import id.go.blitarkab.bpbd.presentation.util.AppImagePickerConfig
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.*

@AndroidEntryPoint
class UserReportCreateFragment :
    BaseFragment<FragmentUserReportCreateBinding, UserReportCreateContract.Presenter>(),
    UserReportCreateContract.View {

    override val layoutResourceId: Int = R.layout.fragment_user_report_create
    override val presenter: UserReportCreateContract.Presenter by viewModels<UserReportCreateViewModel>()

    private var imagePickerLauncher: ImagePickerLauncher? = null
    private var requestPermissionLauncher: ActivityResultLauncher<Array<String>>? = null

    private val imageList = arrayListOf<UserReportCreateContract.ReportImage>()
    private val imageListAdapter = UserReportCreateImageListAdapter(
        onImageClicked = { showImageFullScreenView(it) },
        onActionRemoveClicked = { presenter.removeImage(it) }
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        imagePickerLauncher = registerImagePicker { presenter.addImages(requireContext(), it) }
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                if (result.containsValue(false)) {
                    showActionDialog(
                        getString(R.string.msg_error_permission_camera),
                        R.string.action_close
                    ) { closePage() }
                }
            }
    }

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        binding.listImageReport.adapter = imageListAdapter
        binding.inputCoordinate.apply {
            setOnClickListener { openSelectLocationPage() }
            setEndIconOnClickListener { openSelectLocationPage() }
        }

        setFragmentResultListener(UserReportCreateLocationContract.REQUEST_GET_COORDINATE) { _, bundle ->
            val latitude = bundle.getDouble(
                UserReportCreateLocationContract.ARG_KEY_LATITUDE,
                0.0
            )
            val longitude = bundle.getDouble(
                UserReportCreateLocationContract.ARG_KEY_LONGITUDE,
                0.0
            )
            presenter.setCoordinate(latitude, longitude)
        }
        setFragmentResultListener(AddPhotoChooserContract.REQUEST_GET_CHOOSER_TYPE) { _, bundle ->
            val isCamera = bundle.getBoolean(AddPhotoChooserContract.BUNDLE_KEY_CHOOSER_TYPE, false)
            if (isCamera) openCameraPage()
            else openGalleryPage()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher?.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
            }
        }

        presenter.imageListObservable.observe(viewLifecycleOwner) {
            imageList.freshInsert(it)
            imageListAdapter.submitList(it)
        }
        presenter.eventSubmitReportObservable.observe(viewLifecycleOwner) { event ->
            event.getAvailableEvent()?.let { onSubmitReportFinished(it) }
        }
    }

    override fun submitReport() {
        val dialogBinding: DialogConfirmationCreateReportBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.dialog_confirmation_create_report,
            null,
            false
        )
        dialogBinding.lifecycleOwner = viewLifecycleOwner

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .show()

        dialogBinding.buttonNo.setOnClickListener { dialog.dismiss() }
        dialogBinding.buttonYes.setOnClickListener {
            dialog.dismiss()
            presenter.submitReport(requireContext())
        }
    }

    override fun onSubmitReportFinished(result: Result<Unit>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                showLongToast(R.string.msg_success_submit_report)
                closePage()
            }
            is Error -> {
                val errorMessage = getErrorMessage(result)
                showMessageDialog(errorMessage)
            }
        }
    }

    override fun showImageChooserSheet() {
        val direction =
            UserReportCreateFragmentDirections.actionUserReportCreateFragmentToAddPhotoChooserFragment()
        findNavController().navigate(direction)
    }

    override fun showImageFullScreenView(startPosition: Int) {
        StfalconImageViewer.Builder(requireContext(), imageList) { view, image ->
            view.setImageLocalSource(
                srcLocal = image.content,
                srcPlaceholderRes = R.drawable.img_bpbd_40,
                srcErrorRes = R.drawable.img_bpbd_40,
                targetScaleType = ImageView.ScaleType.CENTER_INSIDE
            )
        }.withStartPosition(startPosition).show()
    }

    override fun openSelectLocationPage() {
        val direction =
            UserReportCreateFragmentDirections.actionUserReportCreateFragmentToUserReportCreateLocationFragment()
        findNavController().navigate(direction)
    }

    override fun openCameraPage() {
        imagePickerLauncher?.launch(CameraOnlyConfig())
    }

    override fun openGalleryPage() {
        imagePickerLauncher?.launch(AppImagePickerConfig.DEFAULT)
    }

}