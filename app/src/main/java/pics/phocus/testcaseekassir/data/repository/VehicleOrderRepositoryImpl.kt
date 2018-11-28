package pics.phocus.testcaseekassir.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import pics.phocus.testcaseekassir.data.db.VehicleOrderDao
import pics.phocus.testcaseekassir.data.db.model.VehicleOrder
import pics.phocus.testcaseekassir.data.network.VehicleOrderNetworkDataSource

class VehicleOrderRepositoryImpl(
    private val vehicleOrderDao: VehicleOrderDao,
    private val networkDataSource: VehicleOrderNetworkDataSource
) : VehicleOrderRepository {

    var lastFetchTime: ZonedDateTime = ZonedDateTime.now().minusHours(2)

    init {
        networkDataSource.downloadedVehicleOrders.observeForever { newVehicleOrders ->
            persistFetchedVehicleOrders(newVehicleOrders)
        }
    }

    private fun persistFetchedVehicleOrders(fetchedVehicleOrders: List<VehicleOrder>) {
        GlobalScope.launch(Dispatchers.IO) {
            vehicleOrderDao.upsert(fetchedVehicleOrders)
        }
    }

    override suspend fun getVehicleOrders(): LiveData<out List<VehicleOrder>> {
        return withContext(Dispatchers.IO) {
            initData()
            return@withContext vehicleOrderDao.loadOrders()
        }
    }

    private suspend fun initData() {
        if (isFetchNeeded())
            fetchVehicleOrders()
    }

    private suspend fun fetchVehicleOrders() {
        networkDataSource.fetchVehicleOrders()
    }

    private fun isFetchNeeded(): Boolean {
        return lastFetchTime.isBefore(ZonedDateTime.now().minusMinutes(CACHE_MINUTES))
    }

    companion object {
        const val CACHE_MINUTES = 10L
    }
}