package pics.phocus.testcaseekassir.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pics.phocus.testcaseekassir.data.db.model.VehicleOrder

@Dao
interface VehicleOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(order: VehicleOrder)

    @Query("SELECT * FROM vehicle_order")
    fun loadOrders(): LiveData<List<VehicleOrder>>
}