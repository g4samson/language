package com.profs.languageapp.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class AnimalClassifier(private val context: Context) {

    private val interpreter: Interpreter

    init {
        interpreter = Interpreter(loadModelFile())
    }

    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("model.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun classify(bitmap: Bitmap): FloatArray {
        val input = preprocess(bitmap)

        val outputShape = interpreter.getOutputTensor(0).shape()
        val output = Array(1) { FloatArray(outputShape[1]) }

        interpreter.run(input, output)

        return output[0]
    }

    private fun preprocess(bitmap: Bitmap): Array<Array<Array<FloatArray>>> {
        val resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

        val input = Array(1) {
            Array(224) {
                Array(224) {
                    FloatArray(3)
                }
            }
        }

        for (y in 0 until 224) {
            for (x in 0 until 224) {
                val pixel = resized.getPixel(x, y)

                input[0][y][x][0] = Color.red(pixel) / 255f
                input[0][y][x][1] = Color.green(pixel) / 255f
                input[0][y][x][2] = Color.blue(pixel) / 255f
            }
        }

        return input
    }

    fun close() {
        interpreter.close()
    }

    fun loadLabels(context: Context): List<String> {
        return context.assets.open("labels.txt").bufferedReader().readLines()
    }
}