package com.onetwothree.paging3sample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.onetwothree.paging3sample.databinding.ItemLoadingBinding

class LoadStateViewHolder(parent: ViewGroup, private val retry: () -> Unit) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
    ) {

    private val dataBinding = ItemLoadingBinding.bind(itemView)

    fun bind(loadState: LoadState) {

        dataBinding.retryButton.setOnClickListener { retry() }
        dataBinding.isLoading = loadState is LoadState.Loading
        dataBinding.isError = loadState is LoadState.Error
        dataBinding.errorMessage = (loadState as? LoadState.Error)?.error?.message ?: ""
        dataBinding.executePendingBindings()
    }
}