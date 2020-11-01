package ru.elron.weather.ui.add.list

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.elron.libcore.base.BaseFragment
import ru.elron.libcore.base.BaseViewModel
import ru.elron.weather.App
import ru.elron.weather.R
import ru.elron.weather.databinding.FragmentAddListBinding
import ru.elron.weather.view.LifecycleDialogFragment
import ru.elron.weather.view.setSubtitle
import ru.elron.weather.view.setTitle
import ru.elron.weather.view.showButtonBack

class AddListFragment : BaseFragment<AddListEntity, AddListState, AddListEvent>(), LifecycleDialogFragment.Builder {
    companion object {
        const val DIALOG_ERROR_ADD = 100
    }

    private lateinit var binding: FragmentAddListBinding
    val viewModel: AddListViewModel by viewModels {
        AddListViewModelFactory(
            App.INSTANCE,
            this
        )
    }
    val args: AddListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        viewModel.onCreateView(args.city)
        binding.entity = viewModel.entity

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setTitle(getString(R.string.add_title))
        setSubtitle(null)
        showButtonBack()
    }

    override fun onEvent(event: AddListEvent) {
        when(event) {
            is AddListEvent.OnAddItemSuccess -> {
                Toast.makeText(requireActivity(), R.string.toast_add_list_success, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            is AddListEvent.OnAddItemError -> {
                LifecycleDialogFragment()
                    .withId(DIALOG_ERROR_ADD)
                    .withChildFragmentId(R.id.nav_host_fragment, id)
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
            DIALOG_ERROR_ADD -> {
                builder.setTitle(R.string.dialog_add_list_error_add_title)
                builder.setMessage(R.string.dialog_add_list_error_add_message)
                builder.setPositiveButton(R.string.button_ok, null)
            }
        }

        dialog = builder.create()
        return dialog
    }

    override fun getMainViewModel(): BaseViewModel<AddListEntity, AddListState, AddListEvent> {
        return viewModel
    }

}