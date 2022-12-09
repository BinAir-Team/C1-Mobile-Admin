package binar.finalproject.binair.admin.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.data.Constant
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.databinding.FragmentHomeBinding
import binar.finalproject.binair.admin.databinding.FragmentTicketListBinding
import binar.finalproject.binair.admin.ui.activity.MainActivity
import binar.finalproject.binair.admin.ui.adapter.ListTicketAdapter
import binar.finalproject.binair.admin.viewmodel.FlightViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketListFragment : Fragment() {

    private lateinit var binding : FragmentTicketListBinding
    private lateinit var flightVM : FlightViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTicketListBinding.inflate(inflater, container, false)
        flightVM = ViewModelProvider(requireActivity()).get(FlightViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavigation()
        getListTicket()
    }

    private fun getListTicket() {
        showLoading(true)
        flightVM.callGetAllTicket("true").observe(viewLifecycleOwner) {
            if (it != null) {
                setDataToRecView(it)
                showLoading(false)
            }
        }
    }

    private fun setDataToRecView(data: List<DataTicket>) {
        val adapter : ListTicketAdapter = ListTicketAdapter(data)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvListTicket.adapter = adapter
        binding.rvListTicket.layoutManager = layoutManager

        adapter.onClick = {

            val action = TicketListFragmentDirections.actionTicketListFragmentToTicketDetailsFragment2(it)
            findNavController().navigate(action)
        }
    }
    private fun showLoading(condition : Boolean) {
        if (condition) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    private fun showBottomNavigation() {
        (activity as MainActivity).binding.bottomNavContainer.visibility = View.VISIBLE
    }
}