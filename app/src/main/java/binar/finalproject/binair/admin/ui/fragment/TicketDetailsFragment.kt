@file:Suppress("RemoveEmptyParenthesesFromLambdaCall", "RemoveEmptyParenthesesFromLambdaCall")

package binar.finalproject.binair.admin.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.data.formatRupiah
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.databinding.FragmentTicketDetailsBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class TicketDetailsFragment : Fragment() {
    private lateinit var binding : FragmentTicketDetailsBinding
    private lateinit var clickedTicket : DataTicket

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketDetailsBinding.inflate(inflater, container, false)// Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataFromBundle()
        setDataToView()
        setListener()
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener(){
            findNavController().navigate(R.id.action_ticketDetailsFragment_to_ticketListFragment)
        }
    }
    private fun getDataFromBundle() {
        clickedTicket = arguments?.getParcelable("clickedTicket")!!
    }

    private fun setDataToView() {
        val oldDateStart = clickedTicket.dateStart
        val formatedDateStart = formatDate(oldDateStart)
        clickedTicket.dateStart = formatedDateStart
        binding.tvAdultPrice.text = formatRupiah(clickedTicket.adultPrice)
        binding.tvChildPrice.text = formatRupiah(clickedTicket.childPrice)

        val oldDateEnd = clickedTicket.dateEnd
        if (oldDateEnd != null) {
            val formatedDateEnd = formatDate(oldDateEnd)
            clickedTicket.dateEnd = formatedDateEnd
        }else{
            binding.tvDateEnd.visibility = View.GONE
            binding.labelDateEnd.visibility = View.GONE
        }
        countDuration(clickedTicket)
        binding.ticket = clickedTicket
    }

    fun countDuration(item : DataTicket){
        val dpTime = SimpleDateFormat("HH:mm").parse(item.departureTime)
        val arTime = SimpleDateFormat("HH:mm").parse(item.arrivalTime)
        val difference = (arTime?.time ?: 0) - (dpTime?.time ?: 0)
        val days = (difference / (1000 * 60 * 60 * 24)).toInt()
        val hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60))
        val min = (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours) / (1000 * 60)
        binding.ticket = item
        binding.tvDurasi.text = "${hours}j ${min}menit"
    }

    fun formatDate(date : String) : String {
        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDate = LocalDate.parse(date, formatter)
            val formatter2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("id","ID"))
            return localDate.format(formatter2)
        }catch (e : Exception){
            return date
        }
    }
}