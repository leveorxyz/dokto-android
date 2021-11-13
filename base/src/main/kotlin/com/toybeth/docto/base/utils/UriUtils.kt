package com.toybeth.docto.base.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import com.orhanobut.logger.Logger
import java.lang.Exception
import android.content.ContentResolver
import android.util.Base64
import java.io.*


fun getFile(context: Context, uri: Uri): File {
    val fileName = queryName(context, uri)
    val destinationFilename = File(
        context.filesDir.path + File.separatorChar.toString() + fileName
    )
    Logger.d(fileName)
    try {
        val ins = context.contentResolver.openInputStream(uri)
        ins?.let {
            createFileFromStream(it, destinationFilename)
        }

    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return destinationFilename
}
fun fileUriToBase64(context: Context, uri: Uri): String {
    val resolver = context.contentResolver
    var encodedBase64 = ""
    try {
        val bytes: ByteArray? = readBytes(uri, resolver)
        encodedBase64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
    } catch (e1: IOException) {
        e1.printStackTrace()
    }
    return encodedBase64
}

private fun createFileFromStream(ins: InputStream, destination: File) {
    try {
        FileOutputStream(destination).use { os ->
            val buffer = ByteArray(4096)
            var length: Int
            while (ins.read(buffer).also { length = it } > 0) {
                os.write(buffer, 0, length)
            }
            os.flush()
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

private fun queryName(context: Context, uri: Uri): String {
    val returnCursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
    val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name: String = returnCursor.getString(nameIndex)
    returnCursor.close()
    return name
}

private fun readBytes(uri: Uri, resolver: ContentResolver): ByteArray? {
    // this dynamically extends to take the bytes you read
    val inputStream = resolver.openInputStream(uri)
    val byteBuffer = ByteArrayOutputStream()

    // this is storage overwritten on each iteration with bytes
    val bufferSize = 1024
    val buffer = ByteArray(bufferSize)

    // we need to know how may bytes were read to write them to the
    // byteBuffer
    var len = 0
    while (inputStream!!.read(buffer).also { len = it } != -1) {
        byteBuffer.write(buffer, 0, len)
    }

    // and then we can return your byte array.
    return byteBuffer.toByteArray()
}