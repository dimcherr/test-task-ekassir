package pics.phocus.testtaskekassir.ui.list.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import pics.phocus.testtaskekassir.R
import pics.phocus.testtaskekassir.data.db.model.TaxiOrder
import pics.phocus.testtaskekassir.internal.PriceFormatter

class ListRecyclerViewAdapter(private val onClick: (TaxiOrder) -> Unit) :
    RecyclerView.Adapter<ListViewHolder>() {

    private var items: List<TaxiOrder>? = null

    fun loadItems(items: List<TaxiOrder>) {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item__taxi_order, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val items = this.items ?: return
        val item = items[position]
        holder.textViewEndAddress.text = item.endAddress.address
        holder.textViewStartAddress.text = item.startAddress.address
        holder.textViewPrice.text = PriceFormatter.format(item.price.amount, item.price.currency)
        holder.textViewRideDate.text = ZonedDateTime.parse(item.orderTime).format(DateTimeFormatter.ofPattern("dd.MM.yyyy в HH:mm"))
        with(holder.view) {
            setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0
}
