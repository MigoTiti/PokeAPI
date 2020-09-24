package com.lucasrodrigues.pokemonshowcase.components.adapter.view_holder

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Any? = null, onClick: View.OnClickListener? = null) {
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.onClick, onClick)
        binding.executePendingBindings()
    }

    fun clearAnimation() {
        binding.root.clearAnimation()
    }
}