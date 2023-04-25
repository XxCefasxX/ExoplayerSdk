package com.cfsproj.exoplayer_sdk

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView

interface ExoplayerApi {
    fun initPlayer(bitRate: Int)
    fun setMediaSource(mediaSource: String, sourceType: SourceType)
    fun preparePlayer()
    fun startPlayer()
    fun pausePlayer()
    fun releasePlayer()

    fun addHeader(headerName: String, headerValue: String)
}