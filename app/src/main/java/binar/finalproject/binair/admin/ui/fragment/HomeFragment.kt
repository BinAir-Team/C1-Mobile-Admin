@file:Suppress("RedundantSemicolon", "RemoveEmptyParenthesesFromLambdaCall",
    "RemoveEmptyParenthesesFromLambdaCall", "RemoveEmptyParenthesesFromLambdaCall",
    "RemoveEmptyParenthesesFromLambdaCall", "RemoveEmptyParenthesesFromLambdaCall",
    "RemoveEmptyParenthesesFromLambdaCall"
)

package binar.finalproject.binair.admin.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import binar.finalproject.binair.admin.data.Constant.dataUser
import binar.finalproject.binair.admin.data.model.TicketData
import binar.finalproject.binair.admin.data.response.CityAirport
import binar.finalproject.binair.admin.databinding.FragmentHomeBinding
import binar.finalproject.binair.admin.ui.activity.MainActivity
import binar.finalproject.binair.admin.ui.adapter.AutoCompleteAirportAdapter
import binar.finalproject.binair.admin.viewmodel.TicketViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Boolean.TRUE
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

@Suppress("UNCHECKED_CAST", "RedundantSemicolon", "UNUSED_ANONYMOUS_PARAMETER",
    "RedundantSemicolon", "RedundantSemicolon"
)
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
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        ticketVM = ViewModelProvider(this).get(TicketViewModel::class.java)
        sharedPrefs = requireActivity().getSharedPreferences(dataUser, 0)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setAutoCompleteData()
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

    private fun initData(){
        val now = Calendar.getInstance().time
        val formatedDate = formatDate(now)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        var mnt = ""
        if(minute.toString() == "0"){
            mnt = "00"
        } else if(minute.toString() in "1".."9"){
            mnt = "0$minute"
        } else{
            mnt = minute.toString()
        }
        val time = "$hour:$mnt"
        binding.etTglBerangkatInput.setText(formatedDate)
        binding.etTglSelesaiInput.setText(formatedDate)
        binding.etJamBerangkatInput.setText(time)
        binding.etJamKedatanganInput.setText(time)
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    private fun setAutoCompleteData() {
        val dataTipe = arrayOf("Sekali Jalan", "Pulang Pergi")
        val adapterTipe = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, dataTipe)
        ticketVM.callGetCityAirport().observe(viewLifecycleOwner){
            if(it != null){
                val city = it
                val adapter = AutoCompleteAirportAdapter(requireContext(), city as ArrayList<CityAirport?>)
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
                    etTipe.threshold = 0
                    etTipe.setAdapter(adapterTipe)
                    etTipe.setOnItemClickListener(){ adapterView, view, pos, l ->
                        if(dataTipe[pos] == "Pulang Pergi"){
                            binding.tglSelesaiInputContainer.visibility = View.VISIBLE
                        }else{
                            binding.tglSelesaiInputContainer.visibility = View.GONE
                        }
                        binding.etTipe.setText(dataTipe[pos])
                    }
                    etTipe.setOnClickListener{
                        etTipe.maxLines = 5
                        etTipe.showDropDown()
                    }
                }
            }
        }
    }

    private fun showTimePickerDialog(kategori: String){
        val timePicker = TimePickerDialog(requireContext(), { view, hourOfDay, minute ->
            var mnt = ""
            if(minute.toString() == "0"){
                mnt = "00"
            } else if(minute.toString() in "1".."9"){
                mnt = "0$minute"
            } else{
                mnt = minute.toString()
            }
            val time = "$hourOfDay:$mnt"
            updateTime(kategori, time)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
        timePicker.show()
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
        val dateFormat = SimpleDateFormat(myFormat, Locale("id", "ID"))
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
        val tanggalBerangkat = binding.etTglBerangkatInput.text.toString()
        var tanggalSelesai : String? = binding.etTglSelesaiInput.text.toString()
        val jamBerangkat = binding.etJamBerangkatInput.text.toString()
        val jamKedatangan = binding.etJamKedatanganInput.text.toString()
        var tipe = binding.etTipe.text.toString()
        val adultPrice = binding.etAdultPrice.text.toString().toInt()
        val childPrice = binding.etChildPrice.text.toString().toInt()
        val initialStock = binding.etJmlPenumpangInput.text.toString().toInt()

        if(tipe == "Sekali Jalan"){
            tipe = "oneway"
            tanggalSelesai = null
        }else{
            tipe = "roundtrip"
        }

        val token ="Bearer " + sharedPrefs.getString("token","tokenisnull")
        val ticketdata = TicketData(cityFrom,airportFrom,cityTo,airportTo,
            tanggalBerangkat,tanggalSelesai,jamBerangkat,jamKedatangan, tipe,
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