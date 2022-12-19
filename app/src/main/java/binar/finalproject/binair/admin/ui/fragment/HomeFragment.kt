package binar.finalproject.binair.admin.ui.fragment

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import binar.finalproject.binair.admin.R
import binar.finalproject.binair.admin.data.Constant.dataPassenger
import binar.finalproject.binair.admin.data.Constant.dataUser
import binar.finalproject.binair.admin.data.model.TicketData
import binar.finalproject.binair.admin.databinding.FragmentHomeBinding
import binar.finalproject.binair.admin.ui.activity.MainActivity
import binar.finalproject.binair.admin.viewmodel.TicketViewModel
import binar.finalproject.binair.admin.viewmodel.UserViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Boolean.TRUE
import java.text.SimpleDateFormat
import java.util.*
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private val calendar = Calendar.getInstance()
    lateinit var ticketVM : TicketViewModel
    private lateinit var sharedPrefs : SharedPreferences
    private var cityFrom : String = "Jakarta"
    private var airportFrom : String = "Soekarno Hatta"
    private var cityTo : String = "Surabaya"
    private var airportTo : String = "Juanda"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        ticketVM = ViewModelProvider(this).get(TicketViewModel::class.java)
        sharedPrefs = requireActivity().getSharedPreferences(dataUser, 0)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavigation()
        setListener()
    }

    private fun setListener() {
        binding.apply {

            btnAdd.setOnClickListener {
                addTicket()
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

    private fun setAutoCompleteData() {
        ticketVM.callGetCityAirport().observe(viewLifecycleOwner){
            if(it != null){
                val city = it
                val adapter = AutoCompleteAirportAdapter(requireContext(), city as java.util.ArrayList<CityAirport?>)
                binding.apply {
                    etFrom.threshold = 1
                    etFrom.setAdapter(adapter)
                    etDestination.threshold = 1
                    etDestination.setAdapter(adapter)
                    etFrom.setOnItemClickListener { adapterView, view, pos, l ->
                        val data = adapter.getDataAirport(pos)
                        cityFrom = data.city
                        airportFrom = data.airport
                        binding.etFrom.setText("${data.city} - ${data.code}")
                    }
                    etDestination.setOnItemClickListener { adapterView, view, pos, l ->
                        val data = adapter.getDataAirport(pos)
                        cityTo = data.city
                        airportTo = data.airport
                        binding.etDestination.setText("${data.city} - ${data.code}")
                    }
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

    private fun updateTime(kategori: String, time : String){
        if (kategori == "keberangkatan") {
            binding.etJamBerangkatInput.setText(time)
        }
        else if (kategori == "kepulangan") {
            binding.etJamKedatanganInput.setText(time)
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
        DatePickerDialog(requireActivity(),datePicker,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private fun formatDate(date : Date) : String {
        val myFormat = "EEEE, dd MMM yy"
        val dateFormat = SimpleDateFormat(myFormat)
        return dateFormat.format(date)
    }

    private fun updateLabel(kategori : String, date : Date) {
        val formatedDate = formatDate(date)
        if(kategori == "berangkat") {
            binding.etTglBerangkatInput.setText(formatedDate)
        }
        if(kategori == "selesai") {
            binding.etTglSelesaiInput.setText(formatedDate)
        }
    }

    private fun addTicket(){
        val asalKota = binding.etFrom.text.toString()
        val asalBandara = binding.etAirportFrom.text.toString()
        val destinasi = binding.etDestination.text.toString()
        val tanggal = binding.etTglBerangkatInput.text.toString()
        val destinasiBandara = binding.etAirportDestination.text.toString()
        val tanggalBerangkat = binding.etTglBerangkatInput.text.toString()
        val tanggalSelesai = binding.etTglSelesaiInput.text.toString()
        val jamBerangkat = binding.etJamBerangkatInput.text.toString()
        val jamKedatangan = binding.etJamKedatanganInput.text.toString()
        val adultPrice = binding.etAdultPrice.text.toString().toInt()
        val childPrice = binding.etChildPrice.text.toString().toInt()
        val initialStock = binding.etJmlPenumpangInput.text.toString().toInt()

        val token ="Bearer " + sharedPrefs.getString("token","tokenisnull")
        val ticketdata = TicketData(asalKota,asalBandara,destinasi,destinasiBandara,
            "2022-11-25 13:39:42.408 +00:00",jamBerangkat,jamKedatangan, "oneway",
            adultPrice,childPrice, TRUE, initialStock, initialStock )

        ticketVM.addticket(ticketdata,token).observe(viewLifecycleOwner){
            if (it != null && it.message == "ticket created") {
                Toast.makeText(context, "Data Berhasil disimpan", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Data Gagal disimpan", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun showBottomNavigation() {
        (activity as MainActivity).binding.bottomNavContainer.visibility = View.VISIBLE
    }
}