package pics.phocus.testtaskekassir.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder

@Dao
interface TaxiOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(orders: List<TaxiOrder>)

    @Query("SELECT * FROM taxi_order ORDER BY orderTime DESC")
    fun loadOrders(): LiveData<List<TaxiOrder>>

    @Query("SELECT * FROM taxi_order WHERE id = :id ORDER BY orderTime DESC")
    fun loadOrderById(id: Int): LiveData<TaxiOrder>
}