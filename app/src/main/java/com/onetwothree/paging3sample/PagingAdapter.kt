package com.onetwothree.paging3sample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.onetwothree.paging3sample.databinding.ItemHeaderBinding
import com.onetwothree.paging3sample.databinding.ItemItemBinding

class PagingAdapter : PagingDataAdapter<DataModel, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<DataModel>() {
            override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
                return if (oldItem is DataModel.Header && newItem is DataModel.Header) {
                    oldItem.title == newItem.title
                } else if (oldItem is DataModel.Item && newItem is DataModel.Item) {
                    oldItem.title == newItem.title
                } else {
                    oldItem is DataModel.Separator && newItem is DataModel.Separator
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when (viewType) {
        DataType.ITEM.ordinal -> PagingItemViewHolder(parent)
        DataType.HEADER.ordinal -> PagingHeaderViewHolder(parent)
        else -> PagingSeparatorViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PagingItemViewHolder -> holder.bind(getItem(position) as DataModel.Item)
            is PagingHeaderViewHolder -> holder.bind(getItem(position) as DataModel.Header)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.type?.ordinal ?: DataType.ITEM.ordinal
    }

    class PagingItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
    ) {
        private val dataBinding = ItemItemBinding.bind(itemView)

        fun bind(data: DataModel.Item) {
            dataBinding.item = data
            dataBinding.executePendingBindings()
        }
    }

    class PagingHeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
    ) {
        private val dataBinding = ItemHeaderBinding.bind(itemView)

        fun bind(data: DataModel.Header) {
            dataBinding.item = data
            dataBinding.executePendingBindings()
        }
    }

    class PagingSeparatorViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_seperate, parent, false)
    )
}
