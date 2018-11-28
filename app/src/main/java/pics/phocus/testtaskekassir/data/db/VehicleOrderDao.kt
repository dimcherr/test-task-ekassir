package pics.phocus.testtaskekassir.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pics.phocus.testtaskekassir.data.db.model.VehicleOrder

@Dao
interface VehicleOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(orders: List<VehicleOrder>)

    @Query("SELECT * FROM vehicle_order ORDER BY orderTime DESC")
    fun loadOrders(): LiveData<List<VehicleOrder>>
}