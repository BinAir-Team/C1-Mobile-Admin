package binar.finalproject.binair.admin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import binar.finalproject.binair.admin.data.TransactionRepository
import binar.finalproject.binair.admin.data.response.DataGetAllTransaction
import binar.finalproject.binair.admin.data.response.DeleteTransactionResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private var transactionRepo : TransactionRepository) : ViewModel(){
    fun callGetAllTransaction(token : String): LiveData<DataGetAllTransaction?> = transactionRepo.callGetAllTransaction(token)

    fun deleteTransaction(token : String, id : String): LiveData<DeleteTransactionResponse?> = transactionRepo.deleteTransaction(token, id)
}