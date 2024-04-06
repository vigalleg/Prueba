package com.example.myapplication.io


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val username: String, val password: String)
data class RegisterRequest(val username: String, val password: String, val email: String)
fun provideGson(): Gson {
    val builder = GsonBuilder()
    builder.setLenient()
    return builder.create()
}
interface ApiService {
    @POST(value = "/auth/login")
    fun postLogin(@Body request: LoginRequest): Call<String>

    @POST(value = "/auth/register")
    fun postRegister(@Body request: RegisterRequest): Call<String>

    companion object Factory{
        private const val BASE_URL = "https://lopezgeraghty.com:8080"
        fun create(): ApiService{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .build()
            return retrofit.create((ApiService::class.java))
        }
    }


}