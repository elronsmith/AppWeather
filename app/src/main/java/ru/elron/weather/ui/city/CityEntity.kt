package ru.elron.weather.ui.city

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.databinding.ObservableField
import ru.elron.libcore.base.AEntity

class CityEntity : AEntity {
    val city = ObservableField<String>("")
    val info = ObservableField<String>("")

    var forecastListener: View.OnClickListener? = null

    constructor() : super()
    constructor(parcel: Parcel) : this() {
        city.set(parcel.readString())
        info.set(parcel.readString())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(city.get())
        parcel.writeString(info.get())
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CityEntity> {
        override fun createFromParcel(parcel: Parcel): CityEntity {
            return CityEntity(parcel)
        }

        override fun newArray(size: Int): Array<CityEntity?> {
            return arrayOfNulls(size)
        }
    }
}