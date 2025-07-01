package com.example.tictactoe

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)


        mediaPlayer = MediaPlayer.create(this, R.raw.pacman_beginning)
        mediaPlayer?.start()


        findViewById<TextView>(R.id.textview1).startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.bob)
        )
        findViewById<ImageView>(R.id.mascot).startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.bob)
        )


        findViewById<Button>(R.id.play).setOnClickListener {
            startActivity(Intent(this, NameEntry::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
