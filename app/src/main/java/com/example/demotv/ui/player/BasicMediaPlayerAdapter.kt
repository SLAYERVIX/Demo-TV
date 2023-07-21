package com.example.demotv.ui.player

import android.content.Context
import android.net.Uri
import android.util.SparseArray
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackBannerControlGlue.ACTION_FAST_FORWARD
import androidx.leanback.media.PlaybackBannerControlGlue.ACTION_PLAY_PAUSE
import androidx.leanback.media.PlaybackBannerControlGlue.ACTION_REWIND
import androidx.leanback.media.PlaybackBannerControlGlue.ACTION_SKIP_TO_PREVIOUS
import com.maxrave.kotlinyoutubeextractor.State
import com.maxrave.kotlinyoutubeextractor.YTExtractor
import com.maxrave.kotlinyoutubeextractor.YtFile

class BasicMediaPlayerAdapter(private val context: Context) : MediaPlayerAdapter(context) {
    override fun next() {
        super.next()
    }

    override fun previous() {
        super.previous()
    }

    override fun fastForward() {
        seekTo(currentPosition + 10_000)
    }

    override fun rewind() {
        seekTo(currentPosition - 10_000)
    }

    override fun getSupportedActions(): Long {
        return (ACTION_SKIP_TO_PREVIOUS xor ACTION_REWIND xor
                ACTION_PLAY_PAUSE xor
                ACTION_FAST_FORWARD xor
                ACTION_SKIP_TO_PREVIOUS).toLong()
    }

    suspend fun loadVideo(id: String) {
        val yt = YTExtractor(con = context, CACHING = false, LOGGING = false, retryCount = 3)

        yt.extract(id)
        if (yt.state == State.SUCCESS) {
            var ytFiles: SparseArray<YtFile>? = yt.getYTFiles()
            val video = ytFiles?.get(22)

            setDataSource(Uri.parse(video?.url))
        }
    }
}