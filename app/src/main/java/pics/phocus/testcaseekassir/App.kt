package pics.phocus.testcaseekassir

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import pics.phocus.testcaseekassir.data.db.VehicleOrderDatabase
import pics.phocus.testcaseekassir.data.network.*
import pics.phocus.testcaseekassir.data.repository.VehicleOrderRepository
import pics.phocus.testcaseekassir.data.repository.VehicleOrderRepositoryImpl
import pics.phocus.testcaseekassir.ui.list.ListViewModelFactory

class App : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { VehicleOrderDatabase(instance()) }
        bind() from singleton { instance<VehicleOrderDatabase>().vehicleOrderDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { VehicleOrderApiService(instance()) }
        bind<VehicleOrderNetworkDataSource>() with singleton { VehicleOrderNetworkDataSourceImpl(instance()) }
        bind<VehicleOrderRepository>() with singleton { VehicleOrderRepositoryImpl(instance(), instance()) }
        bind() from provider { ListViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
    }
}