package id.go.blitarkab.bpbd.presentation.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.ActivityUserBinding
import id.go.blitarkab.bpbd.presentation.util.base.BaseActivity

@AndroidEntryPoint
class UserActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, UserActivity::class.java)
        }
    }

    private lateinit var binding: ActivityUserBinding
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user)
        binding.lifecycleOwner = this

        controller = findNavController(R.id.nav_host_user)
        controller.addOnDestinationChangedListener(this)

        NavigationUI.setupWithNavController(binding.bottomNav, controller)

        binding.fabCreateReport.setOnClickListener {
            controller.navigate(R.id.action_global_userReportCreateFragment)
        }
    }

    override fun onDestroy() {
        controller.removeOnDestinationChangedListener(this)
        super.onDestroy()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.userHomeFragment, R.id.userNewsListFragment, R.id.userReportFragment, R.id.userGuideFragment, R.id.userProfileFragment -> {
                binding.bottomNav.isVisible = true
                binding.fabCreateReport.isVisible = true
            }
            else -> {
                binding.bottomNav.isVisible = false
                binding.fabCreateReport.isVisible = false
            }
        }
    }

}