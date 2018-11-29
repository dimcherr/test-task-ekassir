package pics.phocus.testtaskekassir.data.network

import androidx.lifecycle.LiveData
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder
import pics.phocus.testtaskekassir.internal.NoConnectivityException

interface TaxiOrderNetworkDataSource {
    val downloadedTaxiOrders: LiveData<List<TaxiOrder>>

    @Throws(NoConnectivityException::class)
    suspend fun fetchTaxiOrders()
}