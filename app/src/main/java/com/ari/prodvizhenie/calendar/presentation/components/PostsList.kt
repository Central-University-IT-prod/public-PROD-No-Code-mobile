package com.ari.prodvizhenie.calendar.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ari.prodvizhenie.auth.presentation.login_screen.PostViewState
import com.ari.prodvizhenie.calendar.domain.model.PostInfo
import com.ari.prodvizhenie.util.components.LoadingDialog

@Composable
fun PostsList(
    state: PostViewState,
    navigateToDetails: (PostInfo) -> Unit
) {

    LoadingDialog(isLoading = state.isLoading)

    if (state.posts.isEmpty() && state.error == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Посты отсутствуют",
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 40.sp
            )
        }
    } else if (state.posts.isNotEmpty() && state.error == null) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(state.posts.size) { ind ->
                PostCard(
                    postInfo = state.posts[ind],
                    navigateToPostDetails = {
                        navigateToDetails(state.posts[ind])
                    }
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Ошибка",
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 40.sp
            )
        }
    }

}