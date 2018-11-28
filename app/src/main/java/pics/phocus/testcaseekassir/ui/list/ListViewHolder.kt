package pics.phocus.testcaseekassir.ui.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_orders.view.*

class ListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val textViewStartAddress: TextView = view.textView_start_address
    val textViewEndAddress: TextView = view.textView_end_address
    val textViewPrice: TextView = view.textView_price
    val textViewRideDate: TextView = view.textView_ride_date
}