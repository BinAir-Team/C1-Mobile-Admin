package binar.finalproject.binair.admin.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import binar.finalproject.binair.admin.data.model.TicketData
import binar.finalproject.binair.admin.data.remote.APIService
import binar.finalproject.binair.admin.data.response.AddTicketResponse
import javax.inject.Inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketRepository @Inject constructor(var apiService: APIService) {
    private val _addTicket = MutableLiveData<AddTicketResponse?>()
    val addticket : LiveData<AddTicketResponse?> = _addTicket

    fun addticket(dataTicket : TicketData, token : String) : LiveData<AddTicketResponse?>{
        apiService.addTicket(dataTicket, token).enqueue(object :Callback<AddTicketResponse>{
            override fun onResponse(
                call: Call<AddTicketResponse>,
                response: Response<AddTicketResponse>
            ) {
                if (response.isSuccessful){
                    val dataResponse = response.body()
                    _addTicket.postValue(dataResponse)
                    Log.d("LogRegister", dataResponse.toString())
                }else{
                    _addTicket.postValue(null)
                    Log.e("Error not successful : ", response.message())
                }
            }

            override fun onFailure(call: Call<AddTicketResponse>, t: Throwable) {
                _addTicket.postValue(null)
                Log.d("Error onFailure : ", t.message!!)
            }

        })
        return addticket
    }


}