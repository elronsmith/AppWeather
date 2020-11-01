package ru.elron.weather.ui.city.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import ru.elron.libcore.base.BaseFragment
import ru.elron.libcore.base.BaseViewModel
import ru.elron.weather.App
import ru.elron.weather.R
import ru.elron.weather.databinding.FragmentCityForecastBinding
import ru.elron.weather.view.setSubtitle
import ru.elron.weather.view.setTitle
import ru.elron.weather.view.showButtonBack

/**
 * Прогноз погоды в городе
 */
class ForecastFragment : BaseFragment<ForecastEntity, ForecastState, ForecastEvent>() {
    lateinit var binding: FragmentCityForecastBinding
    val viewModel: ForecastViewModel by activityViewModels {
        ForecastViewModelFactory(
            App.INSTANCE,
            this
        )
    }
    val args: ForecastFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityForecastBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        viewModel.onCreateView(args.city)
        binding.entity = viewModel.entity

        binding.recyclerView.setup(viewModel.adapter, binding.emptyTextView)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setTitle(viewModel.entity.city.get())
        setSubtitle(getString(R.string.forecast_subtitle))
        showButtonBack()
    }

    override fun onState(state: ForecastState) {
        when(state) {
            is ForecastState.Idle -> {
                viewModel.requestGetWeather()
            }
            is ForecastState.Loading -> {}
            is ForecastState.Completed -> {}
        }
    }

    override fun getMainViewModel(): BaseViewModel<ForecastEntity, ForecastState, ForecastEvent> {
        return viewModel
    }
}