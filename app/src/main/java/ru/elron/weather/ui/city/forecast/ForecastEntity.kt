package ru.elron.weather.ui.city.forecast

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import ru.elron.libcore.base.AEntity

class ForecastEntity : AEntity {
    val city = ObservableField<String>("")
    val progressVisible = ObservableBoolean(false)

    constructor() : super()
    constructor(parcel: Parcel) : this() {
        city.set(parcel.readString())
        progressVisible.set(parcel.readInt() != 0)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(city.get())
        parcel.writeInt(if (progressVisible.get()) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ForecastEntity> {
        override fun createFromParcel(parcel: Parcel): ForecastEntity {
            return ForecastEntity(parcel)
        }

        override fun newArray(size: Int): Array<ForecastEntity?> {
            return arrayOfNulls(size)
        }
    }
}