@file:Suppress("KotlinDeprecation", "KotlinDeprecation", "RemoveEmptySecondaryConstructorBody")

package binar.finalproject.binair.admin.data.model

import android.os.Parcel
import android.os.Parcelable

@Suppress("KotlinDeprecation", "RemoveEmptySecondaryConstructorBody")
class News(val id: Int, val imgUrl: Int):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()!!,
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(imgUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<News> {
        override fun createFromParcel(parcel: Parcel): News {
            return News(parcel)
        }

        override fun newArray(size: Int): Array<News?> {
            return arrayOfNulls(size)
        }
    }
}