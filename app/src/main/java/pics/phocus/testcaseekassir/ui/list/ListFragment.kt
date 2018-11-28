package pics.phocus.testcaseekassir.ui.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import pics.phocus.clean.ui.base.ScopedFragment
import pics.phocus.testcaseekassir.R

class ListFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()

    private val viewModelFactory by instance<ListViewModelFactory>()
    private lateinit var viewModel: ListViewModel

    private val adapter by lazy {
        ListRecyclerViewAdapter {
            Toast.makeText(context!!, "Click! ${it.startAddress.address}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val vehicleOrders = viewModel.vehicleOrders.await()
        vehicleOrders.observe(this@ListFragment, Observer { items ->
            adapter.loadItems(items)
            adapter.notifyDataSetChanged()
        })

        button_to_details.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_toDetails)
        }

        // setup recycler view
        recycler_view.let {
            it.layoutManager = LinearLayoutManager(context!!)
            it.adapter = adapter
            it.addItemDecoration(ListItemOffsetDecoration(context!!, R.dimen.list_spacing))
        }
    }
}
