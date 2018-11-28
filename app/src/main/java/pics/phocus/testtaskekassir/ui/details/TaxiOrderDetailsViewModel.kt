package pics.phocus.testtaskekassir.ui.details

import androidx.lifecycle.ViewModel
import pics.phocus.testtaskekassir.data.repository.TaxiOrderRepository
import pics.phocus.testtaskekassir.internal.lazyDeferred

class TaxiOrderDetailsViewModel(private val repository: TaxiOrderRepository) : ViewModel() {

    var id: Int = -1

    val taxiOrder by lazyDeferred {
        repository.getTaxiOrderById(id)
    }
}