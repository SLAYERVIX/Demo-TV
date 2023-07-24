package com.example.demotv.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ClassPresenterSelector
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.demotv.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : DetailsSupportFragment() {
    private lateinit var rowsAdapter: ArrayObjectAdapter

    private val viewModel: DetailsViewModel by viewModels()

    private val args: DetailsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildDetails()
    }

    private fun buildDetails() {

        val selector = ClassPresenterSelector().apply {
            // Attach your media item details presenter to the row presenter:
            FullWidthDetailsOverviewRowPresenter(DetailsPresenter()).also {
                addClassPresenter(DetailsOverviewRow::class.java, it)
            }
            addClassPresenter(ListRow::class.java, ListRowPresenter())
        }

        rowsAdapter = ArrayObjectAdapter(selector)

        val res = activity?.resources
        val detailsOverview = DetailsOverviewRow(args.movie).apply {

            // Add images and action buttons to the details view
            imageDrawable = res?.getDrawable(R.drawable.player)
            addAction(Action(1, "Watch Trailer"))


            setOnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->
                if (item is Action) {
                    lifecycleScope.launch {
                        val video = viewModel.getVideo(args.movie.id)
                        val youtubeVideos = video?.results?.filter { video ->
                            video.type == "Trailer" && video.site == "YouTube" && video.official
                        }
                        Log.d("rabbit", "buildDetails: ${youtubeVideos?.first()?.key!!}")
                        findNavController().navigate(
                            DetailsFragmentDirections.actionDetailsFragmentToVideoPlayerFragment(
                                youtubeVideos?.first()?.key!!,
                                args.movie
                            )
                        )
                    }
                }
            }


        }

        rowsAdapter.add(detailsOverview)
        adapter = rowsAdapter
    }
}