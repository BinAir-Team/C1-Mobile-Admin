package binar.finalproject.binair.admin.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import binar.finalproject.binair.admin.data.Constant
import binar.finalproject.binair.admin.data.model.TicketData
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.databinding.FragmentEditTicketBinding
import binar.finalproject.binair.admin.viewmodel.TicketViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Boolean
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class EditTicketFragment : Fragment() {
    private lateinit var binding : FragmentEditTicketBinding
    private lateinit var clickedTicket : DataTicket
    private val calendar = Calendar.getInstance()
    lateinit var ticketVM : TicketViewModel
    private lateinit var sharedPrefs : SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditTicketBinding.inflate(inflater, container, false)// Inflate the layout for this fragment
        sharedPrefs = requireActivity().getSharedPreferences(Constant.dataUser, 0)
        ticketVM = ViewModelProvider(this).get(TicketViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        getDataFromBundle()
        setDataToView()
    }
    private fun setListener(){
        binding.apply {
            btnSave.setOnClickListener{
                saveedit()
            }
            etTglBerangkatInput.setOnClickListener {
                showDatePickerDialog("berangkat")
            }
            etTglBerangkatInput.setOnFocusChangeListener { view, b ->
                if (b) {
                    showDatePickerDialog("berangkat")
                }
            }
            etTglSelesaiInput.setOnClickListener {
                showDatePickerDialog("selesai")
            }
            etTglSelesaiInput.setOnFocusChangeListener { view, b ->
                if (b) {
                    showDatePickerDialog("selesai")
                }
            }
            etJamBerangkatInput.setOnClickListener{
                showTimePickerDialog("keberangkatan")
            }
            etJamBerangkatInput.setOnFocusChangeListener { view, b ->
                if (b) {
                    showTimePickerDialog("keberangkatan")
                }
            }
            etJamKedatanganInput.setOnClickListener{
                showTimePickerDialog("kepulangan")
            }
            etJamKedatanganInput.setOnFocusChangeListener { view, b ->
                if (b) {
                    showTimePickerDialog("kepulangan")
                }
            }
        }
    }

    private fun showTimePickerDialog(kategori: String){
        val timePicker: MaterialTimePicker = MaterialTimePicker
            .Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText("Pilih Waktu")
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            .build()

        fragmentManager?.let { timePicker.show(it, "TIME_PICKER") }

        timePicker.addOnPositiveButtonClickListener {
            val time = timePicker.hour.toString() + ":" + timePicker.minute.toString()
            updateTime(kategori, time)
        }
    }

    private fun showDatePickerDialog(kategori: String) {
        val datePicker =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel(kategori, calendar.time)
            }
        DatePickerDialog(requireActivity(),datePicker,calendar.get(Calendar.YEAR),calendar.get(
            Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private fun formatDate(date : Date) : String {
        val myFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat)
        return dateFormat.format(date)
    }

    private fun updateLabel(kategori : String, date : Date) {
        val formatedDate = formatDate(date)
        if(kategori == "berangkat") {
            binding.etTglBerangkatInput.setText(formatedDate)
        }
        if(kategori == "selesai"){
            binding.etTglSelesaiInput.setText(formatedDate)
        }
    }

    private fun updateTime(kategori: String, time : String){
        if (kategori == "keberangkatan") {
            binding.etJamBerangkatInput.setText(time)
        }
        else if (kategori == "kepulangan") {
            binding.etJamKedatanganInput.setText(time)
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
        val formatedDateEnd = oldDateEnd?.let { formatDate(it) }
        clickedTicket.dateEnd = formatedDateEnd
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

    private fun saveedit(){
        val asalKota = binding.etFrom.text.toString()
        val asalBandara = binding.etAirportFrom.text.toString()
        val destinasi = binding.etDestination.text.toString()
        val destinasiBandara = binding.etAirportDestination.text.toString()
        val tanggalBerangkat = binding.etTglBerangkatInput.text.toString()
        val tanggalSelesai = binding.etTglSelesaiInput.text.toString()
        val jamBerangkat = binding.etJamBerangkatInput.text.toString()
        val jamKedatangan = binding.etJamKedatanganInput.text.toString()
        val adultPrice = binding.etAdultPrice.text.toString().toInt()
        val childPrice = binding.etChildPrice.text.toString().toInt()
        val initialStock = binding.etJmlPenumpangInput.text.toString().toInt()

        val formatedDateBerangkat = tanggalBerangkat.substring(6, 10) + "-" + tanggalBerangkat.substring(3, 5) + "-" + tanggalBerangkat.substring(0, 2)
        val formatedDateSelesai = tanggalSelesai.substring(6, 10) + "-" + tanggalSelesai.substring(3, 5) + "-" + tanggalSelesai.substring(0, 2)

        val token ="Bearer " + sharedPrefs.getString("token","tokenisnull")
        val ticketdata = TicketData(asalKota,asalBandara,destinasi,destinasiBandara,
            formatedDateBerangkat,formatedDateSelesai,jamBerangkat,jamKedatangan, "sekali jalan",
            adultPrice,childPrice, Boolean.TRUE, initialStock, initialStock )

        ticketVM.updateticket(clickedTicket.id, ticketdata,token).observe(viewLifecycleOwner){
            if (it != null && it.status == "success") {
                Toast.makeText(context, "Data Berhasil diupdate", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Data Gagal disimpan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}