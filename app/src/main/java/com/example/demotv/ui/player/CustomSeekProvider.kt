package com.example.demotv.ui.player

import androidx.leanback.widget.PlaybackSeekDataProvider

class CustomSeekProvider : PlaybackSeekDataProvider() {
    override fun getThumbnail(index: Int, callback: ResultCallback?) {
        super.getThumbnail(index, callback)
    }

    override fun getSeekPositions(): LongArray {
        return longArrayOf(0, 10000, 20000, 30000, 40000)
    }

    override fun reset() {
        super.reset()
    }
}