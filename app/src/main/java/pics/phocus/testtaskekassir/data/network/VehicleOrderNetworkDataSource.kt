package pics.phocus.testtaskekassir.data.network

import androidx.lifecycle.LiveData
import pics.phocus.testtaskekassir.data.db.model.VehicleOrder

interface VehicleOrderNetworkDataSource {
    val downloadedVehicleOrders: LiveData<List<VehicleOrder>>

    suspend fun fetchVehicleOrders()
}