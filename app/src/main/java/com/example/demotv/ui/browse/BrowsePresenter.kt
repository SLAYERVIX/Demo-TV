package com.example.demotv.ui.browse

import android.view.ViewGroup
import androidx.leanback.widget.BaseCardView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.example.data.Constants
import com.example.demotv.R
import com.example.domain.entity.movie.Result

class BrowsePresenter : Presenter() {

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
       val movie = item as Result

        with(viewHolder?.view as ImageCardView) {
            Glide.with(mainImageView.context).load(Constants.IMAGE_URL + movie.poster_path).into(mainImageView)

            titleText = movie.title
            contentText = movie.release_date
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}
