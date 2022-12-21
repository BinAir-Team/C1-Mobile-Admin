package binar.finalproject.binair.admin.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import binar.finalproject.binair.admin.data.model.DataRegister
import binar.finalproject.binair.admin.data.remote.APIService
import binar.finalproject.binair.admin.data.response.GetUserResponse
import binar.finalproject.binair.admin.data.response.LogOutResponse
import binar.finalproject.binair.admin.data.response.LoginResponse
import binar.finalproject.binair.admin.data.response.RegisterUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(var apiService: APIService) {
    private val _registerUser = MutableLiveData<RegisterUserResponse?>()
    val registerUser : LiveData<RegisterUserResponse?> = _registerUser
    private val _loginUser = MutableLiveData<LoginResponse?>()
    val loginUser : LiveData<LoginResponse?> = _loginUser
    private val _logout = MutableLiveData<LogOutResponse?>()
    val logout : LiveData<LogOutResponse?> = _logout
    private val _currentUser = MutableLiveData<GetUserResponse?>()
    val currentUser : LiveData<GetUserResponse?> = _currentUser

    fun registerUser(dataUser : DataRegister) : LiveData<RegisterUserResponse?> {
        apiService.registerUser(dataUser).enqueue(object : Callback<RegisterUserResponse> {
            override fun onResponse(
                call: Call<RegisterUserResponse>,
                response: Response<RegisterUserResponse>
            ) {
                if (response.isSuccessful){
                    val dataResponse = response.body()
                    _registerUser.postValue(dataResponse)
                }else{
                    _registerUser.postValue(null)
                    Log.e("Error not successful : ", response.message())
                }
            }

            override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                _registerUser.postValue(null)
                Log.d("Error onFailure : ", t.message!!)
            }
        })
        return registerUser
    }

    fun loginUser(email : String, password : String) : LiveData<LoginResponse?> {
        apiService.loginUser(email,password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful){
                    val dataResponse = response.body()
                    _loginUser.postValue(dataResponse)
//                    Log.d("LogLogin", dataResponse.toString())
                }else{
                    _loginUser.postValue(null)
                    Log.e("Error not successful : ", response.message())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginUser.postValue(null)
                Log.d("Error onFailure : ", t.message!!)
            }
        })
        return loginUser
    }

    fun logoutProfile(token : String) : LiveData<LogOutResponse?>{
        apiService.logout(token).enqueue(object :Callback<LogOutResponse>{
            override fun onResponse(
                call: Call<LogOutResponse>,
                response: Response<LogOutResponse>
            ) {
                if (response.isSuccessful){
                    val dataResponse = response.body()
                    _logout.postValue(dataResponse)
                    Log.d("LogRegister", dataResponse.toString())
                }else{
                    _logout.postValue(null)
                    Log.e("Error not successful : ", response.message())
                }
            }

            override fun onFailure(call: Call<LogOutResponse>, t: Throwable) {
                _logout.postValue(null)
                Log.d("Error onFailure : ", t.message!!)
            }
        })
        return logout
    }

    fun getUser(token : String) : LiveData<GetUserResponse?> {
        apiService.getUser(token).enqueue(object : Callback<GetUserResponse>{
            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                if (response.isSuccessful){
                    val dataResponse = response.body()
                    _currentUser.postValue(dataResponse)
                }else{
                    _currentUser.postValue(null)
                    Log.e("Error not successful : ", response.message())
                }
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                _currentUser.postValue(null)
                Log.d("Error onFailure : ", t.message!!)
            }
        })
        return currentUser
    }
}