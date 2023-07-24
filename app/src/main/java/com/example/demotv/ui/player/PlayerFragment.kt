package com.example.demotv.ui.player

import android.os.Bundle
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.PlaybackGlue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PlayerFragment : VideoSupportFragment() {

    private lateinit var playerAdapter: BasicMediaPlayerAdapter
    private lateinit var playerGlue: ControlGlue

    private val args: PlayerFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerAdapter = BasicMediaPlayerAdapter(requireContext())
        playerGlue = ControlGlue(requireContext(),playerAdapter)

        playerGlue.host = VideoSupportFragmentGlueHost(this)
        playerGlue.setupVideoData(args.movie.title,args.movie.overview)

        playerGlue.addPlayerCallback(object : PlaybackGlue.PlayerCallback() {
            override fun onPreparedStateChanged(glue: PlaybackGlue?) {
                super.onPreparedStateChanged(glue)
                glue?.let {
                    if (glue.isPrepared) {
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