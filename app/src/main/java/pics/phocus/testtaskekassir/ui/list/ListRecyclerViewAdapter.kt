package pics.phocus.testtaskekassir.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pics.phocus.testtaskekassir.R
import pics.phocus.testtaskekassir.data.db.model.VehicleOrder

class ListRecyclerViewAdapter(private val onClick: (VehicleOrder) -> Unit) :
    RecyclerView.Adapter<ListViewHolder>() {

    private var items: List<VehicleOrder>? = null

    fun loadItems(items: List<VehicleOrder>) {
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
