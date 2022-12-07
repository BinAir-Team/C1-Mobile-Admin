package binar.finalproject.binair.admin.data.remote

import binar.finalproject.binair.admin.data.model.DataRegister
import binar.finalproject.binair.admin.data.model.TicketData
import binar.finalproject.binair.admin.data.response.AddTicketResponse
import binar.finalproject.binair.admin.data.response.LogOutResponse
import binar.finalproject.binair.admin.data.response.LoginResponse
import binar.finalproject.binair.admin.data.response.RegisterUserResponse
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @POST("register")
    fun registerUser(@Body data : DataRegister): Call<RegisterUserResponse>

    @POST("login")

    @FormUrlEncoded
    fun loginUser(@Field("email") email : String, @Field("password") password : String) : Call<LoginResponse>

    @POST("tickets")
    fun addTicket(@Body data : TicketData, @Header("Authorization") Auth :  String) : Call<AddTicketResponse>

    @DELETE("logout")
    fun logout(@Header("Authorization") Auth :  String) : Call<LogOutResponse>
}