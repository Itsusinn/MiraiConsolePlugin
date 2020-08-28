package org.meowcat.mirai.consoleplugin

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class GuessResult(val name:String,val trans:ArrayList<String>)

interface NbnhhshService {

    @Headers("Content-Type:application/json")
    @POST("/api/nbnhhsh/guess")
    fun guess(@Body text:Map<String,String>)
            :Call<ArrayList<GuessResult>>

    companion object Factory{
        fun create():NbnhhshService{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://lab.magiconch.com/")
                .build()
            return retrofit.create(NbnhhshService::class.java)
        }
    }
}

