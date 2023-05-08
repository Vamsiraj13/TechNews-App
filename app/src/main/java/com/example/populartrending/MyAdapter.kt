package com.example.populartrending

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MyAdapter(private val listener: MyItemSelected, private val items: ArrayList<DataClass>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        val viewHolder = MyViewHolder(view)
        view.setOnClickListener{
            listener.onItemSelected(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleView.text = items[position].title
        Glide.with(holder.itemView.context).load(items[position].urlToImage).into(holder.imageView)
        holder.textView.text = items[position].description
        holder.textView1.text = "- " + items[position].author
        holder.textView2.text = items[position].publishedAt
    }

    override fun getItemCount(): Int {
        return items.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleView: TextView = itemView.findViewById(R.id.title)
        val imageView: ImageView = itemView.findViewById(R.id.thumbnail)
        val textView: TextView = itemView.findViewById(R.id.description)
        val textView1: TextView = itemView.findViewById(R.id.author)
        val textView2: TextView = itemView.findViewById(R.id.publishedAt)
    }

    fun updateNews(updatedNews: ArrayList<DataClass>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }

}
interface MyItemSelected{
    fun onItemSelected(item: DataClass)
}
