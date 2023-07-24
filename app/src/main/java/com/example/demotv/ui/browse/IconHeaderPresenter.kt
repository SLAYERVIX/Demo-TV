package com.example.demotv.ui.browse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.RowHeaderPresenter
import com.example.demotv.R

class IconHeaderPresenter : Presenter() {
        override fun onCreateViewHolder(viewGroup: ViewGroup): RowHeaderPresenter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).run {
            inflate(R.layout.icon_header_item, null)
        }

        return RowHeaderPresenter.ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        if (item is ListRow) {
            val headerItem = item.headerItem
            val rootView = viewHolder?.view
            rootView?.focusable = View.FOCUSABLE

            rootView?.findViewById<ImageView>(R.id.header_icon).apply {
                rootView?.resources?.getDrawable(R.drawable.ic_launcher_background, null).also { icon ->
                    this?.setImageDrawable(icon)
                }
            }

            rootView?.findViewById<TextView>(R.id.header_label)?.apply {
                text = headerItem.name
            }
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}