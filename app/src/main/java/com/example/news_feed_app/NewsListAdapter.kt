package com.example.news_feed_app

import android.net.sip.SipSession
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener:NewsItemClicked): Adapter<NewsViewHolder>() {
    private val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.newslist,parent,false)
        val viewHolder=NewsViewHolder(view)
        view.setOnClickListener(){
           listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var currentItem=items[position]
        holder.titleView.text=currentItem.title
        holder.titleView.text=currentItem.author
        Glide.with(holder.itemView).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
        TODO("Not yet implemented")
    }
    fun updateNews(updatedNews:ArrayList<News>)
    {
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }
}

interface NewsItemClicked {
        fun onItemClicked(items:News)
}

class NewsViewHolder(itemView: View) : ViewHolder(itemView) {
    val titleView:TextView=itemView.findViewById(R.id.title)
    val image:ImageView=itemView.findViewById(R.id.image)
    val author:TextView=itemView.findViewById(R.id.author)
}