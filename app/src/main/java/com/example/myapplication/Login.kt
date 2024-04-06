package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.io.ApiService
import com.example.myapplication.io.LoginRequest
import com.example.myapplication.io.response.LoginResponse
import com.example.myapplication.util.PreferenceHelper
import com.example.myapplication.util.PreferenceHelper.get
import com.example.myapplication.util.PreferenceHelper.set
import com.example.myapplication.util.PreferenceHelper.defaultPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class Login : AppCompatActivity() {


    private val apiService: ApiService by lazy{
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)


        val preferences = PreferenceHelper.defaultPrefs(this)
        if(preferences["jwt",""].contains(".")) {
            goToMenu()
        }
            Toast.makeText(applicationContext, "prueba",Toast.LENGTH_SHORT).show()


        val btnGoMenu=findViewById<Button>(R.id.button)
        btnGoMenu.setOnClickListener{
            performLogin()
        }
    }

    private fun goToMenu(){
        val i = Intent(this, PantallaPrincipal::class.java)
        //createSessionPreference()
        startActivity(i)
        finish()
    }

    private fun createSessionPreference(jwt: String){
        val preferences=PreferenceHelper.defaultPrefs(this)
        preferences ["jwt"]=jwt;
    }

    private fun performLogin(){
        val etEmail = findViewById<EditText>(R.id.et_email).text.toString()
        val etPassword = findViewById<EditText>(R.id.et_password).text.toString()

        val loginRequest = LoginRequest(username = etEmail, password = etPassword)

        val call = apiService.postLogin(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse == null) {
                        Toast.makeText(
                            applicationContext,
                            "Se produjo un error en el servidor",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    if (loginResponse!= null) { //probar también si no loginResponse!=null
                        createSessionPreference(loginResponse.jwt)
                        goToMenu()
                    }
                } else {
                    // Añade aquí el manejo del caso en el que la respuesta HTTP no es exitosa
                    Toast.makeText(applicationContext, "La respuesta HTTP no fue exitosa", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Se produjo un error en el servidor",Toast.LENGTH_SHORT).show()
            }

        })

    }
}
