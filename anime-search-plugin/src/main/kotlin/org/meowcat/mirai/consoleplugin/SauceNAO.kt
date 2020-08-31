package org.meowcat.mirai.consoleplugin

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SauceNAOService{
    @GET("search.php")
    fun search(){

    }
}