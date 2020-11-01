package ru.elron.weather.observables

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import ru.elron.weather.R
import ru.elron.weather.databinding.ItemMainBinding
import ru.elron.weather.view.*

class MainItemObservable(val city: String, val degree: String) : AObservable(MainItemViewHolder.ID)

class MainItemViewHolder(
    val binding: ItemMainBinding,
    callback: OnLongItemClickViewHolderCallback
) : LongClickableViewHolder(binding.root, callback) {
    companion object {
        internal const val ID = R.layout.item_main

        fun addViewHolder(
            builderList: SparseArrayCompat<ViewHolderBuilder>,
            callback: OnLongItemClickViewHolderCallback
        ) {
            builderList.put(ID, object : ViewHolderBuilder {
                override fun create(parent: ViewGroup): ViewHolder<*> {
                    return MainItemViewHolder(
                        ItemMainBinding.inflate(
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
        val o = callback.getObservable(position) as MainItemObservable
        binding.rootItem.setOnClickListener(this)
        binding.observable = o

    }
}
