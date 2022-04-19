package id.go.blitarkab.bpbd.presentation.ui.main.landing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.go.blitarkab.bpbd.R
import id.go.blitarkab.bpbd.databinding.ItemLandingIndicatorBinding
import id.go.blitarkab.bpbd.presentation.ui.main.landing.LandingContract.ContentIndicator
import id.go.blitarkab.bpbd.presentation.util.extensions.colorStateListFromAttrOf

class LandingContentIndicatorListAdapter :
    ListAdapter<ContentIndicator, LandingContentIndicatorListAdapter.ViewHolder>(Comparator) {

    inner class ViewHolder(
        val binding: ItemLandingIndicatorBinding
    ) : RecyclerView.ViewHolder(binding.root)

    private object Comparator : DiffUtil.ItemCallback<ContentIndicator>() {
        override fun areItemsTheSame(
            oldItem: ContentIndicator,
            newItem: ContentIndicator
        ): Boolean {
            return oldItem.position == newItem.position
        }

        override fun areContentsTheSame(
            oldItem: ContentIndicator,
            newItem: ContentIndicator
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_landing_indicator,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)
        holder.binding.imageIndicator.imageTintList = context.colorStateListFromAttrOf(
            if (item.isSelected) R.attr.colorSecondary
            else R.attr.colorIndicator
        )
    }

}