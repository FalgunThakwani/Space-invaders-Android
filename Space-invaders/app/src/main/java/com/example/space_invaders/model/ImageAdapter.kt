package com.example.space_invaders.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.space_invaders.R

/**
 * The image adapter
 * @param imageList The list of images
 * @constructor Create empty Image adapter
 */
class ImageAdapter(private val imageList: List<ImageItem>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    /**
     * The ViewHolder
     * @param itemView The item view
     * @property imageView The ImageView
     */
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    /**
     * Create the ViewHolder
     * @param parent The parent ViewGroup
     * @param viewType The view type
     * @return The ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(itemView)
    }

    /**
     * Bind the image to the ImageView
     * @param holder The ViewHolder
     * @param position The position of the item in the list
     */
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = imageList[position]
        holder.imageView.setImageResource(currentItem.imageResId)
    }

    /**
     * @return The size of the image list
     */
    override fun getItemCount(): Int {
        return imageList.size
    }
}