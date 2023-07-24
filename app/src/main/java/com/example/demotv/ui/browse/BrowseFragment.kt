package com.example.demotv.ui.browse

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.SectionRow
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.data.Constants
import com.example.domain.entity.movie.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BrowseFragment : BrowseSupportFragment() {
    private lateinit var rowsAdapter: ArrayObjectAdapter
    private val viewModel: BrowseViewModel by viewModels()

    private val nowPlayingAdapter = ArrayObjectAdapter(BrowsePresenter())

    private val backgroundManager by lazy {
        BackgroundManager.getInstance(requireActivity()).apply {
            attach(requireActivity().window)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = rowsAdapter // Initialize the header adapter

        val menuItems = listOf(
            // Browse Sections
            SectionRow(HeaderItem(1, "Movies")),
            ListRow(HeaderItem(2, "Now Playing"), nowPlayingAdapter),
            ListRow(HeaderItem(3, "Popular"), nowPlayingAdapter),
            ListRow(HeaderItem(4, "Top Rated"), nowPlayingAdapter),
            ListRow(HeaderItem(2, "Upcoming"), nowPlayingAdapter),

//            ListRow(HeaderItem(3, "Upcoming Releases"),listRowAdapter),
//            ListRow(HeaderItem(4, "Top Rated"),listRowAdapter),
//            ListRow(HeaderItem(5, "Trending"),listRowAdapter),
//
//            DividerRow(),
//
//            // Settings or Account Section (Optional)
//            ListRow(HeaderItem(6, "Settings"),listRowAdapter), // or Account if needed
        )
        // Add the row to the rowsAdapter
        rowsAdapter.addAll(0, menuItems)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDynamicBackground()
        loadNowPlaying()
    }

    private fun loadNowPlaying() {
        lifecycleScope.launch {
            val data = viewModel.getNowPlaying()

            nowPlayingAdapter.addAll(0, data?.results)
            nowPlayingAdapter.notifyItemRangeChanged(0, nowPlayingAdapter.size())
        }
    }

//    private fun setupUiElements() {
//        title = "Demo TV" // Change the title
//        badgeDrawable =
//            resources.getDrawable(R.drawable.player, requireContext().theme) // Change icon
//        headersState = HEADERS_HIDDEN // Change the view state of the header
//        isHeadersTransitionOnBackEnabled = true // Control the back pressed when header is shown
//
//        onItemViewClickedListener =
//            OnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->
//                findNavController().navigate(
//                    BrowseFragmentDirections.actionMainFragmentToDetailsFragment(
//                        (item as Result)
//                    )
//                )
//            }
//
//
//        // brandColor =
//        //    ContextCompat.getColor(requireContext(), R.color.black) // Set header background color
//    }
//
    private fun setDynamicBackground() {
        setOnItemViewSelectedListener { itemViewHolder, item, _, _ ->
            if (itemViewHolder?.view != null) {
                val video = item as Result
                Glide.with(requireContext()).load(Constants.IMAGE_URL + video.backdrop_path)
                    .into(object : CustomTarget<Drawable>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
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