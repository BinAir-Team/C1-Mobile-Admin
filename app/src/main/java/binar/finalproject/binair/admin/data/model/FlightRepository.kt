package binar.finalproject.binair.admin.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import binar.finalproject.binair.admin.data.remote.APIService
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.data.response.GetTiketResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FlightRepository @Inject constructor (var client : APIService){
    private val _allTicket = MutableLiveData<List<DataTicket>?>()
    val allTicket : LiveData<List<DataTicket>?> = _allTicket

    fun callGetAllTicket() : LiveData<List<DataTicket>?>{
        client.getAllTicket().enqueue(object : Callback<GetTiketResponse>{
            override fun onResponse(
                call: Call<GetTiketResponse>,
                response: Response<GetTiketResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        _allTicket.postValue(result.data)
                        Log.d("RESULT", "Result : $result")
                    }else{
                        _allTicket.postValue(null)
                    }
                }else{
                    Log.e("Error : ", "onFailed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetTiketResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")
            }
        })
        return  allTicket
    }
}