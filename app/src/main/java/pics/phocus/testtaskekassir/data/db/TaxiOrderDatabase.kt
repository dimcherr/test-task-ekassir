package pics.phocus.testtaskekassir.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder

@Database(
    entities = [TaxiOrder::class],
    version = 1
)
abstract class TaxiOrderDatabase : RoomDatabase() {
    abstract fun taxiOrderDao(): TaxiOrderDao

    companion object {
        @Volatile
        private var instance: TaxiOrderDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, TaxiOrderDatabase::class.java, "taxi_order_db").build()
    }
}