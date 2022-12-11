package binar.finalproject.binair.admin.ui.fragment

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.data.Constant
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.databinding.FragmentPastTicketListBinding
import binar.finalproject.binair.admin.databinding.FragmentTicketListBinding
import binar.finalproject.binair.admin.databinding.ReviewDeleteTicketBinding
import binar.finalproject.binair.admin.ui.activity.MainActivity
import binar.finalproject.binair.admin.ui.adapter.ListTicketAdapter
import binar.finalproject.binair.admin.viewmodel.FlightViewModel
import binar.finalproject.binair.admin.viewmodel.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PastTicketListFragment : Fragment() {
    private lateinit var binding : FragmentPastTicketListBinding
    private lateinit var flightVM : FlightViewModel
    private lateinit var sharedPrefs : SharedPreferences
    lateinit var ticketVM : TicketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPrefs = requireActivity().getSharedPreferences(Constant.dataUser, 0)
        binding = FragmentPastTicketListBinding.inflate(inflater, container, false)
        ticketVM = ViewModelProvider(this).get(TicketViewModel::class.java)
        flightVM = ViewModelProvider(requireActivity()).get(FlightViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavigation()
        setListener()
        getListTicket()
    }
    private fun setListener(){
        binding.apply {
            cvPast.setOnClickListener(){
                findNavController().navigate(R.id.action_pastTicketListFragment_to_ticketListFragment)
            }
        }
    }
    private fun getListTicket() {
        showLoading(true)
        flightVM.callGetAllTicket("false").observe(viewLifecycleOwner) {
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

        adapter.editClick = {
            val action = TicketListFragmentDirections.actionTicketListFragmentToEditTicketFragment(it)
            findNavController().navigate(action)
        }

        adapter.deleteClick = {
            val builder = AlertDialog.Builder(context)
            val alertDialog = builder.create()
            val dialogView = layoutInflater.inflate(R.layout.review_delete_ticket, null)
            val dialogBinding = ReviewDeleteTicketBinding.bind(dialogView)
            alertDialog.setView(dialogView)
            alertDialog.create()
            val idTicket = it.id
            dialogBinding.btnPayment.setOnClickListener() {
                val etpassword = dialogBinding.passwordInput.text.toString()
                val token ="Bearer " + sharedPrefs.getString("token","tokenisnull")

                if (etpassword == "123"){
                    ticketVM.deleteticket(idTicket,token).observe(viewLifecycleOwner){
                        if (it != null && it.status == "success") {
                            Toast.makeText(context, "Tiket Berhasil dihapus", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                            alertDialog.dismiss()
                            getListTicket()
                        }else{
                            Toast.makeText(context, "Data Gagal disimpan", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Password Salah", Toast.LENGTH_SHORT).show()
                }


            }
            dialogBinding.btnBack.setOnClickListener {
                findNavController().popBackStack()
                alertDialog.dismiss()
            }
            alertDialog.show()
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