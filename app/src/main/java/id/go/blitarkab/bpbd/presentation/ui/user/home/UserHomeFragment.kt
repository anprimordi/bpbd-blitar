package id.go.blitarkab.bpbd.presentation.ui.user.home

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentUserHomeBinding
import id.go.blitarkab.bpbd.domain.model.*
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.ui.user.report.list.UserReportListListAdapter
import id.go.blitarkab.bpbd.presentation.util.DateTimeFormatter
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment
import id.go.blitarkab.bpbd.presentation.util.extensions.freshInsert
import id.go.blitarkab.bpbd.presentation.util.extensions.setImageRemoteSource

@AndroidEntryPoint
class UserHomeFragment :
    BaseFragment<FragmentUserHomeBinding, UserHomeContract.Presenter>(),
    UserHomeContract.View, OnMapReadyCallback {

    override val layoutResourceId: Int = R.layout.fragment_user_home
    override val presenter: UserHomeContract.Presenter by viewModels<UserHomeViewModel>()

    private val reportListAdapter = UserReportListListAdapter { openReportDetailPage(it) }

    private var googleMap: GoogleMap? = null

    private val weatherList = arrayListOf<Weather>()

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        binding.listReport.adapter = reportListAdapter

        binding.choiceWeatherArea.apply {
            threshold = 1000
            setOnItemClickListener { _, _, position, _ ->
                val weather = weatherList.getOrNull(position)
                if (weather != null) onWeatherSelected(weather)
            }
        }

        presenter.loadData()
        presenter.accountObservable.observe(viewLifecycleOwner) { fetchAccount(it) }
        presenter.dashboardInfoObservable.observe(viewLifecycleOwner) { fetchDashboardInfo(it) }
        presenter.reportListObservable.observe(viewLifecycleOwner) { fetchReportList(it) }
        presenter.weatherListObservable.observe(viewLifecycleOwner) { fetchWeatherList(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.apply {
            onCreate(savedInstanceState)
            onResume()
            MapsInitializer.initialize(requireContext().applicationContext)
            getMapAsync(this@UserHomeFragment)
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
                setAllGesturesEnabled(false)
            }
        }
    }

    override fun fetchAccount(result: Result<Account>) {
        if (result is Success) {
            val account = result.data
            binding.textName.text = account.fullName
            binding.imageProfile.setImageRemoteSource(
                srcRemote = account.photoProfileUrl,
                srcPlaceholderRes = R.drawable.ic_placeholder_account_24,
                srcErrorRes = R.drawable.ic_placeholder_account_24,
                roundedRadius = 8f
            )
        }
    }

    override fun fetchDashboardInfo(result: Result<Dashboard>) {
        if (result is Success) {
            val dashboard = result.data

            val covid = dashboard.covid
            binding.covid = covid

            val earthQuake = dashboard.earthQuake
            binding.textEarthQuakeDateTimeTitle.text =
                DateTimeFormatter.formatHomeEarthQuakeDate(earthQuake.datetime)
            binding.textEarthQuakeDateTimeContent.text =
                DateTimeFormatter.formatHomeEarthQuakeTime(earthQuake.datetime)
            binding.textEarthQuakeMagnitudeTitle.text = earthQuake.magnitude
            binding.textEarthQuakeLocationTitle.text = earthQuake.area

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
    }

    override fun fetchReportList(result: Result<List<Report>>) {
        if (result is Success) {
            val reportList = result.data
            reportListAdapter.submitList(reportList.sortedByDescending { it.datetime })
            binding.groupReport.isVisible = reportList.isNotEmpty()
        }
    }

    override fun fetchWeatherList(result: Result<List<Weather>>) {
        if (result is Success) {
            weatherList.freshInsert(result.data)
            binding.choiceWeatherArea.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.item_drop_down_menu,
                    weatherList
                )
            )

            val defaultWeather = weatherList.firstOrNull()
            if (defaultWeather != null) {
                binding.choiceWeatherArea.setText(defaultWeather.area)
                onWeatherSelected(defaultWeather)
            }
        }
    }

    override fun onWeatherSelected(weather: Weather) {
        binding.textTemperature.text = getString(R.string.text_temperature, weather.temperature)
        binding.textHumidity.text = getString(R.string.text_humidity, weather.humidity)
        binding.textWind.text = weather.wind
        binding.textToday.text = weather.weather
    }

    override fun openProfileDetailPage() {
        val direction =
            UserHomeFragmentDirections.actionUserHomeFragmentToUserProfileDetailFragment()
        findNavController().navigate(direction)
    }

    override fun openVolcanoPage() {
        val direction = UserHomeFragmentDirections.actionUserHomeFragmentToUserVolcanoFragment()
        findNavController().navigate(direction)
    }

    override fun openCovidPage() {
        val direction = UserHomeFragmentDirections.actionUserHomeFragmentToUserCovidFragment()
        findNavController().navigate(direction)
    }

    override fun openEarthQuakePage() {
        val direction = UserHomeFragmentDirections.actionUserHomeFragmentToUserEarthQuakeFragment()
        findNavController().navigate(direction)
    }

    override fun openSatellitePage() {
        val direction = UserHomeFragmentDirections.actionUserHomeFragmentToUserSatelliteFragment()
        findNavController().navigate(direction)
    }

    override fun openReportListPage() {
        val direction = UserHomeFragmentDirections.actionUserHomeFragmentToUserReportListFragment()
        findNavController().navigate(direction)
    }

    override fun openReportDetailPage(report: Report) {
        val direction = UserHomeFragmentDirections.actionUserHomeFragmentToUserReportDetailFragment(
            reportId = report.id,
            isHistory = false
        )
        findNavController().navigate(direction)
    }

    override fun openNewsPage(news: News) {
//        val direction = UserHomeFragmentDirections.actionUserHomeFragmentToUserNewsFragment(news)
//        findNavController().navigate(direction)
    }

}