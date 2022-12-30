package binar.finalproject.binair.admin.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import binar.finalproject.binair.admin.data.formatDateFromISO
import binar.finalproject.binair.admin.data.formatRupiah
import binar.finalproject.binair.admin.data.response.TransactionGetAllTransaction
import binar.finalproject.binair.admin.databinding.ItemHistoryTransactionBinding

@RequiresApi(Build.VERSION_CODES.O)
class TransactionAdapter (private val listTransaction : List<TransactionGetAllTransaction?>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>(){
    var onClick: ((TransactionGetAllTransaction) -> Unit)? = null

    class ViewHolder(
        private val binding : ItemHistoryTransactionBinding,
        private var onClick : (((TransactionGetAllTransaction) -> Unit)?)
    ):RecyclerView.ViewHolder(binding.root){
        fun bind(item : TransactionGetAllTransaction){
            binding.transaction = item
            binding.tvDate.text = item.date?.let { formatDateFromISO(it) }
            binding.tvTotalPrice.text = item.amounts?.let { formatRupiah(it) }
            binding.cvTransaction.setOnClickListener{
                onClick?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemHistoryTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listTransaction[position]?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = listTransaction.size
}