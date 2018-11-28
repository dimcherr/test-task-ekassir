package pics.phocus.testcaseekassir.data.repository

import androidx.lifecycle.LiveData
import pics.phocus.testcaseekassir.data.db.model.VehicleOrder

interface VehicleOrderRepository {
    suspend fun getVehicleOrders(): LiveData<out List<VehicleOrder>>
}