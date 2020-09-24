package com.lucasrodrigues.pokemonshowcase.components.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.lucasrodrigues.pokemonshowcase.components.adapter.view_holder.DataBindingViewHolder

class PagingLoadStateAdapter(
    private val loadingLayoutId: Int,
    private val errorLayoutId: Int,
    private val retry: () -> Unit
) : LoadStateAdapter<DataBindingViewHolder>() {

    override fun onBindViewHolder(holder: DataBindingViewHolder, loadState: LoadState) {
        holder.bind(
            item = if (loadState is LoadState.Error) loadState.error else null
        ) {
            retry()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): DataBindingViewHolder {
        val viewId = when (loadState) {
            is LoadState.Loading -> loadingLayoutId
            is LoadState.Error -> errorLayoutId
            else -> 0
        }

        return DataBindingViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewId, parent, false)
        )
    }
}