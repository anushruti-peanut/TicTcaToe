package com.example.tictactoe

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.gridlayout.widget.GridLayout
import android.view.animation.AlphaAnimation



class GameActivity : AppCompatActivity() {
    private val boardBtn = Array(3) { arrayOfNulls<Button>(3) }   

    private lateinit var dotTop: View
    private lateinit var dotBottom: View


    private fun blink(view: View) {
        val anim = AlphaAnimation(0f, 1f).apply {
            duration = 500
            repeatMode = AlphaAnimation.REVERSE
            repeatCount = AlphaAnimation.INFINITE
        }
        view.visibility = View.VISIBLE     
        view.startAnimation(anim)
    }

    private fun stopBlink(view: View) {
        view.clearAnimation()
        view.visibility = View.INVISIBLE   
    }


    private fun updateTurnDots() {
        if (isXTurn) {
            blink(dotTop);   stopBlink(dotBottom)
        } else {
            blink(dotBottom); stopBlink(dotTop)
        }
    }



    
    private lateinit var topLabel: TextView          
    private lateinit var bottomLabel: TextView       

   
    private lateinit var p1Name: String              
    private lateinit var p2Name: String             

   
    private var isXTurn = true
    private val boardState = Array(3) { Array(3) { "" } }
    private var moveCount = 0


    private fun coloredLabel(name: String, symbol: String, hex: String): CharSequence =
        SpannableString("$name  ($symbol)").apply {
            val start = indexOf('(')
            setSpan(
                ForegroundColorSpan(hex.toColorInt()),
                start, length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameactivity)


        p1Name = intent.getStringExtra("PLAYER1") ?: "Player 1"
        p2Name = intent.getStringExtra("PLAYER2") ?: "Player 2"


        topLabel = findViewById(R.id.txtTop)
        bottomLabel = findViewById(R.id.txtBottom)
        topLabel.text = coloredLabel(p2Name, "X", "#FF69B4")   // pink X
        bottomLabel.text = coloredLabel(p1Name, "O", "#87CEEB")// blue O
        dotTop = findViewById(R.id.dotTop)
        dotBottom = findViewById(R.id.dotBottom)
        updateTurnDots()



        val grid = findViewById<GridLayout>(R.id.gameGrid)
        grid.rowCount = 3
        grid.columnCount = 3

        for (r in 0..2) {
            for (c in 0..2) {
                val row = r
                val col = c

                val cell = Button(this).apply {
                    textSize = 24f
                    setBackgroundColor(Color.WHITE)
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0; height = 0
                        rowSpec    = GridLayout.spec(row, 1, 1f)
                        columnSpec = GridLayout.spec(col, 1, 1f)
                        setMargins(2, 2, 2, 2)
                    }
                    setOnClickListener {
                        if (text.isEmpty()) {
                            val symbol = if (isXTurn) "X" else "O"
                            text = symbol
                            setTextColor(
                                if (symbol == "X") "#FF69B4".toColorInt() else "#87CEEB".toColorInt()
                            )
                            boardState[row][col] = symbol
                            moveCount++

                            when {
                                checkWin(symbol) -> {


                                    highlightWin(symbol)


                                    disableBoard(grid)


                                    val winner = if (symbol == "X") p2Name else p1Name
                                    val winIntent = Intent(this@GameActivity, WinnerActivity::class.java).apply {
                                        putExtra("WINNER", winner)
                                    }
                                    startActivity(winIntent)


                                    finish()
                                }


                                moveCount == 9 -> {
                                    startActivity(Intent(this@GameActivity, drawscreen::class.java))
                                }

                                else -> {
                                    isXTurn = !isXTurn
                                    updateTurnDots()
                                }

                            }
                        }
                    }
                }
                boardBtn[row][col] = cell
                grid.addView(cell)
            }
        }
    }


    private fun checkWin(sym: String): Boolean {
        for (i in 0..2) {
            if ((boardState[i][0] == sym && boardState[i][1] == sym && boardState[i][2] == sym) ||
                (boardState[0][i] == sym && boardState[1][i] == sym && boardState[2][i] == sym)
            ) return true
        }
        return (boardState[0][0] == sym && boardState[1][1] == sym && boardState[2][2] == sym) ||
                (boardState[0][2] == sym && boardState[1][1] == sym && boardState[2][0] == sym)
    }
    private fun highlightWin(sym: String) {
        val pink = "#FFD1DC".toColorInt()

        // rows & columns
        for (i in 0..2) {
            if (boardState[i][0] == sym && boardState[i][1] == sym && boardState[i][2] == sym) {
                (0..2).forEach { boardBtn[i][it]?.setBackgroundColor(pink) }
            }
            if (boardState[0][i] == sym && boardState[1][i] == sym && boardState[2][i] == sym) {
                (0..2).forEach { boardBtn[it][i]?.setBackgroundColor(pink) }
            }
        }

        if (boardState[0][0] == sym && boardState[1][1] == sym && boardState[2][2] == sym) {
            listOf(0 to 0, 1 to 1, 2 to 2).forEach { (r, c) -> boardBtn[r][c]?.setBackgroundColor(pink) }
        }
        if (boardState[0][2] == sym && boardState[1][1] == sym && boardState[2][0] == sym) {
            listOf(0 to 2, 1 to 1, 2 to 0).forEach { (r, c) -> boardBtn[r][c]?.setBackgroundColor(pink) }
        }
    }



    private fun disableBoard(grid: GridLayout) {
        for (i in 0 until grid.childCount) {
            (grid.getChildAt(i) as Button).isEnabled = false
        }
    }
}
