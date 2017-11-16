package com.compunetlimited.ogan.ebsu

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by belema on 9/11/17.
 */

object ServiceGenerator {

    private val BASE_URL = "https://ebsuportal.azurewebsites.net/"


    private val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = builder.build()

    var apiMethods = retrofit.create(ApiMethods::class.java)

}

