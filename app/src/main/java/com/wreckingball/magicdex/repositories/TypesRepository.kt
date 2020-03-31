package com.wreckingball.magicdex.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.wreckingball.magicdex.models.Subtypes
import com.wreckingball.magicdex.models.Supertypes
import com.wreckingball.magicdex.models.Types
import com.wreckingball.magicdex.network.*
import retrofit2.Call
import retrofit2.Response

private const val MAX_FETCHES = 3
private const val TAG = "TypesRepository"

class TypesRepository(private val supertypesService: SupertypesService,
                      private val typesService: TypesService,
                      private val subtypesService: SubtypesService) {
    var networkStatus: MutableLiveData<NetworkStatus> =MutableLiveData(NetworkStatus())
    var supertypes = MutableLiveData<Supertypes>()
    var types = MutableLiveData<Types>()
    var subtypes = MutableLiveData<Subtypes>()
    private var successCount = 0

    fun fetchSupertypes() {
        Log.d(TAG, "Network call https://api.magicthegathering.io/v1/supertypes")
        val call = supertypesService.getSupertypes()
        call.enqueue(object : retrofit2.Callback<Supertypes> {
            override fun onResponse(call: Call<Supertypes>, response: Response<Supertypes>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Network response successful")
                    if (response.body() != null) {
                        Log.d(TAG, "Successfully fetched supertypes")
                        val fetchedSupertypes = response.body()
                        fetchedSupertypes?.let { theSupertypes ->
                            updateNetworkStatus(true, "")
                            supertypes.value = theSupertypes
                        }
                    } else {
                        Log.d(TAG, "Failed fetching supertypes")
                        updateNetworkStatus(true, "Failed fetching supertypes")
                    }
                } else {
                    val errorStr = response.errorBody()?.string()
                    Log.d(TAG, "Network response unsuccessful: $errorStr")
                    try {
                        val message: ApiError = Gson().fromJson(errorStr, ApiError::class.java)
                        Log.d(TAG, "Network response unsuccessful: $message")
                        updateNetworkStatus(false, "Error code ${message.status}: ${message.message}")
                    } catch (e: Exception) {
                        Log.d(TAG, "Gson failed", e)
                        updateNetworkStatus(false, "Gson failed")
                    }
                }
            }

            override fun onFailure(call: Call<Supertypes>, t: Throwable) {
                Log.d(TAG, "Call failed", t)
                var error = t.message
                if (error == null) {
                    error = "Call failed"
                }
                updateNetworkStatus(false, error)
            }
        })
    }

    fun fetchTypes() {
        Log.d(TAG, "Network call https://api.magicthegathering.io/v1/types")
        val call = typesService.getTypes()
        call.enqueue(object : retrofit2.Callback<Types> {
            override fun onResponse(call: Call<Types>, response: Response<Types>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Network response successful")
                    if (response.body() != null) {
                        Log.d(TAG, "Successfully fetched types")
                        val fetchedTypes = response.body()
                        fetchedTypes?.let { theTypes ->
                            updateNetworkStatus(true, "")
                            types.value = theTypes
                        }
                    } else {
                        Log.d(TAG, "Failed fetching types")
                        updateNetworkStatus(true, "Failed fetching types")
                    }
                } else {
                    val errorStr = response.errorBody()?.string()
                    Log.d(TAG, "Network response unsuccessful: $errorStr")
                    try {
                        val message: ApiError = Gson().fromJson(errorStr, ApiError::class.java)
                        Log.d(TAG, "Network response unsuccessful: $message")
                        updateNetworkStatus(false, "Error code ${message.status}: ${message.message}")
                    } catch (e: Exception) {
                        Log.d(TAG, "Gson failed", e)
                        updateNetworkStatus(false, "Gson failed")
                    }
                }
            }

            override fun onFailure(call: Call<Types>, t: Throwable) {
                Log.d(TAG, "Call failed", t)
                var error = t.message
                if (error == null) {
                    error = "Call failed"
                }
                updateNetworkStatus(false, error)
            }
        })
    }

    fun fetchSubtypes() {
        Log.d(TAG, "Network call https://api.magicthegathering.io/v1/subtypes")
        val call = subtypesService.getSubtypes()
        call.enqueue(object : retrofit2.Callback<Subtypes> {
            override fun onResponse(call: Call<Subtypes>, response: Response<Subtypes>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Network response successful")
                    if (response.body() != null) {
                        Log.d(TAG, "Successfully fetched subtypes")
                        val fetchedSubtypes = response.body()
                        fetchedSubtypes?.let { theSubtypes ->
                            updateNetworkStatus(true, "")
                            subtypes.value = theSubtypes
                        }
                    } else {
                        Log.d(TAG, "Failed fetching subtypes")
                        updateNetworkStatus(true, "Failed fetching subtypes")
                    }
                } else {
                    val errorStr = response.errorBody()?.string()
                    Log.d(TAG, "Network response unsuccessful: $errorStr")
                    try {
                        val message: ApiError = Gson().fromJson(errorStr, ApiError::class.java)
                        Log.d(TAG, "Network response unsuccessful: $message")
                        updateNetworkStatus(false, "Error code ${message.status}: ${message.message}")
                    } catch (e: Exception) {
                        Log.d(TAG, "Gson failed", e)
                        updateNetworkStatus(false, "Gson failed")
                    }
                }
            }

            override fun onFailure(call: Call<Subtypes>, t: Throwable) {
                Log.d(TAG, "Call failed", t)
                var error = t.message
                if (error == null) {
                    error = "Call failed"
                }
                updateNetworkStatus(false, error)
            }
        })
    }

    private fun updateNetworkStatus(success: Boolean, errorMsg: String) {
        if (success) {
            successCount++
        } else {
            val status = NetworkStatus()
            status.status = ERROR
            status.message = errorMsg
            networkStatus.value = status
        }

        if (successCount == MAX_FETCHES) {
            val status = NetworkStatus()
            status.status = SUCCESS
            networkStatus.value = status
        }
    }
}