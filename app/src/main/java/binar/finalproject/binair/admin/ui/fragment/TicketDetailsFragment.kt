package binar.finalproject.binair.admin.ui.fragment

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.databinding.FragmentTicketDetailsBinding

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
        val oldDateStart = clickedTicket.date_start.substring(0, 10)
        val formatedDateStart = oldDateStart.substring(8, 10) + "/" + oldDateStart.substring(5, 7) + "/" + oldDateStart.substring(0, 4)
        clickedTicket.date_start = formatedDateStart

        val oldDateEnd = clickedTicket.date_end.substring(0, 10)
        val formatedDateEnd = oldDateEnd.substring(8, 10) + "/" + oldDateEnd.substring(5, 7) + "/" + oldDateEnd.substring(0, 4)
        clickedTicket.date_end = formatedDateEnd

        binding.ticket = clickedTicket
    }
}