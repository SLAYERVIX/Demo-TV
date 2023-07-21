package com.example.demotv.ui.player

import android.os.Bundle
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.widget.PlaybackSeekDataProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PlayerFragment : VideoSupportFragment() {
    private lateinit var playerAdapter: BasicMediaPlayerAdapter
    private val args: PlayerFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerAdapter = BasicMediaPlayerAdapter(requireContext())

        val playerGlue = ControlGlue(requireContext(),playerAdapter)

        playerGlue.title = args.movie.title
        playerGlue.subtitle = args.movie.overview

        playerGlue.host = VideoSupportFragmentGlueHost(this)

        playerGlue.addPlayerCallback(object : PlaybackGlue.PlayerCallback() {
            override fun onPreparedStateChanged(glue: PlaybackGlue?) {
                super.onPreparedStateChanged(glue)
                glue?.let {
                    if (glue.isPrepared) {
                        playerGlue.isSeekEnabled = true

                        playerGlue.seekProvider = PlaybackSeekDataProvider()
                        playerGlue.play()
                    }
                }
            }
        })

        loadVideo()
    }

    private fun loadVideo() {
        lifecycleScope.launch(Dispatchers.IO) {
            playerAdapter.loadVideo(args.key)
        }

    }
}