package binar.finalproject.binair.admin.data.response


import com.google.gson.annotations.SerializedName

data class DeleteTicketResponse(
    @SerializedName("data")
    val `data`: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)