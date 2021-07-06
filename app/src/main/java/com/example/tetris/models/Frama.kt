package com.example.tetris.models

import com.example.tetris.helper.array2dOfByte

class Frama(private val  width: Int) {
    val data: ArrayList<ByteArray> = ArrayList()

    fun addRow(byteStr: String): Frama {
        val row = ByteArray(byteStr.length)

        for (index in byteStr.indices){
            row[index] = "${byteStr[index]}".toByte()
        }
        data.add(row)
        return this
    }
    fun as2dByteArray(): Array<ByteArray>{
        val bytes = array2dOfByte(data.size, width)
        return  data.toArray(bytes)
    }
}