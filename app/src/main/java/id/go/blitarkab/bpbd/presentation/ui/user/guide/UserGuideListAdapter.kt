package id.go.blitarkab.bpbd.presentation.ui.user.guide

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.ItemGuideBinding
import id.go.blitarkab.bpbd.domain.model.Guide
import id.go.blitarkab.bpbd.presentation.util.comparator.SimpleComparator

class UserGuideListAdapter(
    private val onItemClicked: (Guide) -> Unit
) : ListAdapter<Guide, UserGuideListAdapter.ViewHolder>(SimpleComparator<Guide>()) {

    inner class ViewHolder(val binding: ItemGuideBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_guide,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item
        holder.binding.imageGuide.setImageResource(item.iconRes)
        holder.binding.cardItem.setOnClickListener { onItemClicked(item) }
    }

}