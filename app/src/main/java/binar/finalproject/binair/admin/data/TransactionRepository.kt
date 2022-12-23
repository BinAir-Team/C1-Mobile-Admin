package binar.finalproject.binair.admin.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import binar.finalproject.binair.admin.data.remote.APIService
import binar.finalproject.binair.admin.data.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TransactionRepository @Inject constructor(var apiService: APIService) {
    private val _allTransaction = MutableLiveData<DataGetAllTransaction?>()
    val allTransaction : LiveData<DataGetAllTransaction?> = _allTransaction

    fun callGetAllTransaction(token : String) : LiveData<DataGetAllTransaction?> {
        apiService.getAllTransaction(token).enqueue(object : Callback<GetAllTransaction>{
            override fun onResponse(
                call: Call<GetAllTransaction>,
                response: Response<GetAllTransaction>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        _allTransaction.postValue(result.data)
                        Log.d("RESULT", "Result : $result")
                    }else{
                        _allTransaction.postValue(null)
                    }
                }else{
                    Log.e("Error : ", "onFailed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetAllTransaction>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")
            }

        })
        return allTransaction
    }
}