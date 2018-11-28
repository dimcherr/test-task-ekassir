package pics.phocus.testtaskekassir.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pics.phocus.testtaskekassir.data.repository.TaxiOrderRepository

class TaxiOrderDetailsViewModelFactory(
    private val repository: TaxiOrderRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaxiOrderDetailsViewModel(repository) as T
    }
}