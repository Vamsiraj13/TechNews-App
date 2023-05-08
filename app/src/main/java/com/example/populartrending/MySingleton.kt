package com.example.populartrending

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL= "https://newsapi.org/"

object NewsSingleton {

    val newsInstance: NewsInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsInstance= retrofit.create(NewsInterface::class.java)
    }

}
const val API_KEY = "0c8f481e14dc45bcb31499501f4533f4"
interface NewsInterface {
    @GET("v2/everything?domains=techcrunch.com,thenextweb.com&apiKey=$API_KEY")
    fun getHeadlines(@Query("page")page: Int): Call<NewsData>
}
