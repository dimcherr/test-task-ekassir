package pics.phocus.testtaskekassir.images

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import kotlinx.coroutines.*
import okhttp3.*
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL


class ImageLoader {
    companion object {
        fun with(coroutineScope: CoroutineScope) = Builder().with(coroutineScope)
        fun load(url: String) = Builder().load(url)
    }

    class Builder {
        private var url: String = ""
        private var target: ImageView? = null
        private var coroutineScope: CoroutineScope? = null

        fun with(coroutineScope: CoroutineScope) = apply {
            this.coroutineScope = coroutineScope
        }

        fun load(url: String) = apply {
            this.url = url
        }

        fun into(target: ImageView) = apply {
            this.target = target
            loadBitmap()
        }

        private fun loadBitmap() {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("MYTAG", "ON FAILURE")
                }

                override fun onResponse(call: Call, response: Response) {
                    val bitmap = response.body()?.byteStream()?.let {
                        BitmapFactory.decodeStream(it)
                    }
                    val scope = coroutineScope ?: GlobalScope
                    scope.launch {
                        target?.setImageBitmap(bitmap)
                    }
                }
            })
        }
    }
}