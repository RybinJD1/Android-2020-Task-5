package org.rsschool.cats.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import org.rsschool.cats.model.Image
import org.rsschool.cats.ui.adapter.viewHolder.ImageViewHolder

class ImagesAdapter(private val onClickListener: OnClickListener) :
    PagingDataAdapter<Image, ImageViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder.from(parent)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.itemView.setOnClickListener {
            image?.let { image -> onClickListener.onClick(image) }
        }
        holder.bind(image)
    }

    class OnClickListener(private val clickListener: (image: Image) -> Unit) {
        fun onClick(image: Image) = clickListener(image)
    }

    private object DiffCallBack : DiffUtil.ItemCallback<Image>() {

        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem == newItem
    }
}
