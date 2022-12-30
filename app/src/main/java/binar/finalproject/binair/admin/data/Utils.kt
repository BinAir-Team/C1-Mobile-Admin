package binar.finalproject.binair.admin.data

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun formatRupiah(value: Int): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    return formatter.format(value)
}

fun formatDateFromISO(date: String): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val newDate = formatter.parse(date)
    val formatter2 = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
    return formatter2.format(newDate)
}