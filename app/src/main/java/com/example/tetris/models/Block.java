package com.example.tetris.models;

import android.graphics.Color;
import android.graphics.Point;

import java.util.Random;

public class Block {

    private int shapeIndex;
    private int framaNumber;
    private BlockColor color;
    private Point position;

    private  Block(int shapeIndex, BlockColor blockColor){
        this.framaNumber = 0;
        this.shapeIndex = shapeIndex;
        this.color = blockColor;
        this.position = new Point (AppModel.FieldConstants.COLUMN_COUNT.getalue()/2,0);
    }
    // Случайным оразом выбирает индекс для формыы тетрамино
    public static Block createBlock(){
        Random random = new Random();
        int shapeIndex = random.nextInt(Shape.values().length);
        BlockColor blockColor = BlockColor.values()[random.nextInt(Shape.values().length)];

        Block block = new Block(shapeIndex, blockColor);
        block.position.x = block.position.x - Shape.values()[shapeIndex].getStartPosition();
        return  block;
    }

    public enum BlockColor{
        PINK(Color.rgb(255,105,180), (byte) 2),
                GREEN(Color.rgb(0,128,0), (byte) 3),
                ORANGE(Color.rgb(255,140,0), (byte) 4),
                YELLOW(Color.rgb(255,140,0), (byte) 5),
                CYAN(Color.rgb(0,255,255), (byte) 6);
        BlockColor(int rgbValue, byte value) {
            this.rgbValue = rgbValue;
            this.byteValue = value;
        }

        private final int rgbValue;
        private final int byteValue;
    }

    public static int getColor(byte value) {
        for (BlockColor colour : BlockColor.values()) {
            if (value == colour.bytealue){
                return  colour.rgbValue;
            }
        }
        return -1;
    }

}
