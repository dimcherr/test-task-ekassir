package pics.phocus.testtaskekassir.data.repository

import androidx.lifecycle.LiveData
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder
import pics.phocus.testtaskekassir.internal.NoConnectivityException

interface TaxiOrderRepository {
    suspend fun getTaxiOrders(onNetworkFailure: () -> Unit): LiveData<out List<TaxiOrder>>
    suspend fun getTaxiOrderById(id: Int): LiveData<out TaxiOrder>
}