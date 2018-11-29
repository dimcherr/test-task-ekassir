package pics.phocus.testtaskekassir.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder
import pics.phocus.testtaskekassir.data.repository.TaxiOrderRepository

class TaxiOrderDetailsViewModel(private val repository: TaxiOrderRepository) : ViewModel() {

    var id: Int = -1

    private val orderMutableLiveData = MutableLiveData<TaxiOrder>()

    fun getTaxiOrder(): Deferred<LiveData<TaxiOrder>> = GlobalScope.async {
        orderMutableLiveData.postValue(repository.getTaxiOrderById(id))
        orderMutableLiveData
    }
}