package ru.elron.weather.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import ru.elron.libcore.base.BaseFragment
import ru.elron.libcore.base.BaseViewModel
import ru.elron.weather.App
import ru.elron.weather.R
import ru.elron.weather.databinding.FragmentMainBinding
import ru.elron.weather.view.LifecycleDialogFragment
import ru.elron.weather.view.setSubtitle
import ru.elron.weather.view.setTitle

class MainFragment : BaseFragment<MainEntity, MainState, MainEvent>(), LifecycleDialogFragment.Builder {
    companion object {
        const val DIALOG_ITEM_DELETE = 100
    }

    lateinit var binding: FragmentMainBinding
    val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
            App.INSTANCE,
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.entity = viewModel.entity

        binding.recyclerView.setup(viewModel.adapter, binding.emptyTextView)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setTitle(getString(R.string.main_title))
        setSubtitle(null)
    }

    override fun onState(state: MainState) {
        when(state) {
            is MainState.Idle -> {
                viewModel.requestGetWeather()
            }
            is MainState.Loading -> {}
            is MainState.Completed -> {}
        }
    }

    override fun onEvent(event: MainEvent) {
        when(event) {
            is MainEvent.ShowScreenAdd -> {
                // TODO
            }
            is MainEvent.ShowScreenCity -> {
                // TODO
            }
            is MainEvent.ShowDialogDeleteItem -> {
                // TODO
            }
        }
    }

    override fun getMainViewModel(): BaseViewModel<MainEntity, MainState, MainEvent> = viewModel
    override fun getLifecycleDialogInstance(
        id: Int,
        dialogFragment: LifecycleDialogFragment
    ): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        var dialog: Dialog

        when(id) {
            DIALOG_ITEM_DELETE -> {
                // TODO
            }
        }

        dialog = builder.create()
        return dialog
    }
}