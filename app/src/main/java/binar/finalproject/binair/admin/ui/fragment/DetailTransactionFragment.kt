package binar.finalproject.binair.admin.ui.fragment

import android.annotation.SuppressLint
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
import binar.finalproject.binair.admin.data.Constant.dataUser
import binar.finalproject.binair.admin.data.formatRupiah
import binar.finalproject.binair.admin.data.response.TransactionGetAllTransaction
import binar.finalproject.binair.admin.data.response.TravelerGetAllTransaction
import binar.finalproject.binair.admin.databinding.FragmentDetailTransactionBinding
import binar.finalproject.binair.admin.ui.adapter.PassangerAdapter
import binar.finalproject.binair.admin.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class DetailTransactionFragment : Fragment() {
    private lateinit var binding : FragmentDetailTransactionBinding
    private lateinit var clickedTransaction :TransactionGetAllTransaction
    private lateinit var adapter : PassangerAdapter
    private lateinit var layoutmanager : RecyclerView.LayoutManager
    private lateinit var transactionVM : TransactionViewModel
    private lateinit var sharedPrefs : SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailTransactionBinding.inflate(inflater, container, false)// Inflate the layout for this fragment
        sharedPrefs = requireActivity().getSharedPreferences(dataUser, 0)
        transactionVM = ViewModelProvider(this).get(TransactionViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickedTransaction = arguments?.getParcelable("transaction")!!
        setDataToView()
        setListener()
    }
    private fun setListener(){
        binding.apply {
            delete.setOnClickListener(){
                val token ="Bearer " + sharedPrefs.getString("token","tokenisnull")

                clickedTransaction.id?.let { it1 ->
                    transactionVM.deleteTransaction(token, it1).observe(viewLifecycleOwner) {
                        if (it != null) {
                            Toast.makeText(context, "Data Berhasil Di Hapus", Toast.LENGTH_SHORT).show()
                            backToProfile()
                        }else{
                            Toast.makeText(context, "Data Tidak Berhasil Di Hapus", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            btnBack.setOnClickListener(){
                backToProfile()
            }
        }
    }

    private fun backToProfile(){
        findNavController().navigate(R.id.action_detailTransactionFragment_to_profileFragment)
    }

    private fun setDataToView() {
        val oldDateStart = clickedTransaction.date
        val formatedDateStart = oldDateStart?.let { formatDate(it) }
        clickedTransaction.date = formatedDateStart
        if(clickedTransaction.ticket?.dateEnd == null){
            binding.apply {
                containerDateEnd.visibility = View.GONE
            }
        }

        binding.transaction = clickedTransaction
        binding.tvTotalPrice.text = clickedTransaction.amounts?.let { formatRupiah(it) }
        passangerSetView(clickedTransaction.traveler as List<TravelerGetAllTransaction>)
    }

    private fun formatDate(date : String) : String {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDate = LocalDate.parse(date, formatter)
            val formatter2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("id","ID"))
            localDate.format(formatter2)
        }catch (e : Exception){
            date
        }
    }

    private fun passangerSetView(it : List<TravelerGetAllTransaction>){
        adapter = PassangerAdapter(it)
        layoutmanager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPassenger.adapter = adapter
        binding.rvPassenger.layoutManager = layoutmanager
    }
}