package com.ari.prodvizhenie.calendar.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ari.prodvizhenie.R
import com.ari.prodvizhenie.calendar.domain.model.PostInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun PostCard(
    postInfo: PostInfo,
    navigateToPostDetails: (PostInfo) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(16.dp))
            .background(color = Color.White)
            .height(114.dp)
            .padding(
                start = 17.dp,
                top = 14.dp,
                end = 10.dp,
                bottom = 21.dp
            )

            .clickable(
                onClick = { navigateToPostDetails(postInfo) },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.telegram),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(17.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = unixTimeMillisToDate(postInfo.upload_date),
                    color = colorResource(id = R.color.post_text),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = postInfo.name,
                    fontSize = 25.sp,
                    color = Color.Black
                )

            }
        }
    }

}

fun unixTimeMillisToDate(unixTimeMillis: Long): String {
    val date = Date(unixTimeMillis)
    val sdf = SimpleDateFormat("HH:mm", Locale("ru"))
    return sdf.format(date)
}