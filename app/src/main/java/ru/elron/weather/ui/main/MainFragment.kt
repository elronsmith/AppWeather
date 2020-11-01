package ru.elron.weather.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.elron.libcore.base.BaseFragment
import ru.elron.libcore.base.BaseViewModel
import ru.elron.weather.App
import ru.elron.weather.R
import ru.elron.weather.databinding.FragmentMainBinding
import ru.elron.weather.view.LifecycleDialogFragment
import ru.elron.weather.view.hideButtonBack
import ru.elron.weather.view.setSubtitle
import ru.elron.weather.view.setTitle

/**
 * Список городов
 */
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
        hideButtonBack()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stateLiveData.value = MainState.Idle
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
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddFragment())
            }
            is MainEvent.ShowScreenCity -> {
                // TODO
            }
            is MainEvent.ShowDialogDeleteItem -> {
                LifecycleDialogFragment()
                    .withId(DIALOG_ITEM_DELETE)
                    .withChildFragmentId(R.id.nav_host_fragment, id)
                    .withIndex(event.index)
                    .withMessage(event.city)
                    .show(requireActivity())
            }
            is MainEvent.ShowToastDeleteOk -> {
                Toast.makeText(requireActivity(), R.string.toast_city_deleted, Toast.LENGTH_SHORT).show()
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
                val city = dialogFragment.getBundleMessage()!!
                val index = dialogFragment.getBundleIndex()
                builder.setTitle(R.string.dialog_main_delete_item_title)
                builder.setMessage(getString(R.string.dialog_main_delete_item_message, city))
                builder.setPositiveButton(R.string.button_delete) {_, _ ->
                    viewModel.deleteItem(index)
                }
            }
        }

        dialog = builder.create()
        return dialog
    }
}