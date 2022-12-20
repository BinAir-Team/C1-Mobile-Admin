package binar.finalproject.binair.admin.ui.adapter

import android.app.AlertDialog
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.databinding.ItemTicketBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class ListTicketAdapter(private val listTicket : List<DataTicket>) : RecyclerView.Adapter<ListTicketAdapter.ViewHolder>() {
    var onClick: ((DataTicket) -> Unit)? = null
    var editClick: ((DataTicket) -> Unit)? = null
    var deleteClick : ((DataTicket) -> Unit)? = null
    class ViewHolder(private val binding : ItemTicketBinding,
                     private var onClick : (((DataTicket) -> Unit)?),
                     private var editClick : (((DataTicket) -> Unit)?),
                     private var deleteClick : (((DataTicket) -> Unit)?)
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataTicket) {
            try {
                item.date_start = formatDate(item.date_start)
                item.date_end = item.date_end?.let { formatDate(it) }
            }catch (e : Exception){
                e.printStackTrace()
            }
            binding.ticket = item
            binding.cvTicket.setOnClickListener {
                onClick?.invoke(item)
            }
            binding.btnEdit.setOnClickListener{
                editClick?.invoke(item)
            }
            binding.btnDelete.setOnClickListener{
                deleteClick?.invoke(item)
            }
        }

        fun formatDate(date : String) : String {
            try {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val localDate = LocalDate.parse(date, formatter)
                val formatter2 = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy", Locale("id", "ID"))
                return localDate.format(formatter2)
            }catch (e : Exception){
                return date
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v,onClick, editClick,deleteClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTicket[position])
    }

    override fun getItemCount(): Int = listTicket.size
}