package com.cfsproj.exoplayer_sdk

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.MimeTypes


class ExoplayerApiImpl(private val context: Context) : ExoplayerApi {

    lateinit var player: ExoPlayer



    private var headers = mutableMapOf<String, String>()

    private var _bitRate: Int = 10000//10 MB
    val bitRate: Int = _bitRate

    private var _forWard: Int = 10000//10 seconds
    val forWard: Int = _forWard

    private var _reWind: Int = 10000//10 seconds
    val rewind: Int = _reWind

    private var _maxWidth: Int = 720
    val maxWidth: Int = _maxWidth

    private var _maxHeight: Int = 720
    val maxHeight: Int = _maxHeight

    override fun initPlayer() {


        val trackSelector = DefaultTrackSelector(context)


        trackSelector.buildUponParameters()
            .setMaxVideoSize(maxWidth, maxHeight)
            .setMaxVideoBitrate(bitRate)
            .setForceHighestSupportedBitrate(true)
            .build()

        // Initialize the player
        player = ExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .build()

    }


    override fun setMediaSource(mediaURL: String, sourceType: SourceType) {

        val mediaItem = MediaItem
            .Builder()
            .setUri(Uri.parse(mediaURL))
            .setMimeType(MimeTypes.BASE_TYPE_VIDEO)
            .setDrmLicenseRequestHeaders(headers)
            .build()


        val mediaSource =
            when (sourceType) {
                SourceType.ProgressiveMediaSource -> ProgressiveMediaSource.Factory(
                    DefaultDataSourceFactory(context, "user-agent")
                ).createMediaSource(mediaItem)

                SourceType.HlsMediaSource -> HlsMediaSource.Factory(
                    DefaultDataSourceFactory(
                        context,
                        "HLS App"
                    )
                )
                    .createMediaSource(mediaItem)
            }

        player.setMediaSource(mediaSource)
    }

    override fun preparePlayer() {
        // Prepare the player for playback
        player.prepare()
    }

    override fun startPlayer() {
        // Start playing the media
        player.playWhenReady = true
        player.play()
    }

    override fun pausePlayer() {
        // Pause the player
        player.playWhenReady = false

    }

    override fun releasePlayer() {
        // Release the player
        player.release()
    }

    override fun rewindPlayer() {
        player.seekTo(player.currentPosition - rewind)
    }

    override fun forwardPlayer() {
        player.seekTo(player.currentPosition + forWard)
    }

    override fun addHeader(headerName: String, headerValue: String) {
        headers[headerName] = headerValue
    }

    override fun setForwardRewind(forWard: Int, reWind: Int) {
        _forWard = forWard
        _reWind = reWind
    }

    override fun setVideoSize(maxWidth: Int, maxHeight: Int) {
        _maxWidth = maxWidth
        _maxHeight = maxHeight
    }

    override fun setBitRate(bitRate: Int) {
        _bitRate = bitRate
    }
}

enum class SourceType {
    ProgressiveMediaSource,
    HlsMediaSource
}

