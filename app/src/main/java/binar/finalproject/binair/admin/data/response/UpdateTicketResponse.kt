package binar.finalproject.binair.admin.data.response


import com.google.gson.annotations.SerializedName

data class UpdateTicketResponse(
    @SerializedName("data")
    val `data`: List<Int?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)