package com.ari.prodvizhenie.details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ari.prodvizhenie.R
import com.ari.prodvizhenie.auth.presentation.login_screen.TokenViewModel
import com.ari.prodvizhenie.calendar.domain.model.PostInfo
import com.ari.prodvizhenie.calendar.presentation.components.TopBar
import com.ari.prodvizhenie.util.components.LoadingDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val Blue = Color(0xFF228BE6)

@Composable
fun DetailsScreen(
    navigateUp: () -> Unit,
    postInfo: PostInfo,
) {


    val detailsViewModel: DetailsViewModel = hiltViewModel()
    val currState by detailsViewModel.statisticState.collectAsStateWithLifecycle()


    val tokenViewModel: TokenViewModel = hiltViewModel()
    val accessToken = tokenViewModel.accessToken

    LaunchedEffect(key1 = accessToken) {
        if (accessToken.value != "" && accessToken.value != null) {
            detailsViewModel.getStatistic(token = accessToken.value!!, id = postInfo.id)
        }
    }

    Column {
        Scaffold(
            topBar = {
                TopBar(
                    isDetailsScreen = true,
                    onBackClick = navigateUp,
                    title = "Информация"
                )
            },
            containerColor = Blue,
            modifier = Modifier
                .height(180.dp)
                .background(
                    color = Blue,
                    shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                )
                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding()),
                contentAlignment = Alignment.Center,

                ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = unixTimeMillisToDate(postInfo.upload_date),
                        color = colorResource(id = R.color.hint_color)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = postInfo.name,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Текст",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = postInfo.body,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Статистика",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Stats(state = currState)
        }
    }
}


@Composable
fun Stats(
    state: StatisticsViewState
) {

    LoadingDialog(isLoading = state.isLoading)

    if (state.statistics == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Статистика отсутствует",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 40.sp
            )
        }
    } else if (state.error == null) {
        Column {
            //TODO CHANGE
            Text(text = "Количество комментов: ${state.statistics.commentsCount}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Индекс вовлеченности: ${(state.statistics.negativeReactionCount + state.statistics.positiveReactionCount + state.statistics.neutralReactionCount) / state.statistics.membersCount * 100}%")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Количество посмотревших: ${state.statistics.viewCount}/${(state.statistics.viewCount / state.statistics.membersCount * 100)}%")
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Ошибка",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 40.sp
            )
        }
    }

}

fun unixTimeMillisToDate(unixTimeMillis: Long): String {
    val date = Date(unixTimeMillis)
    val sdf = SimpleDateFormat("EEEE d MMMM HH:mm", Locale("ru"))
    return sdf.format(date)
}