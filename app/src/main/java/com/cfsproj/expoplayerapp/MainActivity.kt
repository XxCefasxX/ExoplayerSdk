package com.cfsproj.expoplayerapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cfsproj.exoplayer_sdk.ExoplayerApiImpl
import com.cfsproj.exoplayer_sdk.SourceType
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.MimeTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var playerView: PlayerView
    private lateinit var playerView2: PlayerView
    private lateinit var btStop: Button
    private lateinit var btPause: Button

    private lateinit var exoplayerApiImpl: ExoplayerApiImpl
    private lateinit var exoplayerApiImpl2: ExoplayerApiImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exoplayerApiImpl = ExoplayerApiImpl(this)
        exoplayerApiImpl.initPlayer()

        exoplayerApiImpl2 = ExoplayerApiImpl(this)
        exoplayerApiImpl2.initPlayer()

        playerView = findViewById(R.id.playerView)
        playerView.player = exoplayerApiImpl.player

        playerView2 = findViewById(R.id.playerView2)
        playerView2.player = exoplayerApiImpl2.player

        btStop = findViewById(R.id.bt_stop)
        btPause = findViewById(R.id.bt_pause)

        btStop.setOnClickListener {
            exoplayerApiImpl.releasePlayer()
        }
        btPause.setOnClickListener {
            exoplayerApiImpl.forwardPlayer()
        }

        val STREAM_KEY = "ay5mt9r60HQ"
        val source =
//            "https://www.youtube.com/watch?v=ay5mt9r60HQ"
            "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8"
//            "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"
//            "https://player.vimeo.com/external/342571552.hd.mp4?s=6aa6f164de3812abadff3dde86d19f7a074a8a66&profile_id=175&oauth2_token_id=57447761"

//        exoplayerApiImpl.addHeader(
//            "Authorization",
//            "DO4LDjvhJcEbCtOcQwZSpQj1GLWtoStTGtS5iCVuQPABJxAD9hVm8mFO"
//        )

        exoplayerApiImpl.setMediaSource(source, SourceType.HlsMediaSource)
        exoplayerApiImpl.preparePlayer()

        exoplayerApiImpl2.setMediaSource(source, SourceType.HlsMediaSource)
        exoplayerApiImpl2.preparePlayer()

        exoplayerApiImpl.startPlayer()
        exoplayerApiImpl2.startPlayer()


    }

    override fun onDestroy() {
        super.onDestroy()
        exoplayerApiImpl.releasePlayer()
    }
}
