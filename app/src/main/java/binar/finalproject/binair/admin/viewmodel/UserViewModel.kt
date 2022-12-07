package binar.finalproject.binair.admin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import binar.finalproject.binair.admin.data.UserRepository
import binar.finalproject.binair.admin.data.model.DataRegister
import binar.finalproject.binair.admin.data.response.LogOutResponse
import binar.finalproject.binair.admin.data.response.LoginResponse
import binar.finalproject.binair.admin.data.response.RegisterUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private var userRepo : UserRepository) : ViewModel() {
    fun registerUser(dataUser : DataRegister) : LiveData<RegisterUserResponse?> = userRepo.registerUser(dataUser)

    fun loginUser(email : String, password : String) : LiveData<LoginResponse?> = userRepo.loginUser(email, password)

    fun logoutUser(Auth: String):LiveData<LogOutResponse?> = userRepo.logoutProfile(Auth)
}