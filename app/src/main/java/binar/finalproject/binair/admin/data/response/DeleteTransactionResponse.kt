package binar.finalproject.binair.admin.data.response


import com.google.gson.annotations.SerializedName

data class DeleteTransactionResponse(
    @SerializedName("data")
    val `data`: Int?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("status")
    val status: Int?
)