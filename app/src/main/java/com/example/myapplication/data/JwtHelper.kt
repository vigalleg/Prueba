package com.example.myapplication.data

import android.content.Context
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.myapplication.util.PreferenceHelper
import com.example.myapplication.util.PreferenceHelper.get

class JwtHelper(private val context: Context){
    private fun getJwt(): String {
        val preferences = PreferenceHelper.defaultPrefs(context)
        return preferences["jwt"]
    }

    private fun decodeJWT(token: String): DecodedJWT {
        return JWT.decode(token)
    }

    public fun getId(): Int {
        val token = getJwt()
        val decodedToken = decodeJWT(token)
        return decodedToken.getClaim("id").asInt()
    }

    public fun getToken(): String {
        return getJwt()
    }
}