package id.go.blitarkab.bpbd.presentation.ui.user.report.create.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentUserReportCreateLocationBinding
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.showActionDialog
import id.go.blitarkab.bpbd.presentation.util.extensions.showMessageDialog

@AndroidEntryPoint
class UserReportCreateLocationFragment :
    BaseFragment<FragmentUserReportCreateLocationBinding, UserReportCreateLocationContract.Presenter>(),
    UserReportCreateLocationContract.View, OnMapReadyCallback {

    override val layoutResourceId: Int = R.layout.fragment_user_report_create_location
    override val presenter: UserReportCreateLocationContract.Presenter by viewModels<UserReportCreateLocationViewModel>()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    private var googleMap: GoogleMap? = null
    private var selectedLatLng: LatLng? = null

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

        binding.buttonSelect.setOnClickListener {
            val lat = selectedLatLng?.latitude
            val long = selectedLatLng?.longitude
            if (lat != null && long != null) {
                setFragmentResult(
                    UserReportCreateLocationContract.REQUEST_GET_COORDINATE,
                    bundleOf(
                        Pair(UserReportCreateLocationContract.ARG_KEY_LATITUDE, lat),
                        Pair(UserReportCreateLocationContract.ARG_KEY_LONGITUDE, long),
                    )
                )
                closePage()
            } else {
                showMessageDialog(R.string.msg_warning_select_location)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.apply {
            onCreate(savedInstanceState)
            onResume()
            MapsInitializer.initialize(requireContext().applicationContext)
            getMapAsync(this@UserReportCreateLocationFragment)
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
                isRotateGesturesEnabled = false
                isZoomGesturesEnabled = true

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
            setOnCameraIdleListener {
                selectedLatLng = cameraPosition.target
            }
        }

        val latLng = LatLng(-8.12874372612372, 112.21377007142904)
        val cameraPosition = CameraPosition.builder()
            .target(latLng)
            .zoom(10f)
            .bearing(0f)
            .tilt(0f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        googleMap?.animateCamera(cameraUpdate)
    }

}