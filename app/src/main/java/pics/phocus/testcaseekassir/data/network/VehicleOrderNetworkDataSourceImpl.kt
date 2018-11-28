package pics.phocus.testcaseekassir.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pics.phocus.testcaseekassir.data.db.model.VehicleOrder
import pics.phocus.testcaseekassir.internal.NoConnectivityException

class VehicleOrderNetworkDataSourceImpl(
    private val apiService: VehicleOrderApiService
) : VehicleOrderNetworkDataSource {

    private val mDownloadedVehicleOrders = MutableLiveData<List<VehicleOrder>>()

    override val downloadedVehicleOrders: LiveData<List<VehicleOrder>>
        get() = mDownloadedVehicleOrders

    override suspend fun fetchVehicleOrders() {
        try {
            val fetchedVehicleOrders = apiService.getVehicleOrders().await()
            mDownloadedVehicleOrders.postValue(fetchedVehicleOrders)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
        }
    }
}