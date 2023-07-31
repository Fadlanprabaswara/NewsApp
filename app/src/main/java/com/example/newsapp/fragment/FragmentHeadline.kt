package com.example.newsapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.remote.ModelNews
import com.example.newsapp.remote.NewsResponse
import com.example.newsapp.remote.Utlis.getCountry
import com.example.newsapp.retrofit.ApiConfig.getApiClient
import com.example.newsapp.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentHeadline : Fragment() {

    companion object {
        const val API_KEY = "d70fa2c3a85849f7944ae01a2d7acf69"
    }

    var strCountry: String? = null
    var modelArticle: MutableList<NewsResponse> = ArrayList()
    var newsAdapter: NewsAdapter? = null
    lateinit var rvListNews: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        rvListNews = view.findViewById<RecyclerView>(R.id.rvListNews) // Corrected initialization

        tvTitle.text = "Berita Utama"

        rvListNews.layoutManager = LinearLayoutManager(context)

        //reload news
        val imageRefresh = view.findViewById<ImageView>(R.id.imageRefresh)
        imageRefresh.setOnClickListener {
            rvListNews.setHasFixedSize(true)
            getListNews()
        }

        //get news
        getListNews()
    }

    //set api
    private fun getListNews() {
        //get country
        strCountry = getCountry()

        //set api
        val apiInterface = getApiClient().create(ApiService::class.java)
        val call = apiInterface.getHeadlines(strCountry, API_KEY)
        call.enqueue(object : Callback<ModelNews> {
            override fun onResponse(call: Call<ModelNews>, response: Response<ModelNews>) {
                if (response.isSuccessful && response.body() != null) {
                    modelArticle = response.body()?.modelArticle as MutableList<NewsResponse>
                    context?.let {
                        newsAdapter = NewsAdapter(modelArticle, it)
                        rvListNews.adapter = newsAdapter
                    }
                    newsAdapter?.notifyDataSetChanged()
                    rvListNews.setHasFixedSize(true)
                }
            }

            override fun onFailure(call: Call<ModelNews>, t: Throwable) {
                context?.let {
                    Toast.makeText(it, "Oops, jaringan kamu bermasalah.", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

