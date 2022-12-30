@file:Suppress("KotlinDeprecation")

package binar.finalproject.binair.admin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.finalproject.binair.admin.data.response.TravelerGetAllTransaction
import binar.finalproject.binair.admin.databinding.ItemPasanggerBinding


class PassangerAdapter (private val listPassanger : List<TravelerGetAllTransaction>) : RecyclerView.Adapter<PassangerAdapter.ViewHolder>(){

    class ViewHolder(private val binding : ItemPasanggerBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item : TravelerGetAllTransaction){
            binding.traveler = item
            binding.tvType.text = if(item.type.equals("adult")) "Dewasa" else "Anak"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemPasanggerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listPassanger[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = listPassanger.size
}