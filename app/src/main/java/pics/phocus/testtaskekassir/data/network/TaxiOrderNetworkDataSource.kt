package pics.phocus.testtaskekassir.data.network

import androidx.lifecycle.LiveData
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder

interface TaxiOrderNetworkDataSource {
    val downloadedTaxiOrders: LiveData<List<TaxiOrder>>

    suspend fun fetchTaxiOrders()
}