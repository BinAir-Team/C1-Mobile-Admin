package binar.finalproject.binair.admin.data.remote

import binar.finalproject.binair.admin.data.model.DataRegister
import binar.finalproject.binair.admin.data.model.TicketData
import binar.finalproject.binair.admin.data.response.*
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @POST("register")
    fun registerUser(@Body data : DataRegister): Call<RegisterUserResponse>

    @POST("login")
    @FormUrlEncoded
    fun loginUser(@Field("email") email : String, @Field("password") password : String) : Call<LoginResponse>

    @GET("search")
    fun getAllCity() : Call<CityAirportResponse>

    @POST("tickets")
    fun addTicket(@Body data : TicketData, @Header("Authorization") Auth :  String) : Call<AddTicketResponse>

    @DELETE("logout")
    fun logout(@Header("Authorization") Auth :  String) : Call<LogOutResponse>

    @GET("tickets")
    fun getAllTicket(@Query("willFly") willFly : String) : Call<GetTiketResponse>

    @PUT("tickets/{id}")
    fun updateTicket(@Path("id") id : String ,@Body data : TicketData, @Header("Authorization") Auth :  String) : Call<UpdateTicketResponse>

    @DELETE("tickets/{id}")
    fun deleteTicket(@Path("id") id : String, @Header("Authorization") Auth: String) : Call<DeleteTicketResponse>
}