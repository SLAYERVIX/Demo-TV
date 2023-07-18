package com.example.demotv

import android.view.ViewGroup
import androidx.leanback.widget.BaseCardView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.example.domain.entity.Video

class CardPresenter : Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val imageCardView = ImageCardView(parent?.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true
            cardType = ImageCardView.CARD_TYPE_INFO_UNDER

            with(mainImageView) {
                val posterWidth = parent?.resources?.getDimension(R.dimen.poster_width)?.toInt()
                val posterHeight = parent?.resources?.getDimension(R.dimen.poster_height)?.toInt()
                layoutParams = BaseCardView.LayoutParams(posterWidth!!, posterHeight!!)
            }

        }

        return ViewHolder(imageCardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val video = item as Video

        with(viewHolder?.view as ImageCardView) {
            Glide.with(mainImageView.context).load(video.image).into(mainImageView)

            titleText = video.user.name
            contentText = video.duration.toString() +  " Minutes"
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}
