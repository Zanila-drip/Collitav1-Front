package com.programobil.collitav1_front.security

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object TokenManager {
    private const val TOKEN_KEY = "auth_token"
    private lateinit var prefs: SharedPreferences

    fun initialize(context: Context) {
        Log.d("TokenManager", "Inicializando TokenManager")
        try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            prefs = EncryptedSharedPreferences.create(
                context,
                "secure_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            Log.d("TokenManager", "TokenManager inicializado correctamente")
        } catch (e: Exception) {
            Log.e("TokenManager", "Error al inicializar TokenManager", e)
            throw e
        }
    }

    fun saveToken(token: String) {
        Log.d("TokenManager", "Guardando token")
        try {
            prefs.edit().putString(TOKEN_KEY, token).apply()
            Log.d("TokenManager", "Token guardado correctamente")
        } catch (e: Exception) {
            Log.e("TokenManager", "Error al guardar token", e)
            throw e
        }
    }

    fun getToken(): String? {
        Log.d("TokenManager", "Obteniendo token")
        try {
            val token = prefs.getString(TOKEN_KEY, null)
            Log.d("TokenManager", if (token != null) "Token encontrado" else "No hay token guardado")
            return token
        } catch (e: Exception) {
            Log.e("TokenManager", "Error al obtener token", e)
            throw e
        }
    }

    fun clearToken() {
        Log.d("TokenManager", "Limpiando token")
        try {
            prefs.edit().remove(TOKEN_KEY).apply()
            Log.d("TokenManager", "Token limpiado correctamente")
        } catch (e: Exception) {
            Log.e("TokenManager", "Error al limpiar token", e)
            throw e
        }
    }
} 