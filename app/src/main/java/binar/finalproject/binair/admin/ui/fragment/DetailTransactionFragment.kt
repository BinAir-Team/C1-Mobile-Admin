package binar.finalproject.binair.admin.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import binar.finalproject.binair.admin.data.response.TransactionGetAllTransaction
import binar.finalproject.binair.admin.data.response.TravelerGetAllTransaction
import binar.finalproject.binair.admin.databinding.FragmentDetailTransactionBinding
import binar.finalproject.binair.admin.ui.adapter.PassangerAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class DetailTransactionFragment : Fragment() {
    private lateinit var binding : FragmentDetailTransactionBinding
    private lateinit var clickedTransaction :TransactionGetAllTransaction
    private lateinit var adapter : PassangerAdapter
    private lateinit var layoutmanager : RecyclerView.LayoutManager

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailTransactionBinding.inflate(inflater, container, false)// Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        get data from bundle
        clickedTransaction = arguments?.getParcelable("transaction")!!
        setDataToView()
    }

    private fun setDataToView() {
        val oldDateStart = clickedTransaction.date
        val formatedDateStart = oldDateStart?.let { formatDate(it) }
        clickedTransaction.date = formatedDateStart
        binding.transaction = clickedTransaction

        passangerSetView(clickedTransaction.traveler as List<TravelerGetAllTransaction>)
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

    private fun passangerSetView(it : List<TravelerGetAllTransaction>){
        adapter =PassangerAdapter(it)
        layoutmanager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPassenger.adapter = adapter
        binding.rvPassenger.layoutManager = layoutmanager
    }
}