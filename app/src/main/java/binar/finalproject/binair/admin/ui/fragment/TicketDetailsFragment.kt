package binar.finalproject.binair.admin.ui.fragment

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.databinding.FragmentTicketDetailsBinding
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
    ): View? {
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

        val oldDateEnd = clickedTicket.dateEnd
        if (oldDateEnd != null) {
            val formatedDateEnd = formatDate(oldDateEnd)
            clickedTicket.dateEnd = formatedDateEnd
        }else{
            binding.tvDateEnd.visibility = View.GONE
            binding.labelDateEnd.visibility = View.GONE
        }

        binding.ticket = clickedTicket
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