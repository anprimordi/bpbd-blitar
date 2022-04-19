package id.go.blitarkab.bpbd.presentation.ui.user.covid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.ItemCovidBinding
import id.go.blitarkab.bpbd.domain.model.Covid
import id.go.blitarkab.bpbd.presentation.util.comparator.ModelComparator

class UserCovidListAdapter : ListAdapter<Covid, UserCovidListAdapter.ViewHolder>(ModelComparator()) {

    inner class ViewHolder(val binding: ItemCovidBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_covid,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item
    }

}