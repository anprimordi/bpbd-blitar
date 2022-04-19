package id.go.blitarkab.bpbd.presentation.util.comparator

import androidx.recyclerview.widget.DiffUtil

class SimpleComparator<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return false
    }
}