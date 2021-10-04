package org.rsschool.cats.ui.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import org.rsschool.cats.databinding.ImageLoadStateFooterViewItemBinding

class ImageLoadStateViewHolder(
    private val binding: ImageLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        views {
            retryButton.setOnClickListener { retry.invoke() }
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            views {
                loadErrorMsg.text = loadState.error.localizedMessage
            }
        }
        views {
            loadProgressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState !is LoadState.Loading
            loadErrorMsg.isVisible = loadState !is LoadState.Loading
        }
    }

    companion object {
        fun from(parent: ViewGroup, retry: () -> Unit): ImageLoadStateViewHolder =
            ImageLoadStateFooterViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).let { ImageLoadStateViewHolder(it, retry) }
    }

    private fun <T> views(block: ImageLoadStateFooterViewItemBinding.() -> T): T? = binding.block()
}
