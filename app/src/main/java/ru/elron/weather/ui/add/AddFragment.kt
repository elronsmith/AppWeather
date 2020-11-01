package ru.elron.weather.ui.add

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.elron.libcore.base.BaseFragment
import ru.elron.libcore.base.BaseViewModel
import ru.elron.weather.App
import ru.elron.weather.R
import ru.elron.weather.databinding.FragmentAddBinding
import ru.elron.weather.view.LifecycleDialogFragment
import ru.elron.weather.view.setSubtitle
import ru.elron.weather.view.setTitle
import ru.elron.weather.view.showButtonBack

/**
 * Поиск и добавление города
 */
class AddFragment : BaseFragment<AddEntity, AddState, AddEvent>(), LifecycleDialogFragment.Builder {
    companion object {
        const val DIALOG_ERROR_CITY_NOT_FOUND = 100
    }

    private lateinit var binding: FragmentAddBinding
    val viewModel: AddViewModel by viewModels {
        AddViewModelFactory(
            App.INSTANCE,
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.entity = viewModel.entity

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setTitle(getString(R.string.add_title))
        setSubtitle(null)
        showButtonBack()
    }

    override fun getMainViewModel(): BaseViewModel<AddEntity, AddState, AddEvent> {
        return viewModel
    }

    override fun onState(state: AddState) {
        when(state) {
            is AddState.Idle -> {}
            is AddState.ErrorCityEmpty -> {
                binding.cityTextField.error = getString(R.string.error_city_empty)
            }
            is AddState.Loading -> {
                binding.cityTextField.error = null
            }
            is AddState.Completed -> {}
        }
    }

    override fun onEvent(event: AddEvent) {
        when(event) {
            is AddEvent.ShowScreenAddList -> {
                findNavController().navigate(AddFragmentDirections.actionAddFragmentToAddListFragment(event.city))
            }
            is AddEvent.ShowDialogErrorCityNotFound -> {
                LifecycleDialogFragment()
                    .withId(DIALOG_ERROR_CITY_NOT_FOUND)
                    .withChildFragmentId(R.id.nav_host_fragment, id)
                    .withMessage(event.city)
                    .show(requireActivity())
            }
        }
    }

    override fun getLifecycleDialogInstance(
        id: Int,
        dialogFragment: LifecycleDialogFragment
    ): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        var dialog: Dialog

        when (id) {
            DIALOG_ERROR_CITY_NOT_FOUND -> {
                builder.setTitle(R.string.dialog_add_error_city_not_found_title)
                builder.setMessage(getString(R.string.dialog_add_error_city_not_found_message, dialogFragment.getBundleMessage()))
                builder.setPositiveButton(R.string.button_ok, null)
            }
        }

        dialog = builder.create()
        return dialog
    }
}
