package pics.phocus.testtaskekassir

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import pics.phocus.testtaskekassir.data.db.TaxiOrderDatabase
import pics.phocus.testtaskekassir.data.network.*
import pics.phocus.testtaskekassir.data.repository.TaxiOrderRepository
import pics.phocus.testtaskekassir.data.repository.TaxiOrderRepositoryImpl
import pics.phocus.testtaskekassir.ui.details.TaxiOrderDetailsViewModelFactory
import pics.phocus.testtaskekassir.ui.list.TaxiOrderListViewModelFactory

class App : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { TaxiOrderDatabase(instance()) }
        bind() from singleton { instance<TaxiOrderDatabase>().taxiOrderDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { TaxiOrderApiService(instance()) }
        bind<TaxiOrderNetworkDataSource>() with singleton { TaxiOrderNetworkDataSourceImpl(instance()) }
        bind<TaxiOrderRepository>() with singleton { TaxiOrderRepositoryImpl(instance(), instance()) }
        bind() from provider { TaxiOrderListViewModelFactory(instance()) }
        bind() from provider { TaxiOrderDetailsViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
    }
}