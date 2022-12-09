package binar.finalproject.binair.admin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import binar.finalproject.binair.admin.data.TicketRepository
import binar.finalproject.binair.admin.data.model.TicketData
import binar.finalproject.binair.admin.data.response.AddTicketResponse
import binar.finalproject.binair.admin.data.response.LogOutResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(private var ticketRepo : TicketRepository) : ViewModel(){
    fun addticket(dataticket : TicketData , Auth :  String): LiveData<AddTicketResponse?> = ticketRepo.addticket(dataticket,Auth)


}