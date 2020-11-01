package ru.elron.libcore.base

import android.os.Parcel
import android.os.Parcelable

/**
 * Хранит состояние на случай поворота экрана и сворачивания приложения
 * Требования:
 * - объекты в классе не должны быть null
 * - хранятся только те объекты которые влияют на View, например текст, value в ProgressView, OnClickListener
 * - без ссылок на ресурсы
 */
abstract class AEntity() : Parcelable {
    constructor(parcel: Parcel) : this() {
        isSetuped = parcel.readInt() != 0
    }
    var isSetuped = false

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(if (isSetuped) 1 else 0)
    }
}
