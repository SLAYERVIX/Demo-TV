package com.example.demotv.ui.details

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.example.domain.entity.movie.Result

class DetailsPresenter : AbstractDetailsDescriptionPresenter() {
    override fun onBindDescription(vh: ViewHolder?, item: Any?) {
        val details = item as Result

        vh?.let {
            it.title.text = details?.title ?: ""
            it.subtitle.text = details?.overview ?: ""
        }
    }
}