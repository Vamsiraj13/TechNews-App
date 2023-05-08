package com.example.populartrending

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.populartrending.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), MyItemSelected {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsArray: ArrayList<DataClass>
    private lateinit var mAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        //mAdapter = MyAdapter(this)
        //recyclerView.adapter = mAdapter
    }

    private fun fetchData(){
        //val url = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=0c8f481e14dc45bcb31499501f4533f4"
        //val jsonRequest = JsonObjectRequest(Request.Method.GET,url,null,
        //    {
        //        val jsonArray = it.getJSONArray("articles")
        //        for(i in 0 until jsonArray.length()){
        //            val jsonObject = jsonArray.getJSONObject(i)
        //            val news = DataClass(
        //                jsonObject.getString("title"),
        //                jsonObject.getString("author"),
        //                jsonObject.getString("url"),
        //                jsonObject.getString("urlToImage"),
        //                jsonObject.getString("publishedAt")
        //            )
        //            newsArray.add(news)
        //        }
        //        mAdapter.updateNews(newsArray)
        //    },
        //    {
        //
        //    })
        //MySingleton.getInstance(this).addToRequestQueue(jsonRequest)
        val news: Call<NewsData> = NewsSingleton.newsInstance.getHeadlines(1)
        news.enqueue(object: Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {

                mAdapter= MyAdapter(this@MainActivity,
                    response.body()!!.articles as ArrayList<DataClass>
                )
                recyclerView.adapter= mAdapter
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                Log.d("oops", "failure!")
            }

        })
    }

    override fun onItemSelected(item: DataClass) {
        val intent = CustomTabsIntent.Builder().build()
         intent.launchUrl(this@MainActivity, Uri.parse(item.url))
    }
}