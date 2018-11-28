package pics.phocus.testtaskekassir.data.network

import android.util.Log
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

    override suspend fun fetchTaxiOrders() {
        try {
            val fetchedTaxiOrders = apiService.getTaxiOrders().await()
            mDownloadedTaxiOrders.postValue(fetchedTaxiOrders)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
        }
    }
}