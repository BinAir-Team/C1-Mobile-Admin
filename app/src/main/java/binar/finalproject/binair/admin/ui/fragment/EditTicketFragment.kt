package binar.finalproject.binair.admin.ui.fragment

import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import binar.finalproject.binair.admin.data.Constant
import binar.finalproject.binair.admin.data.model.TicketData
import binar.finalproject.binair.admin.data.response.CityAirport
import binar.finalproject.binair.admin.data.response.DataTicket
import binar.finalproject.binair.admin.databinding.FragmentEditTicketBinding
import binar.finalproject.binair.admin.ui.adapter.AutoCompleteAirportAdapter
import binar.finalproject.binair.admin.viewmodel.TicketViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Boolean
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.min

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class EditTicketFragment : Fragment() {
    private lateinit var binding : FragmentEditTicketBinding
    private lateinit var clickedTicket : DataTicket
    private val calendar = Calendar.getInstance()
    lateinit var ticketVM : TicketViewModel
    private lateinit var sharedPrefs : SharedPreferences
    private var cityFrom : String = ""
    private var airportFrom : String = ""
    private var cityTo : String = ""
    private var airportTo : String = ""

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
        setAutoCompleteData()
        setDataToView()
    }
    private fun setListener(){
        binding.apply {
            btnSave.setOnClickListener{
                saveedit()
            }
            etTglBerangkatInput.setOnClickListener {
                showDatePickerDialog("berangkat",clickedTicket.dateStart)
            }
            etTglBerangkatInput.setOnFocusChangeListener { view, b ->
                if (b) {
                    showDatePickerDialog("berangkat",clickedTicket.dateStart)
                }
            }
            etTglSelesaiInput.setOnClickListener {
                showDatePickerDialog("selesai",clickedTicket.dateStart)
            }
            etTglSelesaiInput.setOnFocusChangeListener { view, b ->
                if (b) {
                    showDatePickerDialog("selesai",clickedTicket.dateStart)
                }
            }
            etJamBerangkatInput.setOnClickListener{
                showTimePickerDialog("keberangkatan",clickedTicket.departureTime)
            }
            etJamBerangkatInput.setOnFocusChangeListener { view, b ->
                if (b) {
                    showTimePickerDialog("keberangkatan",clickedTicket.departureTime)
                }
            }
            etJamKedatanganInput.setOnClickListener{
                showTimePickerDialog("kepulangan",clickedTicket.arrivalTime)
            }
            etJamKedatanganInput.setOnFocusChangeListener { view, b ->
                if (b) {
                    showTimePickerDialog("kepulangan",clickedTicket.arrivalTime)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun showTimePickerDialog(kategori: String, jam : String){
        try {
            val time = SimpleDateFormat("hh:mm").parse(jam)
            val hour = time?.hours
            val minute = time?.minutes
            if(hour != null && minute != null){
                val timePicker = TimePickerDialog(requireContext(), { view, hourOfDay, minute ->
                    val time = hourOfDay.toString() + ":" + if(minute.toString() == "0") "00" else(minute.toString())
                    updateTime(kategori, time)
                }, hour, minute, true)
                timePicker.show()
            }
        }catch (e: Exception){
            e.printStackTrace()
            val timePicker = TimePickerDialog(requireContext(), { view, hourOfDay, minute ->
                val time = hourOfDay.toString() + ":" + if(minute.toString() == "0") "00" else(minute.toString())
                updateTime(kategori, time)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
            timePicker.show()
        }
    }

    private fun showDatePickerDialog(kategori: String, date : String) {
        val dt = formatDateToAPI(date).split("-")
//        calendar.set(Integer.valueOf(dt.get(0)),Integer.valueOf(dt.get(1)),Integer.valueOf(dt.get(2)))
//        Log.e("date", "${calendar.get(Calendar.YEAR)}")
//        Log.e("date", "${calendar.get(Calendar.MONTH)}")
//        Log.e("date", "${calendar.get(Calendar.DAY_OF_MONTH)}")
        val datePicker =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel(kategori, calendar.time)
            }
//        DatePickerDialog(requireActivity(),datePicker,)).show();
        DatePickerDialog(requireActivity(),datePicker,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setAutoCompleteData() {
        val dataTipe = arrayOf("Sekali Jalan", "Pulang Pergi")
        val adapterTipe = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, dataTipe)
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
        val formatedDateStart = formatDateFrString(oldDateStart)
        clickedTicket.dateStart = formatedDateStart

        val oldDateEnd = clickedTicket.dateEnd
        val formatedDateEnd = oldDateEnd?.let { formatDateFrString(it) }
        clickedTicket.dateEnd = formatedDateEnd
        binding.ticket = clickedTicket
    }

    fun formatDateFrString(date : String) : String {
        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDate = LocalDate.parse(date, formatter)
            val formatter2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("id","ID"))
            return localDate.format(formatter2)
        }catch (e : Exception){
            return date
        }
    }

    fun formatDateToAPI(date: String) : String{
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy",Locale("id", "ID"))).toString()
    }

    private fun saveedit(){
        if (cityFrom == "" || cityTo == "" || airportFrom == "" || airportTo == "") {
            cityFrom = clickedTicket.from
            cityTo = clickedTicket.to
            airportFrom = clickedTicket.airportFrom
            airportTo = clickedTicket.airportTo
        }
        val tanggalBerangkat = binding.etTglBerangkatInput.text.toString()
        var tanggalSelesai : String? = binding.etTglSelesaiInput.text.toString()
        val jamBerangkat = formatDateToAPI(binding.etJamBerangkatInput.text.toString())
        val jamKedatangan = formatDateToAPI(binding.etJamKedatanganInput.text.toString())
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