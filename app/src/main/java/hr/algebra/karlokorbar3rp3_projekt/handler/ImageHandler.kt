package hr.algebra.karlokorbar3rp3_projekt.handler

import android.content.Context
import android.util.Log
import hr.algebra.karlokorbar3rp3_projekt.factory.createGetHttpUrlConnection
import java.io.File
import java.lang.Exception
import java.net.HttpURLConnection
import java.nio.file.Files
import java.nio.file.Paths

fun downloadImageAndStore(context: Context, url: String, fileName: String): String? {
    val ext = url.substring(url.lastIndexOf("."))
    val file: File = createFile(context, fileName, ext)
    try {

        val con: HttpURLConnection = createGetHttpUrlConnection(url)
        Files.copy(con.inputStream, Paths.get(file.toURI()))
        return file.absolutePath
    } catch (e: Exception) {
        Log.e("DOWNLOAD IMAGE", e.message, e)
    }
    return null
}

fun createFile(context: Context, fileName: String, ext: String): File {
    val dir = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, File.separator + fileName + ext)
    if (file.exists()) {
        file.delete()
    }
    return file
}
