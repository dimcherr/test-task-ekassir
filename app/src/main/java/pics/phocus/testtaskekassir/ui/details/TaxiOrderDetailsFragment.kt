package pics.phocus.testtaskekassir.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment__taxi_order_details.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import pics.phocus.testtaskekassir.R
import pics.phocus.testtaskekassir.images.ImageLoader
import pics.phocus.testtaskekassir.ui.base.ScopedFragment

class TaxiOrderDetailsFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    companion object {
        fun newInstance() = TaxiOrderDetailsFragment()
    }

    private val viewModelFactory by instance<TaxiOrderDetailsViewModelFactory>()
    private lateinit var viewModel: TaxiOrderDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment__taxi_order_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TaxiOrderDetailsViewModel::class.java)
        val safeArgs = TaxiOrderDetailsFragmentArgs.fromBundle(arguments)
        viewModel.id = safeArgs.id
        bindUI()
    }

    private fun bindUI() = launch {
        val taxiOrder = viewModel.taxiOrder.await()
        taxiOrder.observe(this@TaxiOrderDetailsFragment, Observer { order ->
            textView_driver_name.text = order.vehicle.driverName
            textView_start_address.text = order.startAddress.address
            textView_end_address.text = order.endAddress.address
            textView_price.text = order.price.amount.toString()
            textView_model_name.text = order.vehicle.modelName
            textView_reg_number.text = order.vehicle.regNumber
            ImageLoader.with(this@TaxiOrderDetailsFragment).load("http://www.roxiemobile.ru/careers/test/images/${order.vehicle.photo}").into(imageView_photo)
        })
    }
}
