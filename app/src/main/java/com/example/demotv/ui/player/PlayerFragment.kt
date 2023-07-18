package com.example.demotv.ui.player

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.navigation.fragment.navArgs


class PlayerFragment : VideoSupportFragment() {
    val args : PlayerFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playerGlue = PlaybackTransportControlGlue(
            requireActivity(),
            MediaPlayerAdapter(requireActivity())
        )
        playerGlue.host = VideoSupportFragmentGlueHost(this)
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

        Log.d("rabbit", "onCreate: ${args.url}")
        playerGlue.playerAdapter.setDataSource(Uri.parse(args.url))
    }
}