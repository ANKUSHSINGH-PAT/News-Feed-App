package com.example.news_feed_app

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


private lateinit var linearLayoutManager: LinearLayoutManager

class MainActivity : AppCompatActivity(), NewsItemClicked {
    var url: String? = null
    private lateinit var mAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        val items=fetchdata()
        mAdapter=NewsListAdapter(this)
        recyclerView.adapter=mAdapter
    }

    private fun fetchdata(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                mAdapter.updateNews(newsArray)

            },
            Response.ErrorListener {
                // TODO: Handle error
                Toast.makeText(this, "Error ocurred", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }

    override fun onItemClicked(items: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(items.url))
    }

}