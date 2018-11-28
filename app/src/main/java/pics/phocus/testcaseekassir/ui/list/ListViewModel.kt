package pics.phocus.testcaseekassir.ui.list

import androidx.lifecycle.ViewModel;
import pics.phocus.testcaseekassir.data.repository.VehicleOrderRepository
import pics.phocus.testcaseekassir.internal.lazyDeferred

class ListViewModel(private val repository: VehicleOrderRepository) : ViewModel() {
    val vehicleOrders by lazyDeferred {
        repository.getVehicleOrders()
    }
}