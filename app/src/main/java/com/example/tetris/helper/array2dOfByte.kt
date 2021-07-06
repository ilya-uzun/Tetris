/*
sizeOuter -  Номер строки создоваемого массива
sizeInner - Номер столбца сгенерированного ассиав  байтов
 */

package com.example.tetris.helper

fun array2dOfByte(sizeOuter: Int, sizeInner: Int): Array<ByteArray> = Array(sizeOuter) { ByteArray(sizeInner) }

