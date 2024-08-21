package com.ari.prodvizhenie.details.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ari.prodvizhenie.details.domain.repository.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val statsRepository: StatsRepository
) : ViewModel() {

    private val _statisticState = MutableStateFlow(StatisticsViewState())
    val statisticState = _statisticState.asStateFlow()


    fun getStatistic(token: String, id: Int) {
        viewModelScope.launch {
            _statisticState.update { it.copy(isLoading = true) }

            statsRepository.getStatistics(id = id, token = token)
                .onRight { response ->
                    _statisticState.update { it.copy(statistics = response) }
                }
                .onLeft { error ->
                    Log.d("TAG", "getStatistic: $error")
                    _statisticState.update {
                        it.copy(error = error.error.message)
                        //TODO att toast
                    }
                }
            _statisticState.update { it.copy(isLoading = false) }
        }
    }

}