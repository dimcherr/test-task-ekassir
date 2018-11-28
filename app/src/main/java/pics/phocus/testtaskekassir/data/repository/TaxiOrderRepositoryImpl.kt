package pics.phocus.testtaskekassir.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import pics.phocus.testtaskekassir.data.db.TaxiOrderDao
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder
import pics.phocus.testtaskekassir.data.network.TaxiOrderNetworkDataSource

class TaxiOrderRepositoryImpl(
    private val taxiOrderDao: TaxiOrderDao,
    private val networkDataSource: TaxiOrderNetworkDataSource
) : TaxiOrderRepository {

    // TODO handle caching
    var lastFetchTime: ZonedDateTime = ZonedDateTime.now().minusHours(2)

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

    override suspend fun getTaxiOrders(): LiveData<out List<TaxiOrder>> {
        return withContext(Dispatchers.IO) {
            initData()
            return@withContext taxiOrderDao.loadOrders()
        }
    }

    override suspend fun getTaxiOrderById(id: Int): LiveData<out TaxiOrder> {
        return withContext(Dispatchers.IO) {
            return@withContext taxiOrderDao.loadOrderById(id)
        }
    }

    private suspend fun initData() {
        if (isFetchNeeded())
            fetchTaxiOrders()
    }

    private suspend fun fetchTaxiOrders() {
        networkDataSource.fetchTaxiOrders()
    }

    private fun isFetchNeeded(): Boolean {
        return lastFetchTime.isBefore(ZonedDateTime.now().minusMinutes(CACHE_MINUTES))
    }

    companion object {
        const val CACHE_MINUTES = 10L
    }
}