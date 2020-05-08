package pl.qbix.pjatk.prm.projekt1.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.qbix.pjatk.prm.projekt1.R
import pl.qbix.pjatk.prm.projekt1.persistence.DebtInfo

class DebtListAdapter(
    var data: List<DebtInfo>,
    val delete: (Int) -> Unit
) : RecyclerView.Adapter<DebtItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtItemViewHolder {
        return DebtItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.debt_list_item_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DebtItemViewHolder, position: Int) {
        holder.refreshData(data[position]) {delete(data[position].id)}
    }
}