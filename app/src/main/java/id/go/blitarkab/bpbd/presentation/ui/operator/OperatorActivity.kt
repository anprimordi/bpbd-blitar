package id.go.blitarkab.bpbd.presentation.ui.operator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.ActivityOperatorBinding
import id.go.blitarkab.bpbd.presentation.util.base.BaseActivity

@AndroidEntryPoint
class OperatorActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, OperatorActivity::class.java)
        }
    }

    private lateinit var binding: ActivityOperatorBinding
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_operator)
        binding.lifecycleOwner = this

        controller = findNavController(R.id.nav_host_operator)
        controller.addOnDestinationChangedListener(this)

        NavigationUI.setupWithNavController(binding.bottomNav, controller)

        Firebase.messaging.subscribeToTopic("operator_new_reports")
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
            R.id.operatorHomeFragment, R.id.operatorNewsFragment, R.id.operatorReportFragment, R.id.operatorGuideFragment, R.id.operatorProfileFragment -> {
                binding.bottomNav.isVisible = true
            }
            else -> {
                binding.bottomNav.isVisible = false
            }
        }
    }

}