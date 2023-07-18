package com.example.demotv

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BrowseSupportFragment() {
    private lateinit var rowsAdapter: ArrayObjectAdapter
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPopularVideos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = "Demo TV"
        rowsAdapter = ArrayObjectAdapter(ListRowPresenter())

        adapter = rowsAdapter

        val listRowAdapter = ArrayObjectAdapter(CardPresenter())

        lifecycleScope.launch {
            viewModel.popularVideos.collect { popularVideos ->
                popularVideos?.let {
                    listRowAdapter.addAll(0, it.videos)
                    rowsAdapter.add(ListRow(HeaderItem(1,"Popular"), listRowAdapter))
                }
            }
        }
    }
}