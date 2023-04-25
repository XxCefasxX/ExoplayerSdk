package com.cfsproj.exoplayer_sdk

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView

interface ExoplayerApi {
    fun initPlayer()
    fun setMediaSource(mediaURL: String, sourceType: SourceType)
    fun preparePlayer()
    fun startPlayer()
    fun pausePlayer()
    fun releasePlayer()
    fun forwardPlayer()
    fun rewindPlayer()
    fun setForwardRewind(forWard: Int, reWind: Int)
    fun setVideoSize(maxWidth: Int, maxHeight: Int)
    fun setBitRate(bitRate: Int)

    fun addHeader(headerName: String, headerValue: String)
}