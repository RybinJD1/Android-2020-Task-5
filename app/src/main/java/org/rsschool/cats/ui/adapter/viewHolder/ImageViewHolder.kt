package org.rsschool.cats.ui.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.rsschool.cats.R
import org.rsschool.cats.databinding.GridViewItemBinding
import org.rsschool.cats.getDefaultRequestOptions
import org.rsschool.cats.model.Image

class ImageViewHolder(private val binding: GridViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Image?) {
        item?.let {
            Glide.with(itemView.context)
                .load((it.url))
                .apply(
                    getDefaultRequestOptions()
                ).into(binding.image)
        } ?: views {
            image.setImageResource(R.drawable.ic_baseline_cloud_download_24)
        }
    }

    companion object {
        fun from(parent: ViewGroup) = GridViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let(::ImageViewHolder)
    }

    private fun <T> views(block: GridViewItemBinding.() -> T): T? = binding.block()
}
