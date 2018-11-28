package pics.phocus.testtaskekassir.data.repository

import androidx.lifecycle.LiveData
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder

interface TaxiOrderRepository {
    suspend fun getTaxiOrders(): LiveData<out List<TaxiOrder>>
    suspend fun getTaxiOrderById(id: Int): LiveData<out TaxiOrder>
}