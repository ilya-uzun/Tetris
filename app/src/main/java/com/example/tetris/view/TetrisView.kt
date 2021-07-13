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
import com.example.tetris.constants.CellConstants
import com.example.tetris.constants.FieldConstants
import com.example.tetris.constants.Motions
import com.example.tetris.constants.Statuses
import com.example.tetris.models.Block
import com.example.tetris.models.AppModel

class TetrisView : View {
    private val paint = Paint()
    private var lastMove: Long = 0
    private var model: AppModel? = null
    private var activity: GameActivity? = null
    private val viewHandler = ViewHandler(this)
    private var cellSize: Dimension = Dimension(0, 0)
    private var frameOffset: Dimension = Dimension(0, 0)

    constructor(context: Context, attrs: AttributeSet)   : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int)   : super(context, attrs, defStyle)

    companion object{
        private val DELAY = 500
        private val BLOCK_OFFSET = 2
        private val FRAME_OFFSET_BASE = 10
    }
                                     /* Частные классы */
/*
Пользовательский обработчик, реализуемый для TetrisView,
*/

    private class ViewHandler(private val owner: TetrisView): Handler (){
    //Проерка отправки сообщения
        override fun handleMessage(message: Message){
            if(message.what == 0){
                if(owner.model != null){
                    if(owner.model!!.isGameOver()){
                        owner.model?.endGame()
                        Toast.makeText(owner.activity, "Game over", Toast.LENGTH_LONG).show();
                    }
                    if(owner.model!!.isGameActive()){
                        owner.setGameCommandWithDelay(Motions.DOWN)
                    }
                }
            }
        }
        //Удаление отпрвлного сообщения и отправление ноого сообщения с задержкой
        fun sleep(delay:Long){
            this.removeMessages(0)
            sendMessageDelayed(obtainMessage(0),delay)
        }
    }
/*
Включение свойст ширины и высоты
*/
    private data class Dimension(val width: Int, val height: Int)

                                         /* Сетторы*/
    //Установка текущей модели
    fun  setModel(model: AppModel){
        this.model = model
    }
    //установка приениемого действия
    fun setActivity(gameActivity: GameActivity){
        this.activity = gameActivity
    }
    // Установливает исполняемую игрой еукущею команду перемещения
    fun  setGameCommand(move: Motions){
        if(null != model && (model?.currentState == Statuses.ACTIVE.name)){
            if(Motions.DOWN == move){
                model?.generateField(move.name)
                invalidate() // запрос на внесение изменений на экран
                return
            }
            setGameCommandWithDelay(move)
        }
    }
    //
     fun setGameCommandWithDelay(move:Motions){
        val now = System.currentTimeMillis()
        if(now - lastMove > DELAY){
            model?.generateField(move.name)
            invalidate()
            lastMove = now
        }
        updateScores()
        viewHandler.sleep(DELAY.toLong())
    }
    //
    private fun updateScores(){
        activity?.tvCurrentScore?.text = "${model?.score}"
        activity?.tvHighScore?.text = "${activity?.appPreferences?.getHighScore()}"
    }
    // вызывается если пердставление отображает свое содержание
    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        drawFrama(canvas)
        if (model != null){
            for(i in 0 until FieldConstants.ROW_COUNT.value){
                for(j in 0 until FieldConstants.COLUMN_COUNT.value){
                    drawCell(canvas, i, j)
                }
            }
        }
    }

    private fun drawFrama(canvas: Canvas){
        paint.color = Color.LTGRAY
        canvas.drawRect(frameOffset.width.toFloat(), frameOffset.height.toFloat(),
            width - frameOffset.width.toFloat(),
            height - frameOffset.height.toFloat(), paint)
    }

    private fun drawCell(canvas: Canvas, row: Int, col: Int){
        val cellStatus = model?.getCellStatus(row, col)
        if(CellConstants.EMPTY.value != cellStatus){
            val color = if (CellConstants.EPHEMERAL.value == cellStatus){
                model?.currentBlock?.color
            } else {
                Block.getColor(cellStatus as Byte)
            }
            drawCell(canvas, col, row, color as Int)
        }
    }

    private fun drawCell(canvas: Canvas, row: Int, col: Int, rgbColor: Int){
        paint.color = rgbColor
        val top: Float = (frameOffset.height + y * cellSize.height + BLOCK_OFFSET).toFloat()
        val left: Float = (frameOffset.width + x * cellSize.width + BLOCK_OFFSET).toFloat()
        val bottom: Float = (frameOffset.height + (y+1) * cellSize.height - BLOCK_OFFSET).toFloat()
        val right: Float = (frameOffset.width + (x+1) * cellSize.width - BLOCK_OFFSET).toFloat()
        val rectangle = RectF(left, top, right, bottom)
        canvas.drawRoundRect(rectangle, 4F, 4F, paint)
    }
    // вызывается при изменение размера предтавления
    override fun onSizeChanged(width: Int, height: Int, previousWidth: Int, previousHeight: Int) {
        super.onSizeChanged(width, height, previousWidth, previousHeight)
        val cellWidth = (width - 2 * FRAME_OFFSET_BASE) / FieldConstants.COLUMN_COUNT.value
        val cellHeight = (height - 2 * FRAME_OFFSET_BASE) / FieldConstants.ROW_COUNT.value
        val n =Math.min(cellWidth, cellHeight)
        this.cellSize = Dimension(n, n)
        val offsetX = (width - FieldConstants.COLUMN_COUNT.value * n) / 2
        val offsetY = (height - FieldConstants.ROW_COUNT.value * n) / 2
        this.frameOffset = Dimension(offsetX, offsetY)
    }

}
