package com.example.tictactoe

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.media.MediaPlayer
private var mediaPlayer: MediaPlayer? = null


class WinnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)


        val winner = intent.getStringExtra("WINNER") ?: "Someone"
        findViewById<TextView>(R.id.txtwinner).text = "$winner won!"

        mediaPlayer = MediaPlayer.create(this, R.raw.winning)
        mediaPlayer?.start()


        val img = findViewById<ImageView>(R.id.imageView3)
        if (winner == intent.getStringExtra("PLAYER1")) {
            img.setImageResource(R.drawable.yay)
        } else {
            img.setImageResource(R.drawable.yay)
        }
        
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
