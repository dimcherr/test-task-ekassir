package pics.phocus.testtaskekassir.ui.list

import androidx.lifecycle.ViewModel;
import pics.phocus.testtaskekassir.data.repository.TaxiOrderRepository
import pics.phocus.testtaskekassir.internal.lazyDeferred

class TaxiOrderListViewModel(private val repository: TaxiOrderRepository) : ViewModel() {
    val taxiOrders by lazyDeferred {
        repository.getTaxiOrders()
    }
}