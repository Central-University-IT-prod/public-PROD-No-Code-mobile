package com.ari.prodvizhenie.nav_graph

sealed class Route(
    val route: String
) {
    data object LoginScreen : Route(route = "loginScreen")
    data object CalendarScreen : Route(route = "calendarScreen")
    data object AppStartNavigation : Route(route = "appStartNavigation")
    data object AppNavigation : Route(route = "appNavigation")
    data object DetailsScreen : Route(route = "detailsScreen")
}