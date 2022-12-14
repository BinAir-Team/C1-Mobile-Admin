package binar.finalproject.binair.admin.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetTiketResponse(

	@field:SerializedName("data")
	val data: DataPageTicket,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataPageTicket(

	@field:SerializedName("totalItems")
	val totalItems: Int,

	@field:SerializedName("tickets")
	val tickets: List<DataTicket?>,

	@field:SerializedName("totalPages")
	val totalPages: Int,

	@field:SerializedName("currentPage")
	val currentPage: Int
)

@Parcelize
data class DataTicket(

	@field:SerializedName("arrival_time")
	val arrivalTime: String,

	@field:SerializedName("airport_to")
	val airportTo: String,

	@field:SerializedName("child_price")
	val childPrice: Int,

	@field:SerializedName("adult_price")
	val adultPrice: Int,

	@field:SerializedName("available")
	val available: Boolean,

	@field:SerializedName("curr_stock")
	val currStock: Int,

	@field:SerializedName("date_end")
	var dateEnd: String? = null,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("date_start")
	var dateStart: String,

	@field:SerializedName("airport_from")
	val airportFrom: String,

	@field:SerializedName("from")
	val from: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("to")
	val to: String,

	@field:SerializedName("init_stock")
	val initStock: Int,

	@field:SerializedName("departure_time")
	val departureTime: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
) : Parcelable
