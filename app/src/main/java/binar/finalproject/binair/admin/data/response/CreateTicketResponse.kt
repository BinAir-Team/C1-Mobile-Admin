package binar.finalproject.binair.admin.data.response


import com.google.gson.annotations.SerializedName

data class CreateTicketResponse(
    @SerializedName("data")
    val `data`: DataTicketResponse?,
    @SerializedName("message")
    val message: String?
)