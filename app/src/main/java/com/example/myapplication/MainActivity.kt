package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.util.PreferenceHelper


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val tvGoLogin = findViewById<ImageView>(R.id.imageView7)
        tvGoLogin.setOnClickListener{
            goToLogin()
        }
    }
    private fun goToLogin(){
        val i = Intent(this, Login::class.java)
        startActivity(i)
    }
}