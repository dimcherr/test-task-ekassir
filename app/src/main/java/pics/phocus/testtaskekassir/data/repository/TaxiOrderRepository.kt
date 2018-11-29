package pics.phocus.testtaskekassir.data.repository

import pics.phocus.testtaskekassir.data.db.model.TaxiOrder

interface TaxiOrderRepository {
    suspend fun getTaxiOrders(onNetworkFailure: () -> Unit): List<TaxiOrder>
    suspend fun getTaxiOrderById(id: Int): TaxiOrder
}