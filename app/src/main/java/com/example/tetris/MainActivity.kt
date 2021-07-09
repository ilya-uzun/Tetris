package com.example.tetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.view.View
import android.widget.Button
import android.widget.TextView
//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.ActionBar
import com.example.tetris.storage.AppPreferences
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    var tvHighScore: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide() // если ссылка не является пустой то объект вызывает метод hide

        //val linearLayout: LinearLayout = LinearLayout(this)()
        val btnNewGame = findViewById<Button>(R.id.btn_new_game)
        val btnResetScore = findViewById<Button>(R.id.btn_reset_score)
        val btnExit = findViewById<Button>(R.id.btn_exit)

        tvHighScore = findViewById<TextView>(R.id.tv_high_score)
        btnNewGame.setOnClickListener(this::onBtnNewGameClick)
        btnResetScore.setOnClickListener(this::onBtnResetScoreClick)
        btnExit.setOnClickListener(this::onBtnExitClick)
    }

    private fun onBtnNewGameClick(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun onBtnResetScoreClick(view: View) {
        val preferences = AppPreferences(this)
        preferences.clearHighScore()
        Snackbar.make(view, "Score successfully reset",Snackbar.LENGTH_SHORT).show() // Snackbar Класс для дезайна
        tvHighScore?.text = "High score: ${preferences.getHighScore()}"
    }

    private fun onBtnExitClick(view: View) {
        System.exit(0)
    }

}
