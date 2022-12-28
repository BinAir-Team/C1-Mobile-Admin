package binar.finalproject.binair.admin.data.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetAllTransaction(
    @SerializedName("data")
    val `data`: DataGetAllTransaction?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("status")
    val status: Int?
)

data class DataGetAllTransaction(
    @SerializedName("currentPage")
    val currentPage: Int?,
    @SerializedName("totalItems")
    val totalItems: Int?,
    @SerializedName("totalPages")
    val totalPages: Int?,
    @SerializedName("transactions")
    val transactions: List<TransactionGetAllTransaction?>?
)

@Parcelize
data class TransactionGetAllTransaction(
    @SerializedName("amounts")
    val amounts: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("date")
    var date: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("payment_method")
    val paymentMethod: String?,
    @SerializedName("quantity")
    val quantity: Quantity?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("ticket")
    val ticket: Ticket?,
    @SerializedName("ticketsId")
    val ticketsId: String?,
    @SerializedName("traveler")
    val traveler: List<TravelerGetAllTransaction?>?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("usersId")
    val usersId: String?
) : Parcelable

@Parcelize
data class TravelerGetAllTransaction(
    @SerializedName("datebirth")
    val datebirth: String?,
    @SerializedName("id_card")
    val idCard: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nationality")
    val nationality: String?,
    @SerializedName("no_ktp")
    val noKtp: String?,
    @SerializedName("surname")
    val surname: String?,
    @SerializedName("tittle")
    val tittle: String?,
    @SerializedName("type")
    val type: String?
) : Parcelable

@Parcelize
data class Ticket(
    @SerializedName("adult_price")
    val adultPrice: Int?,
    @SerializedName("airport_from")
    val airportFrom: String?,
    @SerializedName("airport_to")
    val airportTo: String?,
    @SerializedName("arrival_time")
    val arrivalTime: String?,
    @SerializedName("child_price")
    val childPrice: Int?,
    @SerializedName("date_end")
    val dateEnd: String?,
    @SerializedName("date_start")
    val dateStart: String?,
    @SerializedName("departure_time")
    val departureTime: String?,
    @SerializedName("from")
    val from: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("type")
    val type: String?
): Parcelable

@Parcelize
data class Quantity(
    @SerializedName("adult")
    val adult: Int?,
    @SerializedName("child")
    val child: Int?
) : Parcelable

@Parcelize
data class User(
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("profile_image")
    val profileImage: String?,
    @SerializedName("verified")
    val verified: Boolean?
) : Parcelable