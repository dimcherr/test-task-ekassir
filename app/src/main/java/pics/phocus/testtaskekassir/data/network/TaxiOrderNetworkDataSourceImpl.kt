package pics.phocus.testtaskekassir.data.network

import pics.phocus.testtaskekassir.data.db.model.TaxiOrder
import pics.phocus.testtaskekassir.internal.NoConnectivityException

class TaxiOrderNetworkDataSourceImpl(
    private val apiService: TaxiOrderApiService
) : TaxiOrderNetworkDataSource {

    @Throws(NoConnectivityException::class)
    override suspend fun fetchTaxiOrders(): List<TaxiOrder> {
        return apiService.getTaxiOrders().await()
    }
}