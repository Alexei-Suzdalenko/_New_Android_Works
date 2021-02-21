package sirniy.teremok.utils

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    val id: String = "",
    val company: String = "",
    val name: String = "",
    val tlf: String = "",
    val image: String = ""
) : Parcelable