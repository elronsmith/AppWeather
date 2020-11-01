package ru.elron.weather.ui.add.list

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import ru.elron.libcore.base.AEntity

class AddListEntity : AEntity {
    val city = ObservableField<String>("")
    val addEnabled = ObservableBoolean(true)

    var addListener: View.OnClickListener? = null

    constructor() : super()
    constructor(parcel: Parcel) : this() {
        city.set(parcel.readString())
        addEnabled.set(parcel.readInt() != 0)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(city.get())
        parcel.writeInt(if (addEnabled.get()) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<AddListEntity> {
        override fun createFromParcel(parcel: Parcel): AddListEntity {
            return AddListEntity(parcel)
        }

        override fun newArray(size: Int): Array<AddListEntity?> {
            return arrayOfNulls(size)
        }
    }
}