package pics.phocus.testtaskekassir.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pics.phocus.testtaskekassir.data.db.model.VehicleOrder

@Database(
    entities = [VehicleOrder::class],
    version = 1
)
abstract class VehicleOrderDatabase : RoomDatabase() {
    abstract fun vehicleOrderDao(): VehicleOrderDao

    companion object {
        @Volatile
        private var instance: VehicleOrderDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, VehicleOrderDatabase::class.java, "vehicle_order_db").build()
    }
}