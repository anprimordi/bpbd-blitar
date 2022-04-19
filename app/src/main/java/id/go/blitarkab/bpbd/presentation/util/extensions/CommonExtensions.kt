package id.go.blitarkab.bpbd.presentation.util.extensions

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.esafirm.imagepicker.model.Image
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.Dispatchers
import java.io.File

// LiveData
fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

fun Image.toFile(context: Context): File? {
    return if (path.isNotBlank() && File(path).exists()) File(path)
    else context.applicationContext.uriToFile(uri)
}

fun Uri.toByteArray(context: Context): ByteArray? {
    return try {
        val inputStream = context
            .contentResolver
            .openInputStream(this) ?: throw NullPointerException()
        inputStream.readBytes()
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}

suspend fun File.compress(context: Context, maxSize: Long = 1_097_152): File {
    return Compressor.compress(
        context = context,
        imageFile = this,
        coroutineContext = Dispatchers.IO
    ) { size(maxFileSize = maxSize) }
}