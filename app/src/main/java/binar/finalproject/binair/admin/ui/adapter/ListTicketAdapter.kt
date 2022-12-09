package binar.finalproject.binair.admin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.databinding.ItemTicketBinding

class ListTicketAdapter(private val listTicket : List<DataTicket>) : RecyclerView.Adapter<ListTicketAdapter.ViewHolder>() {
    var onClick: ((DataTicket) -> Unit)? = null
    class ViewHolder(private val binding : ItemTicketBinding, private var onClick : (((DataTicket) -> Unit)?) ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataTicket) {
            binding.ticket = item
            binding.cvTicket.setOnClickListener {
                onClick?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v,onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTicket[position])
    }

    override fun getItemCount(): Int = listTicket.size
}