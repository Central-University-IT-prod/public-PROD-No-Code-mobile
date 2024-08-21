package com.ari.prodvizhenie.calendar.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ari.prodvizhenie.R
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun ClickableRow(
    dates: List<LocalDate>,
    selectedDate: LocalDate,
    onItemClick: (LocalDate) -> Unit
) {

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(dates.size) { ind ->
            ClickableDate(
                content = dates[ind],
                isSelected = selectedDate == dates[ind],
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun ClickableDate(
    content: LocalDate,
    onItemClick: (LocalDate) -> Unit,
    isSelected: Boolean
) {

    val textColor = if (isSelected) Color.White else Color.Black
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clickable(
                onClick = { onItemClick(content) },
                interactionSource = interactionSource,
                indication = null
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mapDayOfWeekToRussianAbbreviation(content.dayOfWeek),
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(6.dp))
        val backgroundColor =
            if (isSelected) colorResource(id = R.color.circle_background) else Color.Transparent
        Box(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = CircleShape
                )
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = content.dayOfMonth.toString(),
                color = textColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}


fun mapDayOfWeekToRussianAbbreviation(dayOfWeek: DayOfWeek): String {
    return when (dayOfWeek) {
        DayOfWeek.MONDAY -> "Пн"
        DayOfWeek.TUESDAY -> "Вт"
        DayOfWeek.WEDNESDAY -> "Ср"
        DayOfWeek.THURSDAY -> "Чт"
        DayOfWeek.FRIDAY -> "Пт"
        DayOfWeek.SATURDAY -> "Сб"
        DayOfWeek.SUNDAY -> "Вс"
    }
}