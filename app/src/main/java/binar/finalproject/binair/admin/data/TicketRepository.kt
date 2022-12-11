package binar.finalproject.binair.admin.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import binar.finalproject.binair.admin.data.model.TicketData
import binar.finalproject.binair.admin.data.remote.APIService
import binar.finalproject.binair.admin.data.response.AddTicketResponse
import binar.finalproject.binair.admin.data.response.DeleteTicketResponse
import binar.finalproject.binair.admin.data.response.UpdateTicketResponse
import javax.inject.Inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketRepository @Inject constructor(var apiService: APIService) {
    private val _addTicket = MutableLiveData<AddTicketResponse?>()
    val addticket : LiveData<AddTicketResponse?> = _addTicket
    private val _updateTicket = MutableLiveData<UpdateTicketResponse?>()
    val updateticket : LiveData<UpdateTicketResponse?> = _updateTicket
    private val _deleteTicket = MutableLiveData<DeleteTicketResponse?>()
    val deleteticket : LiveData<DeleteTicketResponse?> = _deleteTicket

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

    fun updateticket(id : String, dataTicket: TicketData,token: String) : LiveData<UpdateTicketResponse?>{
        apiService.updateTicket(id, dataTicket,token).enqueue(object  : Callback<UpdateTicketResponse>{
            override fun onResponse(
                call: Call<UpdateTicketResponse>,
                response: Response<UpdateTicketResponse>
            ) {
                if (response.isSuccessful){
                    val dataResponse = response.body()
                    _updateTicket.postValue(dataResponse)
                    Log.d("LogRegister", dataResponse.toString())
                }else{
                    _updateTicket.postValue(null)
                    Log.e("Error not successful : ", response.message())
                }
            }

            override fun onFailure(call: Call<UpdateTicketResponse>, t: Throwable) {
                _updateTicket.postValue(null)
                Log.d("Error onFailure : ", t.message!!)
            }
        })
        return updateticket
    }

    fun deleteticket(id: String, token: String) : LiveData<DeleteTicketResponse?>{
        apiService.deleteTicket(id,token).enqueue(object  : Callback<DeleteTicketResponse>{
            override fun onResponse(
                call: Call<DeleteTicketResponse>,
                response: Response<DeleteTicketResponse>
            ) {
                if (response.isSuccessful){
                    val dataResponse = response.body()
                    _deleteTicket.postValue(dataResponse)
                    Log.d("log Delete", dataResponse.toString())
                }else{
                    _deleteTicket.postValue(null)
                    Log.e("Error not successful : ", response.message())
                }
            }

            override fun onFailure(call: Call<DeleteTicketResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return deleteticket
    }


}