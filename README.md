## This is a classic tetris game
#### Project goals, research:       

## Pre-requisites

* Android SDK v30 
* Android min SDK v21 

## Tools used

* Appcompat v1.3.0
* Material v1.3.0
* Constraintlayout v2.0.4
* Glide v4.2.1

## Objectives of the work:

1. Creating an Android application using the Android studio development environment.              
2. Managing the layout parameters of elements using. xml files        
3. In the Kotlin project, connect the Java class.          
4. Testing language constructs Kotlin;       
    * Set      
     ```Kotlin
     fun  setModel(model: AppModel){
        this.model = model
    }
    ```
    * Get        
    ```Kotlin
    fun getHighScore(): Int{
        return data.getInt("HIGH_SCORE", 0)
    }
    ```   
    * calling the base class constructor    
     ```Kotlin
        constructor(context: Context, attrs: AttributeSet)   : super(context, attrs)
     ```   
    * emum class
    ```Kotlin
    enum class FieldConstants(val value: Int) {
    COLUMN_COUNT(10), ROW_COUNT(20);
    }
    ```
    * data class
    ```Kotlin
     data class Dimension(val width: Int, val height: Int);
    }
    ```
    * Using an annotation @NonNull 
     ```java
    @NonNull
    public final byte[][] getShape(int frameNumber){
        return Shape.values()[shapeIndex].getFrame(frameNumber).as2dByteArray();
    }
     ```
    * Companion Object     
    ```Kotlin
        companion object{
        private val DELAY = 500
        private val BLOCK_OFFSET = 2
        private val FRAME_OFFSET_BASE = 10
    }
    ```
    * abstract functions     
    ```Kotlin  
        abstract fun getFrame(flameNumber: Int): Frama
    ```

5. Testing the application on a real Xiaomi Red 9 Android v 10 device.      
