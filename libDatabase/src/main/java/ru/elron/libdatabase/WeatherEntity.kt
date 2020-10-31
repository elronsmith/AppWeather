package ru.elron.libdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = WeatherEntity.TABLE_NAME)
class WeatherEntity() {
    companion object {
        const val TABLE_NAME = "weather"
    }

    constructor(_id: Long, _city: String, _data: String, _date: Long) : this() {
        this.id = _id
        this.city = _city
        this.data = _data
        this.date = _date
    }

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0L

    @ColumnInfo(name = "city")
    var city: String = ""

    @ColumnInfo(name = "data")
    var data: String = ""

    @ColumnInfo(name = "date")
    var date: Long = 0

    override fun equals(other: Any?): Boolean {
        return other is WeatherEntity &&
                other.id == id &&
                other.city.equals(city)
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + id.hashCode()
        result = prime * result + city.hashCode()
        return result
    }
}
