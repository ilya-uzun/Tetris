/*
Логика игрово поля
 */

package com.example.tetris.models

import android.graphics.Paint
import android.graphics.Point
import com.example.tetris.constants.CellConstants
import com.example.tetris.constants.FieldConstants
import com.example.tetris.constants.Motions
import com.example.tetris.constants.Statuses
import com.example.tetris.helper.array2dOfByte
import com.example.tetris.storage.AppPreferences
import java.text.FieldPosition

class AppModel {
    var score: Int = 0 // Сохраеие счета
    private var preferences: AppPreferences? = null // Обеспечивает доступ у файлу SheredPreference
    
    var currentBlock: Block? = null // Доспуп к свойству текущего блока трянслятора
    var currentState: String = Statuses.AWAITING_START.name // .name - получить string название переменной
    // игровое поля, двухмерный массив
    private var field: Array<ByteArray> = array2dOfByte(
        FieldConstants.ROW_COUNT.value,
        FieldConstants.COLUMN_COUNT.value
    )
    // Устанавливает свойства предпочтения для AppModel
    fun setPreferences(preferences: AppPreferences?){
       this.preferences = preferences
    }
    //возвращает состояние ячейки
    fun getCellStatus(row: Int, column: Int): Byte?{
        return field[row][column]
    }
    //Устанавливает состояние имещейся в поле ячейки равных указаому байту
    private fun setCellStatus(row: Int, column: Int, status: Byte?){
        if (status != null){
            field[row][column] = status
        }
    }
    /*
    Три статуса игры
     */
    fun isGameOver(): Boolean {
        return currentState == Statuses.OVER.name
    }
    fun isGameActive(): Boolean {
        return currentState == Statuses.ACTIVE.name
    }
    fun isGameAwaitingStart(): Boolean {
        return currentState == Statuses.AWAITING_START.name
    }

    //Функция для увелеичения значения очков
    private fun  boostScore(){
        score += 1
        if (score > preferences?.getHighScore() as Int) preferences?.saveHighScore(score) // Переписать значение если больше рекорда
    }
    // Создаем новый блок и установливаем значение createBlock. Если заполнены все ячейки очистить строку
    private fun generateNextBlock(){
        currentBlock = Block.createBlock()
    }
    // Проверка допустимости поступательного движения тетрамино
    private fun validTranslation(position: Point, shape: Array<ByteArray>):
        Boolean{
            return if (position.y < 0 || position.x < 0) {
                false
            } else if (position.y + shape.size > FieldConstants.ROW_COUNT.value) {
                false
            } else if (position.x + shape[0].size > FieldConstants.COLUMN_COUNT.value) {
                false
            } else {
                for (i in 0 until shape.size) {
                    for (j in 0 until shape[i].size) {
                        val y = position.y + i
                        val x = position.x + j
                        if (CellConstants.EMPTY.value != shape[i][j] &&
                            CellConstants.EMPTY.value != field[y][x]){
                            return false
                        }
                    }
                }
                true
            }
        }
    //проверка разрешен ли выполненый игровой ход
    private fun moveValid(position: Point, frameNumber: Int?): Boolean {
        val shape: Array<ByteArray>? = currentBlock?.getShape(frameNumber as Int)
        return validTranslation(position, shape as Array<ByteArray>)
    }
    // Генерируем обновление поля
    fun generateField(action: String) {
        if (isGameActive()) {
            resetField()
            var frameNumber: Int? = currentBlock?.framaNumber
            val cooridinate: Point? = Point()
            cooridinate?.x = currentBlock?.position?.x
            cooridinate?.y = currentBlock?.position?.y

            when (action) {
                Motions.LEFT.name -> {
                    cooridinate?.x = currentBlock?.position?.x?.minus(1)
                }
                Motions.RIGHT.name -> {
                    cooridinate?.x = currentBlock?.position?.x?.plus(1)
                }
                Motions.DOWN.name -> {
                    cooridinate?.y = currentBlock?.position?.y?.plus(1)
                }
                Motions.ROTATE.name -> {
                    frameNumber = frameNumber?.plus(1)
                    if (frameNumber != null) {
                        if (frameNumber >= currentBlock?.frameCount as Int) {
                            frameNumber = 0
                        }
                    }
                }
            }
            if (!moveValid(cooridinate as Point, frameNumber)) {
                translateBlock(currentBlock?.position as Point, currentBlock?.framaNumber as Int)
                if (Motions.DOWN.name == action) {
                    boostScore()
                    persistCellData()
                    assessField()
                    generateNextBlock()
                    if (!blockAdditionPossible()) {
                        currentState = Statuses.OVER.name;
                        currentBlock = null;
                        resetField(false);
                    }
                }
            }else {
                if (frameNumber != null) {
                    translateBlock(cooridinate, frameNumber)
                    currentBlock?.setState(frameNumber, cooridinate)
                }
            }
        }
    }
    //
    private fun resetField(ephemeralCellsOnly: Boolean = true){
        for (i in 0 until FieldConstants.ROW_COUNT.value) {
            (0 until FieldConstants.COLUMN_COUNT.value)
                .filter { !ephemeralCellsOnly || field[i][it] == CellConstants.EPHEMERAL.value }
                .forEach { field[i][it] = CellConstants.EMPTY.value }
        }
    }
    //
    private fun persistCellData(){
        for (i in 0 until field.size){
            for (j in 0 until field[i].size){
                var status = getCellStatus(i, j)
                if (status == CellConstants.EPHEMERAL.value){
                    status = currentBlock?.staticValue
                    setCellStatus(i, j, status)
                }
            }
        }
    }
    // Проверка заполнения строк
    private fun assessField(){
        for (i in 0 until  field.size){
            var emptyCells = 0;
            for (j in 0 until field[i].size){
                val status = getCellStatus(i, j)
                val isEmpty = CellConstants.EMPTY.value == status
                if (isEmpty) emptyCells++
            }
            if (emptyCells == 0) shiftRows(i)
        }
    }

    private fun translateBlock(position: Point, frameNumber: Int){
        synchronized(field){
            val shape: Array<ByteArray>? = currentBlock?.getShape(frameNumber)
            if (shape != null) {
                for (i in shape.indices){
                    for (j in 0 until shape[i].size){
                        val y = position.y + i
                        val x = position.x + j
                        if (CellConstants.EMPTY.value != shape[i][j]){
                            field[y][x] = shape[i][j]
                        }
                    }
                }
            }
        }
    }

    private fun blockAdditionPossible(): Boolean {
        if (!moveValid(currentBlock?.position as Point, currentBlock?.framaNumber)){
            return  false
        }
        return  true
    }

    private fun shiftRows(nToRow: Int){
        if (nToRow > 0) {
            for (j in nToRow - 1 downTo  0) {
                for (m in 0 until field[j].size){
                    setCellStatus(j + 1, m, getCellStatus(j, m))
                }
            }
        }
        for (j in 0 until field[0].size){
            setCellStatus(0, j, CellConstants.EMPTY.value)
        }
    }

    fun startGame(){
        if (!isGameActive()){
            currentState = Statuses.ACTIVE.name
            generateNextBlock()
        }
    }

    fun restartGame(){
        resetModel()
        startGame()
    }

    fun endGame(){
        score = 0
        currentState = Statuses.OVER.name
    }

    private fun resetModel(){
        resetField(false)
        currentState = Statuses.AWAITING_START.name
        score = 0
    }




}
