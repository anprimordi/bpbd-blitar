package id.go.blitarkab.bpbd.presentation.ui.user.volcano

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.ItemVolcanoBinding
import id.go.blitarkab.bpbd.domain.model.Volcano
import id.go.blitarkab.bpbd.presentation.util.comparator.ModelComparator

class UserVolcanoListAdapter :
    ListAdapter<Volcano, UserVolcanoListAdapter.ViewHolder>(ModelComparator<Volcano>()) {

    inner class ViewHolder(val binding: ItemVolcanoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_volcano,
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