package ru.elron.weather.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Если RecyclerView пустой, то отображается View который говорит что список пустой
 */
class EmptyRecyclerView : RecyclerView {
    private var emptyView: View? = null
    private val emptyObserver = MyAdapterDataObserver()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    fun <T : ViewHolder>setup(adapter: Adapter<T>?,
                              emptyView: View,
                              layoutManager: LayoutManager? = null) {
        this.layoutManager = layoutManager ?: LinearLayoutManager(context)
        this.setEmptyView(emptyView)
        this.adapter = adapter
    }

    override fun setAdapter(adapter: Adapter<ViewHolder>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(emptyObserver)
        emptyObserver.onChanged()
    }

    /**
     * указывает View, которое будет отображено если список пустой
     */
    fun setEmptyView(emptyView: View?, onChanged: Boolean = false) {
        this.emptyView = emptyView
        if (onChanged) emptyObserver.onChanged()
    }

    fun onChanged() = emptyObserver.onChanged()

    inner class MyAdapterDataObserver : AdapterDataObserver() {
        override fun onChanged() {
            val adapter = adapter
            adapter?.let {
                emptyView?.let {
                    if (adapter.itemCount == 0) {
                        emptyView!!.visibility = View.VISIBLE
                        this@EmptyRecyclerView.visibility = View.GONE
                    } else {
                        emptyView!!.visibility = View.GONE
                        this@EmptyRecyclerView.visibility = View.VISIBLE
                    }
                }
            }
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            onChanged()
        }
    }
}
