package id.go.blitarkab.bpbd.presentation.ui.user.satellite

import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentUserSatelliteBinding
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment

@AndroidEntryPoint
class UserSatelliteFragment :
    BaseFragment<FragmentUserSatelliteBinding, UserSatelliteContract.Presenter>(),
    UserSatelliteContract.View {

    override val layoutResourceId: Int = R.layout.fragment_user_satellite
    override val presenter: UserSatelliteContract.Presenter by viewModels<UserSatelliteViewModel>()

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter

        val satelliteUrl = "https://inderaja.bmkg.go.id/IMAGE/HIMA/H08_EH_Jatim.png"
        Glide.with(requireContext())
            .load(satelliteUrl)
            .into(binding.imageSatellite)
    }

}