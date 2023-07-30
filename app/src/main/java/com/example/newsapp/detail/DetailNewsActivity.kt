package com.example.newsapp.detail

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.remote.NewsResponse

class DetailNewsActivity : AppCompatActivity() {

    companion object {
        const val DETAIL_NEWS = "DETAIL_NEWS"
    }

    var modelArticle: NewsResponse? = null
    var strNewsURL: String? = null
    var strTitle: String? = null
    var strSubTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        progressBar.max = 100

        //get data intent
        modelArticle = intent.getParcelableExtra(DETAIL_NEWS)
        if (modelArticle != null) {
            strNewsURL = modelArticle?.url
            strTitle = modelArticle?.title
            strSubTitle = modelArticle?.description

            val tvTitle = findViewById<TextView>(R.id.tvTitle)
            val tvSubTitle = findViewById<TextView>(R.id.tvSubTitle)
            val imageShare = findViewById<ImageView>(R.id.imageShare)

            // Check if the title and description are not null before using them
            tvTitle.text = strTitle ?: ""
            tvSubTitle.text = strSubTitle ?: ""

            //share news
            imageShare.setOnClickListener {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "text/plain"
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                share.putExtra(Intent.EXTRA_TEXT, strNewsURL)
                startActivity(Intent.createChooser(share, "Bagikan ke : "))
            }

            //show news
            showWebView()
        }
    }

    private fun showWebView() {
        val webView = findViewById<WebView>(R.id.webView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.loadUrl(strNewsURL!!)

        progressBar.progress = 0

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, newUrl: String): Boolean {
                view.loadUrl(newUrl)
                progressBar.progress = 0
                return true
            }

            override fun onPageStarted(view: WebView, urlStart: String, favicon: Bitmap?) {
                strNewsURL = urlStart
                invalidateOptionsMenu()
            }

            override fun onPageFinished(view: WebView, urlPage: String) {
                progressBar.visibility = View.GONE
                invalidateOptionsMenu()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}



