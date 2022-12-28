@file:Suppress("RemoveEmptyParenthesesFromLambdaCall", "RemoveEmptyParenthesesFromLambdaCall",
    "RemoveEmptyParenthesesFromLambdaCall", "unused", "unused"
)

package binar.finalproject.binair.admin.ui.fragment

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.data.Constant
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.databinding.FragmentPastTicketListBinding
import binar.finalproject.binair.admin.databinding.ReviewDeleteTicketBinding
import binar.finalproject.binair.admin.ui.activity.MainActivity
import binar.finalproject.binair.admin.ui.adapter.ListTicketAdapter
import binar.finalproject.binair.admin.viewmodel.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("RemoveEmptyParenthesesFromLambdaCall", "RemoveEmptyParenthesesFromLambdaCall")
@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
//Round Trip Fragment
class PastTicketListFragment : Fragment() {
    private lateinit var binding : FragmentPastTicketListBinding
    private lateinit var ticketViewModel: TicketViewModel
    private lateinit var sharedPrefs : SharedPreferences
    lateinit var ticketVM : TicketViewModel
    private var listTicket : ArrayList<DataTicket?> = ArrayList()
    private lateinit var adapter : ListTicketAdapter
    private lateinit var layManager : RecyclerView.LayoutManager
    private var currentPage : Int = 0
    private var totalPage : Int = 0
    private var totalItems : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPrefs = requireActivity().getSharedPreferences(Constant.dataUser, 0)
        binding = FragmentPastTicketListBinding.inflate(inflater, container, false)
        ticketVM = ViewModelProvider(this).get(TicketViewModel::class.java)
        ticketViewModel = ViewModelProvider(requireActivity()).get(TicketViewModel::class.java)
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
        ticketVM.callGetAllTicket("false", "roundtrip").observe(viewLifecycleOwner) {
            if (it != null) {
                currentPage = it.currentPage
                totalPage = it.totalPages
                totalItems = it.totalItems
                setDataToRecView(it.tickets)
                showLoading(false)
            }
        }
    }

    private fun setDataToRecView(it : List<DataTicket?>) {
        adapter = ListTicketAdapter(it)
        layManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvListTicket.adapter = adapter
        binding.rvListTicket.layoutManager = layManager

        adapter.onClick = {
            if(it.dateEnd == null){it.dateEnd = ""}
            val action = PastTicketListFragmentDirections.actionPastTicketListFragmentToTicketDetailsFragment(it)
            findNavController().navigate(action)
        }

        adapter.editClick = {
            if(it.dateEnd == null){it.dateEnd = ""}
            val action = PastTicketListFragmentDirections.actionPastTicketListFragmentToEditTicketFragment(it)
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
            dialogBinding.btnDelete.setOnClickListener() {
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