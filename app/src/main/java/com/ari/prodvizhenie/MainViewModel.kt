package com.ari.prodvizhenie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ari.prodvizhenie.auth.domain.manager.LocalUserManager
import com.ari.prodvizhenie.nav_graph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    localUserManager: LocalUserManager
) : ViewModel() {

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    var splashCondition by mutableStateOf(true)
        private set

    init {
        localUserManager.readAppEntry().onEach { shouldStartFromNavigator ->
            startDestination = if (shouldStartFromNavigator) {
                Route.AppNavigation.route
            } else {
                Route.AppStartNavigation.route
            }
            delay(300)
            splashCondition = false
        }.launchIn(viewModelScope)
    }

}