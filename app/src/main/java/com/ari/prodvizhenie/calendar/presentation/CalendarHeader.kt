package com.ari.prodvizhenie.calendar.presentation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ari.prodvizhenie.R
import com.ari.prodvizhenie.auth.presentation.login_screen.TokenViewModel
import com.ari.prodvizhenie.calendar.domain.model.PostInfo
import com.ari.prodvizhenie.calendar.presentation.components.ClickableRow
import com.ari.prodvizhenie.calendar.presentation.components.CustomButton
import com.ari.prodvizhenie.calendar.presentation.components.PostsList
import com.ari.prodvizhenie.calendar.presentation.components.TopBar
import com.ari.prodvizhenie.util.components.LoadingDialog
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import getCurrentDay
import getCurrentWeekNumber
import getDatesForCurrentYearGroupedByWeeks
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarHeader(
    onLogout: () -> Unit,
    navigateToDetails: (PostInfo) -> Unit
) {

    val items = getDatesForCurrentYearGroupedByWeeks()

    val tokenViewModel: TokenViewModel = hiltViewModel()

    val accessToken = tokenViewModel.accessToken

    val calendarViewModel: CalendarViewModel = hiltViewModel()
    val postsState by calendarViewModel.postsState.collectAsStateWithLifecycle()

    LoadingDialog(isLoading = postsState.isLoading)

    var selectedDate by rememberSaveable {
        mutableStateOf(getCurrentDay())
    }

    val pagerState = rememberPagerState(initialPage = getCurrentWeekNumber(items) - 2, pageCount = {
        items.size
    })

    var months by remember {
        mutableStateOf("Июнь")
    }

    LaunchedEffect(key1 = selectedDate, key2 = accessToken) {
        if (accessToken.value != "" && accessToken.value != null) {
            Log.d("TAG", "CalendarHeader: ${accessToken.value}")
            calendarViewModel.getPostsByDate(accessToken.value!!, selectedDate)
        }
    }

    val dateDialogState = rememberMaterialDialogState()

    LaunchedEffect(key1 = pagerState.currentPage) {
        val weekNumber = pagerState.currentPage + 1
        val firstDayOfWeek = LocalDate.now().with(TemporalAdjusters.firstDayOfYear())
            .plusWeeks(weekNumber.toLong() - 1)
            .with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
        val lastDayOfWeek =
            firstDayOfWeek.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY))

        val monthMonday = firstDayOfWeek.month.name
        val monthSunday = lastDayOfWeek.month.name

        months = getResultMonthString(monthMonday, monthSunday)

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.main_background))
    ) {
        //TODO добавить передавание username (хардкод)
        TopBar(onLogout = onLogout, title = "Амир Хазеев")
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f),
            userScrollEnabled = true
        ) { page ->
            ClickableRow(dates = items[page], selectedDate = selectedDate, onItemClick = {
                selectedDate = it
            })
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            CustomButton(text = months, onClick = {
                dateDialogState.show()
            })
            Spacer(modifier = Modifier.height(10.dp))
            PostsList(
                postsState,
                navigateToDetails = navigateToDetails
            )
        }


    }
    val scope = rememberCoroutineScope()
    MaterialDialog(dialogState = dateDialogState, buttons = {
        positiveButton(text = "Выбрать")
        negativeButton(text = "Выход")
    }) {
        datepicker(initialDate = selectedDate, title = "Выберите дату", allowedDateValidator = {
            compareWithFirstDayOfYear(it) > 0 && compareWithLastDayOfYear(it) < 0
        }) {
            selectedDate = it
            scope.launch {
                pagerState.animateScrollToPage(getWeekOfYear(it, items, pagerState.currentPage))
            }
        }
    }


}

val monthAbbreviationsMap = mapOf(
    "JANUARY" to "Янв",
    "FEBRUARY" to "Фев",
    "MARCH" to "Мар",
    "APRIL" to "Апр",
    "MAY" to "Май",
    "JUNE" to "Июн",
    "JULY" to "Июл",
    "AUGUST" to "Авг",
    "SEPTEMBER" to "Сен",
    "OCTOBER" to "Окт",
    "NOVEMBER" to "Ноя",
    "DECEMBER" to "Дек"
)

val monthNamesMap = mapOf(
    "JANUARY" to "Январь",
    "FEBRUARY" to "Февраль",
    "MARCH" to "Март",
    "APRIL" to "Апрель",
    "MAY" to "Май",
    "JUNE" to "Июнь",
    "JULY" to "Июль",
    "AUGUST" to "Август",
    "SEPTEMBER" to "Сентябрь",
    "OCTOBER" to "Октябрь",
    "NOVEMBER" to "Ноябрь",
    "DECEMBER" to "Декабрь"
)

fun getResultMonthString(monthMond: String, monthSund: String): String {
    return if (monthMond == monthSund) {
        monthNamesMap[monthMond]!!
    } else {
        monthAbbreviationsMap[monthMond]!! + " - " + monthAbbreviationsMap[monthSund]!!
    }
}

fun compareWithFirstDayOfYear(date: LocalDate): Int {
    val firstDayOfYear = LocalDate.now().withDayOfYear(1)
    return date.compareTo(firstDayOfYear)
}

fun compareWithLastDayOfYear(date: LocalDate): Int {
    val lastDayOfYear = LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear())
    return date.compareTo(lastDayOfYear)
}

fun getWeekOfYear(date: LocalDate, items: List<List<LocalDate>>, defValue: Int): Int {
    for (i in items.indices) {
        if (items[i].contains(date)) {
            return i
        }
    }
    return defValue
}