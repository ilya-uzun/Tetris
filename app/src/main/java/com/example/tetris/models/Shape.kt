/*
Моделирование фрейма и формы  тетримина
 */

package com.example.tetris.models

import java.lang.IllegalArgumentException

enum class Shape(val frameCount: Int, val startPosition: Int) {
    // линия
    Tetromino(2,2){
        override  fun getFrame(frameNumber: Int): Frama {
            return when (frameNumber){
                0 -> Frama(4).addRow("1111")
                1 -> Frama(1)
                    .addRow("1")
                    .addRow("1")
                    .addRow("1")
                    .addRow("1")
                else -> throw IllegalArgumentException("$frameNumber is an invalid frame number.")
            }
        }
    },
    // квадрат
    Tetromino1(1,1){
        override  fun getFrame(frameNumber: Int): Frama {
            return Frama (2)
                    .addRow("11")
                    .addRow("11")
        }
    },
    // Z-Образная форма
    Tetromino2(2,1){
        override  fun getFrame(frameNumber: Int): Frama {
            return when (frameNumber){
                0 -> Frama(3)
                    .addRow("110")
                    .addRow("011")
                1 -> Frama(2)
                    .addRow("01")
                    .addRow("11")
                    .addRow("10")
                else -> throw IllegalArgumentException("$frameNumber is an invalid frame number.")
            }
        }
    },
    // S-Образная форма
    Tetromino3(2,1){
        override  fun getFrame(frameNumber: Int): Frama {
            return when (frameNumber){
                0 -> Frama(3)
                    .addRow("011")
                    .addRow("110")
                1 -> Frama(2)
                    .addRow("01")
                    .addRow("11")
                    .addRow("10")
                else -> throw IllegalArgumentException("$frameNumber is an invalid frame number.")
            }
        }
    },
    // линия
    Tetromino4(2,2){
        override  fun getFrame(frameNumber: Int): Frama {
            return when (frameNumber){
                0 -> Frama(4).addRow("1111")
                1 -> Frama(1)
                    .addRow("1")
                    .addRow("1")
                    .addRow("1")
                    .addRow("1")
                else -> throw IllegalArgumentException("$frameNumber is an invalid frame number.")
            }
        }
    },
    // T-Образная форма
    Tetromino5(4,1){
        override  fun getFrame(frameNumber: Int): Frama {
            return when (frameNumber){
                0 -> Frama(3)
                    .addRow("010")
                    .addRow("111")
                1 -> Frama(2)
                    .addRow("10")
                    .addRow("11")
                    .addRow("10")
                2 -> Frama(3)
                    .addRow("111")
                    .addRow("010")
                3 -> Frama(2)
                    .addRow("01")
                    .addRow("11")
                    .addRow("01")
                else -> throw IllegalArgumentException("$frameNumber is an invalid frame number.")
            }
        }
    },
    // J-Образная форма
    Tetromino6(4,1){
        override  fun getFrame(frameNumber: Int): Frama {
            return when (frameNumber){
                0 -> Frama(3)
                    .addRow("100")
                    .addRow("111")
                1 -> Frama(2)
                    .addRow("11")
                    .addRow("10")
                    .addRow("10")
                2 -> Frama(3)
                    .addRow("111")
                    .addRow("001")
                3 -> Frama(2)
                    .addRow("01")
                    .addRow("01")
                    .addRow("11")
                else -> throw IllegalArgumentException("$frameNumber is an invalid frame number.")
            }
        }
    },
    // L-Образная форма
    Tetromino7(4,1){
        override  fun getFrame(frameNumber: Int): Frama {
            return when (frameNumber){
                0 -> Frama(3)
                    .addRow("001")
                    .addRow("111")
                1 -> Frama(2)
                    .addRow("10")
                    .addRow("10")
                    .addRow("11")
                2 -> Frama(3)
                    .addRow("111")
                    .addRow("001")
                3 -> Frama(2)
                    .addRow("11")
                    .addRow("01")
                    .addRow("01")
                else -> throw IllegalArgumentException("$frameNumber is an invalid frame number.")
            }
        }
    };
    abstract fun getFrame(flameNumber: Int): Frama
}