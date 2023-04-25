package com.cfsproj.exoplayer_sdk

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.source.MediaParserExtractorAdapter;

private const val TAG = "ExoplayerApiImpl"

class ExoplayerApiImpl(private val context: Context) : ExoplayerApi {

    lateinit var player: SimpleExoPlayer

    private var headers = mutableMapOf<String, String>()

    override fun initPlayer(bitRate: Int) {


        val trackSelector = DefaultTrackSelector(context)


        trackSelector.buildUponParameters()
            .setMaxVideoSize(140, 140)
            .setMaxVideoBitrate(bitRate)
            .setForceHighestSupportedBitrate(true)
            .build()

        val bandwidthMeter: DefaultBandwidthMeter = DefaultBandwidthMeter.Builder(context)
            .setInitialBitrateEstimate(10)
            .build()

        // Initialize the player
        player = SimpleExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .build()

    }

    override fun setMediaSource(mediaSource: String, sourceType: SourceType) {

        val mediaItem = MediaItem
            .Builder()
            .setUri(Uri.parse(mediaSource))
            .setMimeType(MimeTypes.BASE_TYPE_VIDEO)
            .setDrmLicenseRequestHeaders(headers)
            .build()

        val mp4VideoUri = Uri.parse(mediaSource)
        val BANDWIDTH_METER = DefaultBandwidthMeter()
        val dataSourceFactory =
            DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, "androidwave"),
                BANDWIDTH_METER
            )

        val mediaSource: MediaSource = ExtractorMediaSource(dataSourceFactory)
            .createMediaSource(mediaItem)

//        val mediaSource =
//            when (sourceType) {
//                SourceType.ProgressiveMediaSource -> ProgressiveMediaSource.Factory(
//                    DefaultDataSourceFactory(context, "user-agent")
//                ).createMediaSource(mediaItem)
//
//                SourceType.HlsMediaSource -> HlsMediaSource.Factory(
//                    DefaultDataSourceFactory(
//                        context,
//                        "HLS App"
//                    )
//                )
//                    .createMediaSource(mediaItem)
//            }

        player.setMediaSource(mediaSource)
    }

    override fun preparePlayer() {
        // Prepare the player for playback
        player.prepare()
    }

    override fun startPlayer() {
        // Start playing the media
        player.playWhenReady = true
        try {

            player.play()
        } catch (ex: java.lang.Exception) {
            Log.d(TAG, "startPlayer: ${ex.message}")
        }
    }

    override fun pausePlayer() {
        // Pause the player
        player.playWhenReady = false
    }

    override fun releasePlayer() {
        // Release the player
        player.release()
    }

    override fun addHeader(headerName: String, headerValue: String) {
        headers[headerName] = headerValue
    }


}

enum class SourceType {
    ProgressiveMediaSource,
    HlsMediaSource
}

