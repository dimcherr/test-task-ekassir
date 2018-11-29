package pics.phocus.testtaskekassir.ui.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pics.phocus.testtaskekassir.data.repository.TaxiOrderRepository

class TaxiOrderListViewModel(private val repository: TaxiOrderRepository) : ViewModel() {
    fun getTaxiOrders() =
        GlobalScope.async {
            repository.getTaxiOrders(networkFailureListener)
        }

    private var networkFailureListener = {}

    fun setOnNetworkFailureListener(listener: () -> Unit) {
        networkFailureListener = listener
    }
}