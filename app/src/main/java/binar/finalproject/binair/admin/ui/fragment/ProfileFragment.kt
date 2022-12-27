@file:Suppress("RemoveRedundantQualifierName")

package binar.finalproject.binair.admin.ui.fragment

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.data.Constant
import binar.finalproject.binair.admin.data.response.TransactionGetAllTransaction
import binar.finalproject.binair.admin.databinding.FragmentProfileBinding
import binar.finalproject.binair.admin.ui.adapter.TransactionAdapter
import binar.finalproject.binair.admin.viewmodel.TransactionViewModel
import binar.finalproject.binair.admin.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private lateinit var sharedPrefs : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    lateinit var userVM : UserViewModel
    private lateinit var adapter : TransactionAdapter
    lateinit var transactionVM : TransactionViewModel
    private lateinit var layoutmanager : RecyclerView.LayoutManager

    private var currentPage : Int = 0
    private var totalPage : Int = 0
    private var totalItems : Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        userVM = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPrefs = requireActivity().getSharedPreferences(Constant.dataUser, 0)
        transactionVM = ViewModelProvider(this).get(TransactionViewModel::class.java)
        editor = sharedPrefs.edit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }
    val token : String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImUxZDM3MGVlLTBkNDItNDY2Yy04OGEyLTg5MmFkYmQ1ODRkYyIsImZpcnN0bmFtZSI6bnVsbCwibGFzdG5hbWUiOm51bGwsImdlbmRlciI6bnVsbCwiZW1haWwiOiJhZG1pbkBnbWFpbC5jb20iLCJwaG9uZSI6bnVsbCwicm9sZSI6ImFkbWluIiwicHJvZmlsZV9pbWFnZSI6bnVsbCwiaWF0IjoxNjcwMzI5Mzg5LCJleHAiOjE2NzAzMzI5ODl9.-IkpQc9J8B9q8uiJwmiOIGYdYPzMvPyTnXuNQCockt8"
    private fun setListener() {

        gettransaction()
    }

    private fun gotologin(){
        findNavController().navigateSafe(R.id.action_profileFragment_to_loginFragment)
    }

    fun NavController.navigateSafe(@IdRes resId: Int, args: Bundle? = null) {
        val destinationId = currentDestination?.getAction(resId)?.destinationId.orEmpty()
        currentDestination?.let { node ->
            val currentNode = when (node) {
                is NavGraph -> node
                else -> node.parent
            }
            if (destinationId != 0) {
                currentNode?.findNode(destinationId)?.let { navigate(resId, args) }
            }
        }}

    fun Int?.orEmpty(default: Int = 0): Int {
        return this ?: default
    }
    private fun gettransaction(){
        val token = "Bearer " + sharedPrefs.getString("token","tokenisnull")
        showLoading(true)
        transactionVM.callGetAllTransaction(token).observe(viewLifecycleOwner){
            if(it != null){
                currentPage = it.currentPage!!
                totalPage = it.totalPages!!
                totalItems = it.totalItems!!
                setDataToRecView(it.transactions as List<TransactionGetAllTransaction>)
                showLoading(false)
            }
        }

    }
    private fun showLoading(condition : Boolean) {
        if (condition) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setDataToRecView(it : List<TransactionGetAllTransaction>){
        adapter = TransactionAdapter(it)
        layoutmanager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTransactionList.adapter = adapter
        binding.rvTransactionList.layoutManager = layoutmanager

        adapter.onClick = {
            val action = ProfileFragmentDirections.actionProfileFragmentToDetailTransactionFragment(it)
            findNavController().navigate(action)
        }
    }
}