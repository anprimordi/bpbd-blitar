package id.go.blitarkab.bpbd.presentation.ui.user.earthquake

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
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentUserEarthQuakeBinding
import id.go.blitarkab.bpbd.domain.model.EarthQuake
import id.go.blitarkab.bpbd.domain.model.common.Error
import id.go.blitarkab.bpbd.domain.model.common.Loading
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.showActionDialog
import id.go.blitarkab.bpbd.presentation.util.extensions.showLongSnackbar

@AndroidEntryPoint
class UserEarthQuakeFragment :
    BaseFragment<FragmentUserEarthQuakeBinding, UserEarthQuakeContract.Presenter>(),
    UserEarthQuakeContract.View, OnMapReadyCallback {

    override val layoutResourceId: Int = R.layout.fragment_user_earth_quake
    override val presenter: UserEarthQuakeContract.Presenter by viewModels<UserEarthQuakeViewModel>()

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

        presenter.loadData()
        presenter.earthQuakeDetailObservable.observe(viewLifecycleOwner) { fetchEarthQuake(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.apply {
            onCreate(savedInstanceState)
            onResume()
            MapsInitializer.initialize(requireContext().applicationContext)
            getMapAsync(this@UserEarthQuakeFragment)
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
    }

    override fun fetchEarthQuake(result: Result<EarthQuake>) {
        when (result) {
            is Loading -> {
            }
            is Success -> {
                val earthQuake = result.data
                binding.earthQuake = earthQuake

                val latLng = LatLng(earthQuake.latitude, earthQuake.longitude)
                val cameraPosition = CameraPosition.builder()
                    .target(latLng)
                    .zoom(8f)
                    .bearing(0f)
                    .tilt(0f)
                    .build()
                val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
                googleMap?.addMarker(MarkerOptions().position(latLng))
                googleMap?.animateCamera(cameraUpdate)
            }
            is Error -> showLongSnackbar(binding.root, result.message)
        }
    }

}