package id.go.blitarkab.bpbd.presentation.ui.user.covid

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.FragmentUserCovidBinding
import id.go.blitarkab.bpbd.domain.model.Covid
import id.go.blitarkab.bpbd.domain.model.common.Result
import id.go.blitarkab.bpbd.domain.model.common.Success
import id.go.blitarkab.bpbd.presentation.util.base.BaseFragment

@AndroidEntryPoint
class UserCovidFragment :
    BaseFragment<FragmentUserCovidBinding, UserCovidContract.Presenter>(),
    UserCovidContract.View {

    override val layoutResourceId: Int = R.layout.fragment_user_covid
    override val presenter: UserCovidContract.Presenter by viewModels<UserCovidViewModel>()

    private val covidListAdapter = UserCovidListAdapter()

    override fun onInitialize() {
        binding.view = this
        binding.presenter = presenter
        binding.listCovid.adapter = covidListAdapter

        presenter.loadData()
        presenter.covidDetailObservable.observe(viewLifecycleOwner) { fetchCovidDetail(it) }
    }

    override fun fetchCovidDetail(result: Result<Pair<Covid, List<Covid>>>) {
        if (result is Success) {
            val covidInfo = result.data
            binding.countryCovid = covidInfo.first
            covidListAdapter.submitList(covidInfo.second)
        }
    }

}