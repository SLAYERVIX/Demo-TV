package com.example.demotv

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.domain.entity.Video
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BrowseFragment : BrowseSupportFragment() {
    private lateinit var rowsAdapter: ArrayObjectAdapter
    private val viewModel: MainViewModel by viewModels()

    private val backgroundManager by lazy {
        BackgroundManager.getInstance(requireActivity()).apply {
            attach(requireActivity().window)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPopularVideos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUiElements()

        rowsAdapter = ArrayObjectAdapter(ListRowPresenter())

        adapter = rowsAdapter


        val listRowAdapter = ArrayObjectAdapter(CardPresenter())

        lifecycleScope.launch {
            viewModel.popularVideos.collect { popularVideos ->
                popularVideos?.let {
                    listRowAdapter.addAll(0, it.videos)
                    rowsAdapter.add(ListRow(HeaderItem(1, "Popular"), listRowAdapter))

                }
            }
        }

        setDynamicBackground()
    }

    private fun setupUiElements() {
        title = "Demo TV" // Change the title
        badgeDrawable =
            resources.getDrawable(R.drawable.player, requireContext().theme) // Change icon
        headersState = HEADERS_HIDDEN // Change the view state of the header
        isHeadersTransitionOnBackEnabled = true // Control the back pressed when header is shown

        onItemViewClickedListener =
            OnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->

            }

        brandColor =
            ContextCompat.getColor(requireContext(), R.color.black) // Set header background color


    }

    private fun setDynamicBackground() {
        setOnItemViewSelectedListener { itemViewHolder, item, _, _ ->
            if (itemViewHolder?.view != null) {
                val video = item as Video
                Glide.with(requireContext()).load(video.image).into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        backgroundManager.drawable = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // You can do cleanup here if needed.
                    }
                })
            }
        }
    }
}