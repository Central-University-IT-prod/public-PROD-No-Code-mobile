package com.ari.prodvizhenie.nav_graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.ari.prodvizhenie.R
import com.ari.prodvizhenie.auth.presentation.login_screen.LoginScreen
import com.ari.prodvizhenie.auth.presentation.login_screen.LoginViewModel
import com.ari.prodvizhenie.calendar.domain.model.PostInfo
import com.ari.prodvizhenie.calendar.presentation.CalendarHeader
import com.ari.prodvizhenie.details.presentation.DetailsScreen

@Composable
fun NavGraph(
    startDestination: String
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.LoginScreen.route
        ) {
            composable(
                route = Route.LoginScreen.route
            ) {
                LoginScreen()
            }
        }

        val customNavOptions = navOptions {
            anim {
                enter = R.anim.slide_in_from_right
                exit = R.anim.slide_out_to_left
                popEnter = R.anim.slide_in_from_left
                popExit = R.anim.slide_out_to_right
            }
        }

        navigation(
            route = Route.AppNavigation.route,
            startDestination = Route.CalendarScreen.route
        ) {
            composable(
                route = Route.CalendarScreen.route,
                exitTransition = null,
                enterTransition = null
            ) {
                val viewModel: LoginViewModel = hiltViewModel()


                CalendarHeader(
                    onLogout = {
                        viewModel.deleteAppEntry()
                        navController.navigate(Route.AppStartNavigation.route)
                    },
                    navigateToDetails = { obj ->
                        navigateToDetails(
                            navController = navController, postInfo = obj
                        )
                    }
                )
            }

            composable(
                route = Route.DetailsScreen.route,
                enterTransition = null,
                exitTransition = null
            ) {

                navController.previousBackStackEntry?.savedStateHandle?.get<PostInfo>("postInfo")
                    ?.let { obj ->
                        DetailsScreen(
                            navigateUp = { navController.navigateUp() },
                            postInfo = obj
                        )
                    }
            }
        }


    }

}


private fun navigateToDetails(
    navController: NavController, postInfo: PostInfo
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("postInfo", postInfo)

    val customNavOptions = navOptions {
        anim {
            enter = R.anim.slide_in_from_right
            exit = R.anim.slide_out_to_left
            popEnter = R.anim.slide_in_from_left
            popExit = R.anim.slide_out_to_right
        }
    }

    navController.navigate(
        route = Route.DetailsScreen.route,
        navOptions = customNavOptions
    )
}