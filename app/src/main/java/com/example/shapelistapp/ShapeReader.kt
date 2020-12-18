package com.example.shapelistapp

import android.content.Context
import java.io.*


class ShapeReader {

    fun readFromFile(context: Context, path: String): String {
        lateinit var string: String

        if (!fileExist(context, path)) {
            try {
                val inputStream: InputStream = context.assets.open(path)
                val buffer = ByteArray(inputStream.available())
                inputStream.read(buffer)
                string = String(buffer)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            string = BufferedReader(InputStreamReader(context.openFileInput(path))).readLine().toString()
        }
        return string
    }

    fun writeToFile(context: Context, path: String, data: String) {
        try {
            val fileOutputStream = context.openFileOutput(path, Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun fileExist(context: Context, path: String): Boolean {
        val file = context.getFileStreamPath(path)
        return file.exists()
    }
}