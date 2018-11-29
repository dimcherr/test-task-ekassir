package pics.phocus.testtaskekassir.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder
import pics.phocus.testtaskekassir.internal.NoConnectivityException

class TaxiOrderNetworkDataSourceImpl(
    private val apiService: TaxiOrderApiService
) : TaxiOrderNetworkDataSource {

    private val mDownloadedTaxiOrders = MutableLiveData<List<TaxiOrder>>()

    override val downloadedTaxiOrders: LiveData<List<TaxiOrder>>
        get() = mDownloadedTaxiOrders

    @Throws(NoConnectivityException::class)
    override suspend fun fetchTaxiOrders() {
        val fetchedTaxiOrders = apiService.getTaxiOrders().await()
        mDownloadedTaxiOrders.postValue(fetchedTaxiOrders)
    }
}