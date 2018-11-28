package pics.phocus.testtaskekassir.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pics.phocus.testtaskekassir.data.repository.VehicleOrderRepository

class ListViewModelFactory(
    private val repository: VehicleOrderRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(repository) as T
    }
}