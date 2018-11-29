package pics.phocus.testtaskekassir.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder
import pics.phocus.testtaskekassir.data.repository.TaxiOrderRepository

class TaxiOrderListViewModel(private val repository: TaxiOrderRepository) : ViewModel() {

    private val taxiOrdersMutableLiveData = MutableLiveData<List<TaxiOrder>>()

    private suspend fun fetchTaxiOrders(): Deferred<List<TaxiOrder>> {
        return GlobalScope.async {
            repository.getTaxiOrders(networkFailureListener)
        }
    }

    suspend fun refreshTaxiOrders() {
        taxiOrdersMutableLiveData.postValue(fetchTaxiOrders().await())
    }

    fun getTaxiOrders(): LiveData<List<TaxiOrder>> {
        return taxiOrdersMutableLiveData
    }

    private var networkFailureListener = {}

    fun setOnNetworkFailureListener(listener: () -> Unit) {
        networkFailureListener = listener
    }
}