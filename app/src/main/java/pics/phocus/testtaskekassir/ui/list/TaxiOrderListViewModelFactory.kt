package pics.phocus.testtaskekassir.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pics.phocus.testtaskekassir.data.repository.TaxiOrderRepository

class TaxiOrderListViewModelFactory(
    private val repository: TaxiOrderRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaxiOrderListViewModel(repository) as T
    }
}