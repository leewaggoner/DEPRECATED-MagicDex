package com.wreckingball.magicdex.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.wreckingball.magicdex.models.Sets
import com.wreckingball.magicdex.network.ApiError
import com.wreckingball.magicdex.network.ERROR
import com.wreckingball.magicdex.network.SUCCESS
import com.wreckingball.magicdex.network.SetsService
import retrofit2.Call
import retrofit2.Response

private const val TAG = "SetsRepository"

class SetsRepository(private val setsService: SetsService) {
    lateinit var sets: MutableLiveData<Sets>

    fun getSets() {
        Log.d(TAG, "Network call https://api.magicthegathering.io/v1/sets")
        val call = setsService.getSets()
        call.enqueue(object : retrofit2.Callback<Sets> {
            override fun onResponse(call: Call<Sets>, response: Response<Sets>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Network response successful")
                    if (response.body() != null) {
                        Log.d(TAG, "Successfully fetched sets")
                        val fetchedSets = response.body()
                        fetchedSets?.let { theSets ->
                            theSets.status = SUCCESS
                            sets.value = theSets
                        }
                    } else {
                        Log.d(TAG, "Failed fetching sets")
                        sets.value?.status = ERROR
                        sets.value?.message = "Failed fetching sets"
                    }
                } else {
                    val errorStr = response.errorBody()?.string()
                    Log.d(TAG, "Network response unsuccessful: $errorStr")
                    try {
                        val message: ApiError = Gson().fromJson(errorStr, ApiError::class.java)
                        Log.d(TAG, "Network response unsuccessful: $message")
                        sets.value?.status = ERROR
                        sets.value?.message = "Error code ${message.status}: ${message.message}"
                    } catch (e: Exception) {
                        Log.d(TAG, "Gson failed", e)
                        sets.value?.status = ERROR
                        sets.value?.message = "Gson failed"
                    }
                }
            }

            override fun onFailure(call: Call<Sets>, t: Throwable) {
                Log.d(TAG, "Call failed", t)
                val error = t.message
                sets.value?.status = ERROR
                if (error != null) {
                    sets.value?.message = error
                } else {
                    sets.value?.message = "Call failed"
                }
            }
        })
    }
}