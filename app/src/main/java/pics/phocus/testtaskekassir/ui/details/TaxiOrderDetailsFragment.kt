package pics.phocus.testtaskekassir.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_taxiOrderDetails.*
import pics.phocus.testtaskekassir.R

class TaxiOrderDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = TaxiOrderDetailsFragment()
    }

    private lateinit var viewModel: TaxiOrderDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_taxiOrderDetails, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TaxiOrderDetailsViewModel::class.java)

        arguments.let {
            val safeArgs = DetailsFragmentArgs.fromBundle(it)
            textView_driver_name.text = "${safeArgs.id}"
        }
    }

}
