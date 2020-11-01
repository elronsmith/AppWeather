package ru.elron.weather.ui.main

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.databinding.ObservableBoolean
import ru.elron.libcore.base.AEntity

class MainEntity : AEntity {
    val progressVisible = ObservableBoolean(false)
    val addEnabled = ObservableBoolean(false)

    var addListener: View.OnClickListener? = null

    constructor() : super()
    constructor(parcel: Parcel) : this() {
        progressVisible.set(parcel.readInt() != 0)
        addEnabled.set(parcel.readInt() != 0)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeInt(if (progressVisible.get()) 1 else 0)
        parcel.writeInt(if (addEnabled.get()) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<MainEntity> {
        override fun createFromParcel(parcel: Parcel): MainEntity {
            return MainEntity(parcel)
        }

        override fun newArray(size: Int): Array<MainEntity?> {
            return arrayOfNulls(size)
        }
    }
}