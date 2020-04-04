package com.wreckingball.magicdex.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.wreckingball.magicdex.models.Formats
import com.wreckingball.magicdex.network.ApiError
import com.wreckingball.magicdex.network.ERROR
import com.wreckingball.magicdex.network.FormatsService
import com.wreckingball.magicdex.network.SUCCESS
import retrofit2.Call
import retrofit2.Response

private const val TAG = "FormatsRepository"

class FormatsRepository(private val formatsService: FormatsService) {
    lateinit var formats: MutableLiveData<Formats>

    fun fetchFormats() {
        Log.d(TAG, "Network call https://api.magicthegathering.io/v1/formats")
        val call = formatsService.getFormats()
        call.enqueue(object : retrofit2.Callback<Formats> {
            override fun onResponse(call: Call<Formats>, response: Response<Formats>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Network response successful")
                    if (response.body() != null) {
                        Log.d(TAG, "Successfully fetched formats")
                        val fetchedFormats = response.body()
                        fetchedFormats?.let { theFormats ->
                            theFormats.status = SUCCESS
                            formats.value = theFormats
                        }
                    } else {
                        Log.d(TAG, "Failed fetching formats")
                        formats.value?.status = ERROR
                        formats.value?.message = "Failed fetching formats"
                    }
                } else {
                    val errorStr = response.errorBody()?.string()
                    Log.d(TAG, "Network response unsuccessful: $errorStr")
                    try {
                        val message: ApiError = Gson().fromJson(errorStr, ApiError::class.java)
                        Log.d(TAG, "Network response unsuccessful: $message")
                        formats.value?.status = ERROR
                        formats.value?.message = "Error code ${message.status}: ${message.message}"
                    } catch (e: Exception) {
                        Log.d(TAG, "Gson failed", e)
                        formats.value?.status = ERROR
                        formats.value?.message = "Gson failed"
                    }
                }
            }

            override fun onFailure(call: Call<Formats>, t: Throwable) {
                Log.d(TAG, "Call failed", t)
                val error = t.message
                formats.value?.status = ERROR
                if (error != null) {
                    formats.value?.message = error
                } else {
                    formats.value?.message = "Call failed"
                }
            }
        })
    }
}