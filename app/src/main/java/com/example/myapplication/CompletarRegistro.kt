package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.JwtHelper
import com.example.myapplication.io.ApiService
import com.example.myapplication.io.CompleteRegisterRequest
import com.example.myapplication.io.UsuarioId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompletarRegistro : AppCompatActivity() {

    private val apiService : ApiService by lazy {
        ApiService.create()
    }

    private val jwtHelper: JwtHelper by lazy {
        JwtHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completar_registro2)

        val btnGoMenu = findViewById<Button>(R.id.button2)
        btnGoMenu.setOnClickListener{
            performCompleteRegister()
        }
    }

    private fun goToMenu(){
        val i = Intent(this, Menu::class.java)
        startActivity(i)
        finish()
    }

    private fun performCompleteRegister(){
        val etNombre = findViewById<EditText>(R.id.etNombre).text.toString()
        val etTelefono = findViewById<EditText>(R.id.etTelefono).text.toString()
        val etDpto = findViewById<EditText>(R.id.etDepartamento).text.toString()
        val etPuesto = findViewById<EditText>(R.id.etPuesto).text.toString()

        val usuarioId = UsuarioId(id = jwtHelper.getId())

        val registerRequest = CompleteRegisterRequest(
            usuario = usuarioId,
            nombre = etNombre,
            telefono = etTelefono,
            departamento = etDpto,
            puesto = etPuesto
        )

        val authHeader = "Bearer ${jwtHelper.getToken()}"

        val call = apiService.completeRegister(authHeader, registerRequest)
        call.enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {
                val registerResponse = response.body()
                if(response.isSuccessful) {
                    if (registerResponse == null) {
                        Toast.makeText(
                            applicationContext,
                            "Se produjo un error en el servidor (onResponse)",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    Toast.makeText(applicationContext, "Informacion actualizada correctamente", Toast.LENGTH_SHORT).show()
                    goToMenu()

                } else {
                    // Añade aquí el manejo del caso en el que la respuesta HTTP no es exitosa
                    Toast.makeText(applicationContext, "Error actualizando datos de usuario", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("API_CALL", "Error en onFailure(): ${t.message}")

                Toast.makeText(applicationContext, "Se produjo un error en el servidor", Toast.LENGTH_SHORT).show()

            }

        })

    }
}