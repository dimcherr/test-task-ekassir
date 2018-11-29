package pics.phocus.testtaskekassir.internal

import android.content.Context

object CacheManager {
    fun clearOldCache(context: Context, minutes: Int) {
        for (file in context.cacheDir.listFiles()) {
            if (System.currentTimeMillis() - file.lastModified() > minutes * 60 * 1000) {
                file.delete()
            }
        }
    }
}