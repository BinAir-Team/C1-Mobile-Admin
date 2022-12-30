package binar.finalproject.binair.admin.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import binar.finalproject.binair.admin.data.TicketRepository
import binar.finalproject.binair.admin.data.UserRepository
import binar.finalproject.binair.admin.data.response.DataPageTicket
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.data.response.RegisterUserResponse
import binar.finalproject.binair.buyer.utils.DataDummy
import binar.finalproject.binair.buyer.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TicketViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var ticketRepository: TicketRepository
    private lateinit var ticketVM : TicketViewModel

    @Before
    fun setUp() {
        ticketVM = TicketViewModel(ticketRepository)
    }

    @Test
    fun `Get ticket response not null`(){
        val dummyMutLD = MutableLiveData<DataPageTicket>()
        dummyMutLD.value = DataDummy.succesGetOnewayTicket()
        val expectedResponse: LiveData<DataPageTicket?> = dummyMutLD
        Mockito.`when`(ticketVM.callGetAllTicket("false","oneway")).thenReturn(expectedResponse)

        val actualResp = ticketVM.callGetAllTicket("false","oneway").getOrAwaitValue()
        Mockito.verify(ticketRepository).callGetAllTicket("false","oneway")
        assertNotNull(actualResp)
    }

    @Test
    fun `Success get all oneway ticket`(){
        val dummyMutLD = MutableLiveData<DataPageTicket>()
        dummyMutLD.value = DataDummy.succesGetOnewayTicket()
        val expectedResponse: LiveData<DataPageTicket?> = dummyMutLD
        Mockito.`when`(ticketVM.callGetAllTicket("false","oneway")).thenReturn(expectedResponse)

        val actualResp = ticketVM.callGetAllTicket("false","oneway").getOrAwaitValue()
        Mockito.verify(ticketRepository).callGetAllTicket("false","oneway")
        Assert.assertEquals(expectedResponse.value,actualResp)
    }

    @Test
    fun `Success get all roundtrip ticket`(){
        val dummyMutLD = MutableLiveData<DataPageTicket>()
        dummyMutLD.value = DataDummy.succesGetRoundtripTicket()
        val expectedResponse: LiveData<DataPageTicket?> = dummyMutLD
        Mockito.`when`(ticketVM.callGetAllTicket("false","roundtrip")).thenReturn(expectedResponse)

        val actualResp = ticketVM.callGetAllTicket("false","roundtrip").getOrAwaitValue()
        Mockito.verify(ticketRepository).callGetAllTicket("false","roundtrip")
        Assert.assertEquals(expectedResponse.value,actualResp)
    }
}