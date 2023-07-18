package com.example.demotv

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.example.domain.entity.Video

class CardPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {

        val view = LayoutInflater.from(parent?.context).inflate(R.layout.row_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val video = item as Video
        val itemView = viewHolder?.view

        // Find the views by their IDs in the layout
        val itemImage = itemView?.findViewById<ImageView>(R.id.item_image)
        val itemTitle = itemView?.findViewById<TextView>(R.id.item_title)
        val itemDescription = itemView?.findViewById<TextView>(R.id.item_description)

        // Set the image and text values directly
        if (itemView?.context != null) {
            Glide.with(itemView.context)
                .load(video.image) // Replace 'imageUrl' with the actual property from the Video object that holds the image URL or resource ID.
                .centerCrop()
                .into(itemImage!!)
        }

        itemTitle?.text = video.id.toString()
        itemDescription?.text = video.duration.toString()

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}
