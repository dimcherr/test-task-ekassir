package pics.phocus.testtaskekassir.data.repository

import androidx.lifecycle.LiveData
import pics.phocus.testtaskekassir.data.db.model.VehicleOrder

interface VehicleOrderRepository {
    suspend fun getVehicleOrders(): LiveData<out List<VehicleOrder>>
}