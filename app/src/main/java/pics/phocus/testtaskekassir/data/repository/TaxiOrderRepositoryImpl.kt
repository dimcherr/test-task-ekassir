package pics.phocus.testtaskekassir.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pics.phocus.testtaskekassir.data.db.TaxiOrderDao
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder
import pics.phocus.testtaskekassir.data.network.TaxiOrderNetworkDataSource
import pics.phocus.testtaskekassir.internal.NoConnectivityException

class TaxiOrderRepositoryImpl(
    private val taxiOrderDao: TaxiOrderDao,
    private val networkDataSource: TaxiOrderNetworkDataSource
) : TaxiOrderRepository {
    init {
        networkDataSource.downloadedTaxiOrders.observeForever { newTaxiOrders ->
            persistFetchedTaxiOrders(newTaxiOrders)
        }
    }

    private fun persistFetchedTaxiOrders(fetchedTaxiOrders: List<TaxiOrder>) {
        GlobalScope.launch(Dispatchers.IO) {
            taxiOrderDao.upsert(fetchedTaxiOrders)
        }
    }

    override suspend fun getTaxiOrders(onNetworkFailure: () -> Unit): LiveData<out List<TaxiOrder>> {
        return withContext(Dispatchers.IO) {
            try {
                fetchTaxiOrders()
            } catch (e: NoConnectivityException) {
                onNetworkFailure()
            }
            return@withContext taxiOrderDao.loadOrders()
        }
    }

    override suspend fun getTaxiOrderById(id: Int): LiveData<out TaxiOrder> {
        return withContext(Dispatchers.IO) {
            return@withContext taxiOrderDao.loadOrderById(id)
        }
    }

    @Throws(NoConnectivityException::class)
    private suspend fun fetchTaxiOrders() {
        networkDataSource.fetchTaxiOrders()
    }
}