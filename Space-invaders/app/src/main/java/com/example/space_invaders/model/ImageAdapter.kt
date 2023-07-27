package com.example.space_invaders.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.space_invaders.R

// Class for adapter to bind image data to RecyclerView items
class ImageAdapter(private val imageList: List<ImageItem>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    // Inner class for ViewHolder
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    // Inflate item layout and return ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(itemView)
    }

    // Bind image data to ViewHolder
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = imageList[position]
        holder.imageView.setImageResource(currentItem.imageResId)
    }

    // Return the size of the image list
    override fun getItemCount(): Int {
        return imageList.size
    }
}