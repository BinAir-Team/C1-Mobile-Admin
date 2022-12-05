package binar.finalproject.binair.admin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.finalproject.binair.admin.data.model.News
import binar.finalproject.binair.admin.databinding.HeadlineNewsBinding

class HeadlineViewPager(var headlineNewsList: ArrayList<News>): RecyclerView.Adapter<HeadlineViewPager.ViewHolder>() {

    class ViewHolder(val binding: HeadlineNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(img : Int){

//            binding.tvTitleHeadline.text = title
//            binding.tvDateHeadline.text = date
            binding.ivHeadline.setImageResource(img)
//            binding.tvEditorHeadline.text = editor


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = HeadlineNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(headlineNewsList[position].imgUrl)
    }

    override fun getItemCount(): Int {
        return headlineNewsList.size
    }

    fun setHeadlineNewsData(headlineNewsList: ArrayList<News>){
        this.headlineNewsList = headlineNewsList
    }
}