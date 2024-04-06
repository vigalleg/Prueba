package com.example.myapplication.io


import com.example.myapplication.io.response.LoginResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

data class LoginRequest(val username: String, val password: String)
interface ApiService {
    @POST(value = "/auth/login")
    fun postLogin(@Body request: LoginRequest): Call<LoginResponse>

    companion object Factory{
        private const val BASE_URL = "https://lopezgeraghty.com:8080"
        fun create(): ApiService{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create((ApiService::class.java))
        }
    }
}