package com.example.newsapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.newsapp.R
import com.example.newsapp.detail.DetailNewsActivity
import com.example.newsapp.remote.NewsResponse
import com.example.newsapp.remote.Utlis.DateFormat
import com.example.newsapp.remote.Utlis.DateTimeHourAgo
import com.makeramen.roundedimageview.RoundedImageView


class NewsAdapter(private val modelArticles: MutableList<NewsResponse>, private val context: Context) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = modelArticles[position]

        if (model.urlToImage == null) {
            holder.imageThumbnail.setImageResource(R.drawable.icon_broken_image)
        } else {
            Glide.with(context)
                .load(model.urlToImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageThumbnail)
        }

        if (model.author == null) {
            holder.tvNameSource.text = model.modelSource?.name
        } else {
            holder.tvNameSource.text = model.author + " \u2022 " + model.modelSource?.name
        }

        holder.tvTimeAgo.text = DateTimeHourAgo(model.publishedAt)
        holder.tvTitleNews.text = model.title
        holder.tvDateTime.text = DateFormat(model.publishedAt)
        holder.frameListNews.setOnClickListener {
            val intent = Intent(context, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.DETAIL_NEWS, modelArticles[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return modelArticles.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var frameListNews: FrameLayout
        var tvTimeAgo: TextView
        var tvNameSource: TextView
        var tvTitleNews: TextView
        var tvDateTime: TextView
        var imageThumbnail: RoundedImageView

        init {
            frameListNews = itemView.findViewById(R.id.frameListNews)
            tvTimeAgo = itemView.findViewById(R.id.tvTimeAgo)
            tvNameSource = itemView.findViewById(R.id.tvNameSource)
            tvTitleNews = itemView.findViewById(R.id.tvTitleNews)
            tvDateTime = itemView.findViewById(R.id.tvDateTime)
            imageThumbnail = itemView.findViewById(R.id.imageThumbnail)
        }
    }


}