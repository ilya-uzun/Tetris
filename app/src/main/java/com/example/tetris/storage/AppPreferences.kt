package com.example.tetris.storage
import android.content.Context
import android.content.SharedPreferences

class AppPreferences(ctx: Context) {
    var data: SharedPreferences = ctx.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)

    // сохранение наибольшее количество очко
    fun saveHighScore(highScope: Int){
        data.edit().putInt("HIGH_SCORE", highScope).apply()
    }
    // возвращает наибольшее количество очко
    fun getHighScore(): Int{
        return data.getInt("HIGH_SCORE", 0)
    }
    // сбрасывает значения наибольшее количество очко
    fun clearHighScore(highScope: Int){
        data.edit().putInt("HIGH_SCORE", 0).apply()
    }
}