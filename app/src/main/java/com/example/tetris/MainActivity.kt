package com.example.tetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar

class MainActivity : AppCompatActivity() {

    var tvHighScore: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val linearLayout: LinearLayout = LinearLayout(this)
        supportActionBar?.hide() // если ссылка не является пустой то объект вызывает метод hide
        val btnNewGame = findViewById<Button>(R.id.btn_new_game)
        val btnResetScore = findViewById<Button>(R.id.btn_reset_score)
        val btnExit = findViewById<Button>(R.id.btn_exit)
    }

    fun handleExitEvent(view: View){
        finish()
    }
}
