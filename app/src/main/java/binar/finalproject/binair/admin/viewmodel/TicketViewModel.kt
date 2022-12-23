package binar.finalproject.binair.admin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import binar.finalproject.binair.admin.data.TicketRepository
import binar.finalproject.binair.admin.data.model.TicketData
import binar.finalproject.binair.admin.data.response.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(private var ticketRepo : TicketRepository) : ViewModel(){
    fun callGetAllTicket(willFly : String, type : String) : LiveData<DataPageTicket?> = ticketRepo.callGetAllTicket(willFly,type)

    fun addticket(dataticket : TicketData , Auth :  String): LiveData<AddTicketResponse?> = ticketRepo.addticket(dataticket,Auth)

    fun updateticket(id :String, dataticket : TicketData , Auth :  String): LiveData<UpdateTicketResponse?> = ticketRepo.updateticket(id, dataticket,Auth)

    fun deleteticket(id :String, Auth :  String): LiveData<DeleteTicketResponse?> = ticketRepo.deleteticket(id,Auth)

    fun callGetCityAirport() : LiveData<List<CityAirport>?> = ticketRepo.callGetCityAirport()
}