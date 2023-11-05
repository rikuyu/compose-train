package com.example.feature.catalog.threadscard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature.catalog.R

@Composable
fun CardFrontSide(user: User = User()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        CardBlackHalfCircle(modifier = Modifier.align(alignment = Alignment.TopCenter))
        CardContent(
            date = user.date,
            time = user.time,
            instagram = user.instagram,
            userId = user.userId,
            username = user.username,
            userImage = user.userImage,
            userQrCode = user.userQrCode,
        )
        CardBlackHalfCircle(modifier = Modifier.align(alignment = Alignment.BottomCenter))
    }
}

@Composable
internal fun CardBlackHalfCircle(modifier: Modifier) {
    Canvas(modifier = modifier) {
        drawCircle(
            color = Color.Black,
            radius = 24.dp.toPx(),
        )
    }
}

@Composable
internal fun CardContent(
    date: String,
    time: String,
    instagram: String,
    userId: String,
    username: String,
    userImage: Int,
    userQrCode: Int,
) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TitleText(title = "DATE", info = date)
            CardBrand(modifier = Modifier.align(alignment = Alignment.Bottom))
        }
        Spacer(modifier = Modifier.height(8.dp))
        TitleText(title = "TIME", info = time)
        Spacer(modifier = Modifier.height(16.dp))
        TitleText(title = "USERNAME", info = username)
        Spacer(modifier = Modifier.height(12.dp))
        QrCode(userQrCode = userQrCode, modifier = Modifier.align(alignment = Alignment.Start))
        Spacer(modifier = Modifier.height(12.dp))
        CardDashDivider()
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                UserImage(userImage = userImage)
                Instagram(text = instagram)
            }
            CardUserId(text = userId)
        }
    }
}

@Composable
internal fun TitleText(title: String, info: String) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = title,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
            ),
        )

        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = info,
            color = Color.Black,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
            ),
        )
    }
}

@Composable
internal fun CardBrand(modifier: Modifier) {
    Image(
        modifier = modifier
            .padding(end = 24.dp)
            .size(size = 60.dp),
        painter = painterResource(id = R.drawable.ic_threads_icon),
        contentDescription = "",
    )
}

@Composable
internal fun CardDashDivider() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
    ) {
        drawLine(
            color = Color.DarkGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 14f), 0f),
        )
    }
}

@Composable
internal fun QrCode(userQrCode: Int, modifier: Modifier) {
    Image(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .size(size = 56.dp),
        painter = painterResource(id = userQrCode),
        contentDescription = "",
    )
}

@Composable
internal fun UserImage(userImage: Int) {
    Image(
        modifier = Modifier
            .padding(start = 24.dp)
            .size(size = 40.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = userImage),
        contentDescription = "",
    )
}

@Composable
internal fun Instagram(text: String) {
    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = text,
        color = Color.Black,
        fontWeight = FontWeight.SemiBold,
        style = TextStyle(
            fontSize = 12.sp,
            fontFamily = FontFamily.Default,
            letterSpacing = 0.7.sp,
        ),
    )
}

@Composable
internal fun CardUserId(text: String) {
    Text(
        modifier = Modifier.padding(end = 24.dp),
        text = text,
        color = Color.Black,
        fontWeight = FontWeight.Light,
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = FontFamily.Default,
            letterSpacing = 1.sp,
        ),
    )
}


data class User(
    val username: String = "Rikuyu",
    val instagram: String = "rikuyu_instagram",
    val userId: String = "071030501",
    val date: String = "MON NOV 7",
    val time: String = "04:12 P.M.",
    val userImage: Int = R.drawable.ic_user_avater,
    val userQrCode: Int = R.drawable.ic_qr_code,
)
