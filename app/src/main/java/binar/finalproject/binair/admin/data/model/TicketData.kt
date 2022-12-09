package binar.finalproject.binair.admin.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class TicketData(
    val from: String?,
    val airport_from: String,
    val to: String?,
    val airport_to: String,
    val date: String?,
    val departure_time: String?,
    val arrival_time: String,
    val type: String?,
    val adult_price: Int,
    val child_price: Int?,
    val available: Boolean,
    val init_stock: Int?,
    val curr_stock: Int?,
)