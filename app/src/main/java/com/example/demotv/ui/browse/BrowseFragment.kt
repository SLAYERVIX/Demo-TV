package com.example.demotv.ui.browse

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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.data.Constants
import com.example.demotv.R
import com.example.domain.entity.movie.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BrowseFragment : BrowseSupportFragment() {
    private lateinit var rowsAdapter: ArrayObjectAdapter
    private val viewModel: BrowseViewModel by viewModels()

    private val backgroundManager by lazy {
        BackgroundManager.getInstance(requireActivity()).apply {
            attach(requireActivity().window)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = rowsAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUiElements()
        loadData()
        setDynamicBackground()
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.getGenres()
            viewModel.genres.collect { genreResult ->
                genreResult?.genres?.forEach { genre ->
                    // Fetch movies for each genre one by one and wait for the result
                    val movies = viewModel.getMovies(genre.id)

                    // Create ArrayObjectAdapter for each genre
                    val listRowAdapter = ArrayObjectAdapter(BrowsePresenter())

                    // Add movies to the listRowAdapter
                    movies?.results?.forEach {
                        listRowAdapter.add(it)
                    }

                    // Add the row to the rowsAdapter
                    rowsAdapter.add(
                        ListRow(
                            HeaderItem(genre.id.toLong(), genre.name),
                            listRowAdapter
                        )
                    )
                }
            }
        }
    }

    private fun setupUiElements() {
        title = "Demo TV" // Change the title
        badgeDrawable =
            resources.getDrawable(R.drawable.player, requireContext().theme) // Change icon
        headersState = HEADERS_HIDDEN // Change the view state of the header
        isHeadersTransitionOnBackEnabled = true // Control the back pressed when header is shown

        onItemViewClickedListener =
            OnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->
                findNavController().navigate(
                    BrowseFragmentDirections.actionMainFragmentToDetailsFragment(
                        (item as Result)
                    )
                )
            }


        brandColor =
            ContextCompat.getColor(requireContext(), R.color.black) // Set header background color
    }

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