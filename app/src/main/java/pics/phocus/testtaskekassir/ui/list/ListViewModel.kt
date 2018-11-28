package pics.phocus.testtaskekassir.ui.list

import androidx.lifecycle.ViewModel;
import pics.phocus.testtaskekassir.data.repository.VehicleOrderRepository
import pics.phocus.testtaskekassir.internal.lazyDeferred

class ListViewModel(private val repository: VehicleOrderRepository) : ViewModel() {
    val vehicleOrders by lazyDeferred {
        repository.getVehicleOrders()
    }
}