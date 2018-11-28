package pics.phocus.testtaskekassir.ui.list.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pics.phocus.testtaskekassir.R
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder

class ListRecyclerViewAdapter(private val onClick: (TaxiOrder) -> Unit) :
    RecyclerView.Adapter<ListViewHolder>() {

    private var items: List<TaxiOrder>? = null

    fun loadItems(items: List<TaxiOrder>) {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listItem_taxiOrder, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val items = this.items ?: return
        val item = items[position]
        holder.textViewEndAddress.text = item.endAddress.address
        holder.textViewStartAddress.text = item.startAddress.address
        holder.textViewPrice.text = item.price.amount.toString()
        holder.textViewRideDate.text = item.orderTime
        with(holder.view) {
            setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0
}
