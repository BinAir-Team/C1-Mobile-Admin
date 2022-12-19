package binar.finalproject.binair.admin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import binar.finalproject.binair.admin.data.model.FlightRepository
import binar.finalproject.binair.admin.data.response.CityAirport
import binar.finalproject.binair.admin.data.response.DataTicket
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(private val flightRepo : FlightRepository) : ViewModel() {
    fun callGetAllTicket(willFly : String) : LiveData<List<DataTicket>?> = flightRepo.callGetAllTicket(willFly)
}