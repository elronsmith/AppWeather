package ru.elron.weather.ui.add

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.google.android.material.internal.TextWatcherAdapter
import ru.elron.libcore.base.AEntity

class AddEntity : AEntity {
    val city = ObservableField<String>("")
    val progressVisible = ObservableBoolean(false)
    val searchEnabled = ObservableBoolean(true)

    val cityListener = object : TextWatcherAdapter() {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            city.set(s.toString())
        }
    }
    var searchListener: View.OnClickListener? = null
    var cityEditorListener: TextView.OnEditorActionListener? = null

    constructor() : super()
    constructor(parcel: Parcel) : this() {
        city.set(parcel.readString())
        progressVisible.set(parcel.readInt() != 0)
        searchEnabled.set(parcel.readInt() != 0)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(city.get())
        parcel.writeInt(if (progressVisible.get()) 1 else 0)
        parcel.writeInt(if (searchEnabled.get()) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<AddEntity> {
        override fun createFromParcel(parcel: Parcel): AddEntity {
            return AddEntity(parcel)
        }

        override fun newArray(size: Int): Array<AddEntity?> {
            return arrayOfNulls(size)
        }
    }
}