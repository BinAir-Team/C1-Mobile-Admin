package binar.finalproject.binair.admin.data.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class GetTiketResponse(
    @SerializedName("data")
    val `data`: List<DataTicket>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)
@Parcelize
data class DataTicket(
    @SerializedName("adult_price")
    val adultPrice: Int?,
    @SerializedName("airport_from")
    val airportFrom: String?,
    @SerializedName("airport_to")
    val airportTo: String?,
    @SerializedName("arrival_time")
    val arrivalTime: String?,
    @SerializedName("available")
    val available: Boolean?,
    @SerializedName("child_price")
    val childPrice: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("curr_stock")
    val currStock: Int?,
    @SerializedName("date_start")
    var date_start: String,
    @SerializedName("date_end")
    var date_end: String,
    @SerializedName("departure_time")
    val departureTime: String?,
    @SerializedName("from")
    val from: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("init_stock")
    val initStock: Int?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
) : Parcelable