package org.meowcat.mirai.consoleplugin
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

data class SearchResponse(val RawDocsCount:Int,val docs:ArrayList<Doc>)
data class Doc(val from:Float,
               val to:Float,
               val at:Float,
               val episode:String,
               val anime:String,
               val is_adult:Boolean,
               val season:String,
               val title_chinese:String,
               val similarity:Float){
    override fun toString(): String {
        return "Doc(from=$from, to=$to, at=$at, episode='$episode', anime='$anime', is_adult=$is_adult, season='$season', title_chinese='$title_chinese', similarity=$similarity)"
    }
}
interface TraceMoeService{

    @GET("search")
    fun search(@Query("url") url:String): Call<SearchResponse>

    companion object Factory{
        fun create():TraceMoeService{
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://trace.moe/api/")
                    .build()
            return retrofit.create(TraceMoeService::class.java)
        }
    }
}
