/*
Класс для взаимодействия пользователя с AppModel
 */

package com.example.tetris.view

import android.content.Context
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.widget.Toast
import android.view.View
import com.example.tetris.GameActivity
import com.example.tetris.constants.FieldConstants
import com.example.tetris.models.Block
import com.example.tetris.models.AppModel

class TetrisView : View {
    private val paint = Paint()
    private var lastMoe: Long = 0
    private var model: AppModel? = null
    private var activity: GameActivity? = null
    private val viewHandler = ViewHandler(this)
    private var cellSize: Dimension = Dimension =(0, 0)
    private var frameOffset: Dimension = Dimension =(0, 0)

    constructor(context: Context, attrs: ArrtibuteSet)   : super(context, attrs)

    constructor(context: Context, attrs: ArrtibuteSet, defStyle: Int)   : super(context, attrs, defStyle)

    companion object{
        private val DELAY = 500
        private val BLOCK_OFFSET = 2
        private val FRAME_OFFSET_BASE = 10
    }
}