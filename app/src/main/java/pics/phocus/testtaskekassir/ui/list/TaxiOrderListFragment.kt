package pics.phocus.testtaskekassir.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment__taxi_order_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import pics.phocus.testtaskekassir.R
import pics.phocus.testtaskekassir.ui.base.ScopedFragment
import pics.phocus.testtaskekassir.ui.list.recycler.ListItemOffsetDecoration
import pics.phocus.testtaskekassir.ui.list.recycler.ListRecyclerViewAdapter

class TaxiOrderListFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()

    private val viewModelFactory by instance<TaxiOrderListViewModelFactory>()
    private lateinit var viewModel: TaxiOrderListViewModel

    private val adapter by lazy {
        ListRecyclerViewAdapter {
            navigateToDetails(it.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment__taxi_order_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TaxiOrderListViewModel::class.java)
        bindUI()
    }

    private fun navigateToDetails(id: Int) {
        val nextAction = TaxiOrderListFragmentDirections.actionToDetails()
        nextAction.setId(id)
        Navigation.findNavController(recycler_view).navigate(nextAction)
    }

    private fun bindUI() = launch {
        refresh_layout.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.Main) {
                initTaxiOrders()
                refresh_layout.isRefreshing = false
            }
        }

        viewModel.setOnNetworkFailureListener {
            Snackbar.make(recycler_view, resources.getString(R.string.network_failure_message), Snackbar.LENGTH_SHORT).show()
        }

        initTaxiOrders()

        // setup recycler view
        recycler_view.let {
            it.layoutManager = LinearLayoutManager(context!!)
            it.adapter = adapter
            it.addItemDecoration(
                ListItemOffsetDecoration(
                    context!!,
                    R.dimen.list_spacing
                )
            )
        }
    }

    private suspend fun initTaxiOrders() {
        val taxiOrders = viewModel.getTaxiOrders().await()
        taxiOrders.observe(this@TaxiOrderListFragment, Observer { items ->
            adapter.loadItems(items)
            adapter.notifyDataSetChanged()
        })
    }
}
