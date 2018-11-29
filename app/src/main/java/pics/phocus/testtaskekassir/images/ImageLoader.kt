package pics.phocus.testtaskekassir.images

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import androidx.preference.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import pics.phocus.testtaskekassir.internal.CacheManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

object ImageLoader {

    const val IMAGE_BASE_URL = "http://www.roxiemobile.ru/careers/test/images"
    const val CACHE_MINUTES = 10

    fun with(coroutineScope: CoroutineScope) = Builder().with(coroutineScope)

    class Builder {
        private var url: String = ""
        private var target: ImageView? = null
        private var coroutineScope: CoroutineScope? = null

        fun with(coroutineScope: CoroutineScope) = apply {
            this.coroutineScope = coroutineScope
        }

        fun load(photoName: String) = apply {
            this.url = "$IMAGE_BASE_URL/$photoName"
        }

        fun into(target: ImageView) = apply {
            this.target = target
            loadImage(target.context)
        }

        private fun saveNewCacheObject(context: Context): File? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val newCacheFile = getCacheFile(context)
            return newCacheFile?.also { cacheFile ->
                prefs.edit().putString(url, cacheFile.path).apply()
            }
        }

        private fun loadImage(context: Context) {
            if (url.isEmpty()) return

            // clear old cache
            CacheManager.clearOldCache(context, CACHE_MINUTES)

            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val cachePath = prefs.getString(url, null)
            if (cachePath == null || !File(cachePath).exists()) {
                loadBitmapFromNetwork(saveNewCacheObject(context))
            } else {
                loadBitmapFromCache(cachePath)
            }
        }

        private fun getCacheFile(context: Context) =
            Uri.parse(url)?.lastPathSegment?.let { filename ->
                File.createTempFile(filename, null, context.cacheDir)
            }

        private fun saveBitmapToCache(bitmap: Bitmap, cacheFile: File) {
            var outputStream: OutputStream? = null
            try {
                outputStream = FileOutputStream(cacheFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            } catch (e: IOException) {
                cacheFile.delete()
            } finally {
                outputStream?.close()
            }
        }

        private fun loadBitmapFromCache(cachePath: String) {
            val scope = coroutineScope ?: GlobalScope
            scope.launch {
                val bitmap = BitmapFactory.decodeFile(cachePath)
                target?.setImageBitmap(bitmap)
            }
        }

        private fun loadBitmapFromNetwork(cacheFile: File?) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cacheFile?.delete()
                }

                override fun onResponse(call: Call, response: Response) {
                    val bitmap = response.body()?.byteStream()?.let {
                        BitmapFactory.decodeStream(it)
                    }
                    if (bitmap == null) {
                        cacheFile?.delete()
                    } else {
                        val scope = coroutineScope ?: GlobalScope
                        scope.launch {
                            target?.setImageBitmap(bitmap)
                            cacheFile?.let { saveBitmapToCache(bitmap, it) }
                        }
                    }
                }
            })
        }
    }
}