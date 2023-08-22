package com.example.newsapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.remote.ModelNews
import com.example.newsapp.remote.NewsResponse
import com.example.newsapp.retrofit.ApiConfig.getApiClient
import com.example.newsapp.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentSearch : Fragment() {

    companion object {
        const val API_KEY = "d70fa2c3a85849f7944ae01a2d7acf69"
    }

    lateinit var etSearchView: EditText
    lateinit var imageClear: ImageView
    lateinit var linearNews: LinearLayout
    lateinit var rvListNews: RecyclerView

    var strKeywords: String = ""
    var modelArticle: MutableList<NewsResponse> = ArrayList()
    var newsAdapter: NewsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        etSearchView = view.findViewById(R.id.etSearchView)
        imageClear = view.findViewById(R.id.imageClear)
        linearNews = view.findViewById(R.id.linearNews)
        rvListNews = view.findViewById(R.id.rvListNews)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvListNews.layoutManager = LinearLayoutManager(context)
        rvListNews.setHasFixedSize(true)
        imageClear.visibility = View.GONE
        linearNews.visibility = View.GONE

        imageClear.setOnClickListener {
            etSearchView.text.clear()
            modelArticle.clear()
            linearNews.visibility = View.GONE
            imageClear.visibility = View.GONE
        }

        //action search
        etSearchView.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                strKeywords = etSearchView.text.toString()
                if (strKeywords.isEmpty()) {
                    Toast.makeText(context, "Form tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                } else {
                    getListNews(strKeywords)
                }
                val inputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(v.windowToken, 0)
                true
            } else {
                false
            }
        })
    }

    private fun getListNews(strKeywords: String) {
        modelArticle.clear()

        //set api
        val apiInterface = getApiClient().create(ApiService::class.java)
        val call = apiInterface.getNewsSearch(strKeywords, "id", API_KEY)
        call.enqueue(object : Callback<ModelNews> {
            override fun onResponse(call: Call<ModelNews>, response: Response<ModelNews>) {
                if (response.isSuccessful && response.body() != null) {
                    modelArticle = response.body()?.modelArticle as MutableList<NewsResponse>
                    newsAdapter = NewsAdapter(modelArticle, context!!)
                    rvListNews.adapter = newsAdapter
                    newsAdapter?.notifyDataSetChanged()
                    linearNews.visibility = View.VISIBLE
                    imageClear.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<ModelNews>, t: Throwable) {
                Toast.makeText(context, "Oops, jaringan kamu bermasalah.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
