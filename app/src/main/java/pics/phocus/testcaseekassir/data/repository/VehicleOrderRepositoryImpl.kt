package pics.phocus.testcaseekassir.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import pics.phocus.testcaseekassir.data.db.VehicleOrderDao
import pics.phocus.testcaseekassir.data.db.model.VehicleOrder

class VehicleOrderRepositoryImpl(
    private val vehicleOrderDao: VehicleOrderDao
) : VehicleOrderRepository {

    var lastFetchTime: ZonedDateTime = ZonedDateTime.now()

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
        // TODO fetch from remote
    }

    private fun isFetchNeeded(): Boolean {
        return lastFetchTime.isBefore(ZonedDateTime.now().minusMinutes(CACHE_MINUTES))
    }

    companion object {
        const val CACHE_MINUTES = 10L
    }
}