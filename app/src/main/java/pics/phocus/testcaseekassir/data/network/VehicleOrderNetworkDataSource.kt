package pics.phocus.testcaseekassir.data.network

import androidx.lifecycle.LiveData
import pics.phocus.testcaseekassir.data.db.model.VehicleOrder

interface VehicleOrderNetworkDataSource {
    val downloadedVehicleOrders: LiveData<List<VehicleOrder>>

    suspend fun fetchVehicleOrders()
}