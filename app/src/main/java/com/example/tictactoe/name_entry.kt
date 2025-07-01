package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class NameEntry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_entry)


        val p1Input = findViewById<EditText>(R.id.editTextplayer1)
        val p2Input = findViewById<EditText>(R.id.editTextplayer2)
        val goBtn   = findViewById<Button>(R.id.gobtn)


        goBtn.setOnClickListener {
            val name1 = p1Input.text.toString().trim()
            val name2 = p2Input.text.toString().trim()

            if (name1.isEmpty() || name2.isEmpty()) {
                Toast.makeText(this, "Please enter both names", Toast.LENGTH_SHORT).show()
            } else {

                Intent(this, GameActivity::class.java).apply {
                    putExtra("PLAYER1", name1)
                    putExtra("PLAYER2", name2)
                    startActivity(this)
                }
            }
        }

    }
}
