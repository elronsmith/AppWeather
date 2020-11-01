package ru.elron.weather.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.elron.libcore.base.BaseFragment
import ru.elron.libcore.base.BaseViewModel
import ru.elron.weather.App
import ru.elron.weather.databinding.FragmentCityBinding
import ru.elron.weather.view.setSubtitle
import ru.elron.weather.view.setTitle
import ru.elron.weather.view.showButtonBack

/**
 * Погода в городе
 */
class CityFragment : BaseFragment<CityEntity, CityState, CityEvent>() {

    private lateinit var binding: FragmentCityBinding
    val viewModel: CityViewModel by viewModels {
        CityViewModelFactory(
            App.INSTANCE,
            this
        )
    }

    val args: CityFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        viewModel.onCreateView(args.city)
        binding.entity = viewModel.entity

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setTitle(viewModel.entity.city.get())
        setSubtitle(null)
        showButtonBack()
    }

    override fun onState(state: CityState) {
        when(state) {
            is CityState.Idle -> {
                viewModel.requestGetWeather()
            }
            is CityState.Loading -> {}
            is CityState.Completed -> {}
        }
    }

    override fun onEvent(event: CityEvent) {
        when(event) {
            is CityEvent.ShowScreenForecast -> {
                findNavController().navigate(CityFragmentDirections.actionCityFragmentToForecastFragment(event.city))
            }
        }
    }

    override fun getMainViewModel(): BaseViewModel<CityEntity, CityState, CityEvent> {
        return viewModel
    }
}