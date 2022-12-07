package binar.finalproject.binair.admin.data.response


import com.google.gson.annotations.SerializedName

data class LogOutResponse(
    @SerializedName("data")
    val `data`: DataLogout?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)

data class DataLogout(
    @SerializedName("user")
    val user: List<Int?>?
)