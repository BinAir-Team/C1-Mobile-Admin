package binar.finalproject.binair.admin.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.data.Constant
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.databinding.FragmentHomeBinding
import binar.finalproject.binair.admin.databinding.FragmentTicketListBinding
import binar.finalproject.binair.admin.databinding.ReviewDeleteTicketBinding
import binar.finalproject.binair.admin.ui.activity.MainActivity
import binar.finalproject.binair.admin.ui.adapter.ListTicketAdapter
import binar.finalproject.binair.admin.viewmodel.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
//Oneway Fragment
class TicketListFragment : Fragment() {
    private lateinit var binding : FragmentTicketListBinding
    private lateinit var sharedPrefs : SharedPreferences
    lateinit var ticketVM : TicketViewModel
    private var listTicket : ArrayList<DataTicket?> = ArrayList()
    private lateinit var adapter : ListTicketAdapter
    private lateinit var layManager : LayoutManager
    private var currentPage : Int = 0
    private var totalPage : Int = 0
    private var totalItems : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPrefs = requireActivity().getSharedPreferences(Constant.dataUser, 0)
        binding = FragmentTicketListBinding.inflate(inflater, container, false)
        ticketVM = ViewModelProvider(this).get(TicketViewModel::class.java)
        adapter = ListTicketAdapter(listTicket)
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
            cvOngoing.setOnClickListener(){
                findNavController().navigate(R.id.action_ticketListFragment_to_pastTicketListFragment)
            }
        }
//        binding.rvListTicket.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                if(isLastItemDisplaying(recyclerView)){
//                    Log.e("PAGINATION", "Load more")
//                    if (currentPage < totalPage && listTicket.size != totalItems) {
//                        currentPage++
//                        showLoadingRV(true)
//                        getListTicket()
//                        recyclerView.adapter?.notifyDataSetChanged()
//                    }
//                }
//            }
//        })
    }

//    private fun isLastItemDisplaying(rv : RecyclerView) : Boolean{
//        if(rv.adapter?.itemCount != 0){
//            val lastVisibleItemPosition = (rv.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
//            if(lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == (rv.adapter?.itemCount?.minus(
//                    1
//                ) ?: 0)
//            ){
//                Log.d("TAG", "onScrolled: $lastVisibleItemPosition")
//                return true
//            }
//        }
//        return false
//    }

    private fun getListTicket() {
        showLoading(true)
        ticketVM.callGetAllTicket("false", "oneway").observe(viewLifecycleOwner) {
            if (it != null) {
//                if(listTicket.size != totalItems || listTicket.size == 0) listTicket.addAll(it.tickets) else showLoading(false)
//                Log.e("PAGINATION", "List Size : ${listTicket.size}")
//                listTicket.addAll(it.tickets)
                currentPage = it.currentPage
                totalPage = it.totalPages
                totalItems = it.totalItems
                setDataToRecView(it.tickets)
//                if(currentPage == 0){
//                    setDataToRecView()
//                }else{
//                    showLoadingRV(false)
//                }
                showLoading(false)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataToRecView(it : List<DataTicket?>) {
        adapter = ListTicketAdapter(it)
        layManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvListTicket.adapter = adapter
        binding.rvListTicket.layoutManager = layManager

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

    private fun showLoadingRV(condition : Boolean) {
        if (condition){
            binding.pbRecView.visibility = View.VISIBLE
        }else{
            binding.pbRecView.visibility = View.GONE
        }
    }

    private fun showBottomNavigation() {
        (activity as MainActivity).binding.bottomNavContainer.visibility = View.VISIBLE
    }
}