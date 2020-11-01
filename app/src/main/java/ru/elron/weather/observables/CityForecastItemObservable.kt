package ru.elron.weather.observables

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import ru.elron.weather.R
import ru.elron.weather.databinding.ItemCityForecastBinding
import ru.elron.weather.view.*

class CityForecastItemObservable(val info: String) : AObservable(CityForecastItemViewHolder.ID)

class CityForecastItemViewHolder(
    val binding: ItemCityForecastBinding,
    callback: OnItemClickViewHolderCallback
) : ClickableViewHolder(binding.root, callback) {
    companion object {
        internal const val ID = R.layout.item_city_forecast

        fun addViewHolder(
            builderList: SparseArrayCompat<ViewHolderBuilder>,
            callback: OnItemClickViewHolderCallback
        ) {
            builderList.put(ID, object : ViewHolderBuilder {
                override fun create(parent: ViewGroup): ViewHolder<*> {
                    return CityForecastItemViewHolder(
                        ItemCityForecastBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        ),
                        callback
                    )
                }
            })
        }
    }

    override fun update(position: Int) {
        val o = callback.getObservable(position) as CityForecastItemObservable
        binding.infoTextView.text = o.info
    }
}
