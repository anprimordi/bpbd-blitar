package id.go.blitarkab.bpbd.presentation.ui.operator.report.detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.stfalcon.imageviewer.StfalconImageViewer
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentOperatorReportDetailBinding
import id.go.blitarkab.bpbd.domain.model.Report
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.ui.user.report.detail.UserReportDetailListAdapter
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.*

@AndroidEntryPoint
class OperatorReportDetailFragment :
    BaseFragment<FragmentOperatorReportDetailBinding, OperatorReportDetailContract.Presenter>(),
    OperatorReportDetailContract.View, OnMapReadyCallback {

    override val layoutResourceId: Int = R.layout.fragment_operator_report_detail
    override val presenter: OperatorReportDetailContract.Presenter by viewModels<OperatorReportDetailViewModel>()

    private val safeArgs: OperatorReportDetailFragmentArgs by navArgs()
    private val reportId: Long by lazy { safeArgs.reportId }
    private val isReadOnly: Boolean by lazy { safeArgs.readOnly }

    private var currentReport: Report? = null
    private val imageList = arrayListOf<String>()
    private val imageListAdapter = UserReportDetailListAdapter { showImageFullScreenView(it) }

    private var googleMap: GoogleMap? = null

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    @SuppressLint("MissingPermission")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                if (result.containsValue(false)) {
                    showActionDialog(
                        getString(R.string.msg_error_permission_location),
                        R.string.action_close
                    ) { closePage() }
                } else {
                    googleMap?.apply {
                        isMyLocationEnabled = true
                        uiSettings.isMyLocationButtonEnabled = true
                    }
                }
            }
    }

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        binding.isReadOnly = !isReadOnly
        binding.listImageReport.adapter = imageListAdapter

        loadReport()
        presenter.reportDetailObservable.observe(viewLifecycleOwner) { fetchReport(it) }
        presenter.eventAcceptReportFinished.observe(viewLifecycleOwner) { event ->
            event.getAvailableEvent()?.let { onAcceptReportFinished(it) }
        }
        presenter.eventRejectReportFinished.observe(viewLifecycleOwner) { event ->
            event.getAvailableEvent()?.let { onRejectReportFinished(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.apply {
            onCreate(savedInstanceState)
            onResume()
            MapsInitializer.initialize(requireContext().applicationContext)
            getMapAsync(this@OperatorReportDetailFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        binding.mapView.onDestroy()
        super.onDestroy()
    }

    override fun onMapReady(gmap: GoogleMap) {
        googleMap = gmap.apply {
            uiSettings.apply {
                isMapToolbarEnabled = true

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (
                        ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        isMyLocationEnabled = true
                        isMyLocationButtonEnabled = true
                    } else {
                        requestPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                } else {
                    isMyLocationEnabled = true
                    isMyLocationButtonEnabled = true
                }
            }
        }
    }

    override fun loadReport() {
        presenter.loadData(reportId)
    }

    override fun fetchReport(result: Result<Report>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                val report = result.data
                currentReport = report
                binding.report = report
                binding.textReportStatus.apply {
                    setText(report.status.getDisplayNameRes())
                    setTextColor(colorAttrOf(report.status.getDisplayColorRes()))
                }
                binding.textReportStatusOperator.text = when (report.status) {
                    Report.Status.WAITING -> ""
                    Report.Status.ACCEPTED -> getString(
                        R.string.text_approved_by,
                        report.operator.fullName
                    )
                    Report.Status.REJECTED -> getString(
                        R.string.text_rejected_by,
                        report.operator.fullName
                    )
                }

                val isActionButtonShown = !isReadOnly && report.status == Report.Status.WAITING
                binding.buttonAccept.isVisible = isActionButtonShown
                binding.buttonReject.isVisible = isActionButtonShown

                val latLng = LatLng(report.latitude, report.longitude)
                val cameraPosition = CameraPosition.builder()
                    .target(latLng)
                    .zoom(12f)
                    .bearing(0f)
                    .tilt(0f)
                    .build()
                val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
                googleMap?.addMarker(MarkerOptions().position(latLng))
                googleMap?.animateCamera(cameraUpdate)

                imageList.freshInsert(report.imageUrls)
                imageListAdapter.submitList(imageList)
            }
            is Error -> showLongSnackbar(binding.root, result.message)
        }
    }

    override fun onAcceptReportFinished(result: Result<Unit>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                showToast(R.string.msg_success_accept_report)
                loadReport()
            }
            is Error -> showMessageDialog(result.message)
        }
    }

    override fun onRejectReportFinished(result: Result<Unit>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                showToast(R.string.msg_success_reject_report)
                loadReport()
            }
            is Error -> showMessageDialog(result.message)
        }
    }

    override fun showAcceptReportConfirmationDialog() {
        showConfirmationDialog(R.string.msg_confirmation_accept_report) {
            presenter.acceptReport(reportId)
        }
    }

    override fun showRejectReportConfirmationDialog() {
        showConfirmationDialog(R.string.msg_confirmation_reject_report) {
            presenter.rejectReport(reportId)
        }
    }

    override fun showImageFullScreenView(startPosition: Int) {
        StfalconImageViewer.Builder(requireContext(), imageList) { view, url ->
            view.setImageRemoteSource(
                srcRemote = url,
                srcPlaceholderRes = R.drawable.img_bpbd_64,
                srcErrorRes = R.drawable.img_bpbd_64,
                targetScaleType = ImageView.ScaleType.CENTER_INSIDE
            )
        }.withStartPosition(startPosition).show()
    }

    override fun copyToClipboard() {
        currentReport?.user?.phoneNumber?.let { phoneNumber ->
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("label_bpbd_phone_number", phoneNumber)
            clipboard.setPrimaryClip(clip)
            showToast(R.string.msg_success_copied_to_clipboard)
        }
    }

    override fun openDialPhonePage() {
        currentReport?.user?.phoneNumber?.let { phoneNumber ->
            val dialUri = "tel:$phoneNumber".toUri()
            startActivity(Intent(Intent.ACTION_VIEW, dialUri))
        }
    }

}