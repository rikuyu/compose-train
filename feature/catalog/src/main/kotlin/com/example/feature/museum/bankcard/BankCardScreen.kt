package com.example.feature.museum.bankcard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature.museum.R
import theme.M3TrainAppTheme

@Composable
internal fun BankCardScreen(
    modifier: Modifier = Modifier,
) {
    BankCard(
        baseColor = Color(0xFF1252c8),
        cardNumber = "1234567812345678",
        cardHolder = "YUKI KOBAYASHI",
        expires = "06/27",
        cvv = "123",
        brand = "VISA",
        modifier = modifier,
    )
}

@Composable
fun BankCard(
    baseColor: Color,
    cardNumber: String,
    cardHolder: String,
    expires: String,
    cvv: String,
    brand: String,
    modifier: Modifier = Modifier,
    bankCardAspectRatio: Float = 1.6f,
) {
    var flipped by remember { mutableStateOf(false) }
    val rotationZ by animateFloatAsState(targetValue = if (flipped) 180f else 0f, label = "")

    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(bankCardAspectRatio)
            .graphicsLayer {
                this.rotationX = rotationZ
                cameraDistance = 12f * density
                shadowElevation = if (flipped) 0f else 30f
                alpha = if (flipped) 0.3f else 0.8f
            }
            .clickable { flipped = !flipped },
        shape = RoundedCornerShape(12.dp),
    ) {
        Box {
            BankCardBackground(baseColor = baseColor)
            BankCardNumber(cardNumber = cardNumber)
            SpaceWrapper(
                modifier = Modifier.align(Alignment.TopStart),
                space = 32.dp,
                top = true,
                left = true,
            ) {
                BankCardLabelAndText(label = "card holder", text = cardHolder)
            }
            SpaceWrapper(
                modifier = Modifier.align(Alignment.BottomStart),
                space = 32.dp,
                bottom = true,
                left = true,
            ) {
                Row {
                    BankCardLabelAndText(label = "expires", text = expires)
                    Spacer(modifier = Modifier.width(16.dp))
                    BankCardLabelAndText(label = "cvv", text = cvv)
                }
            }
            SpaceWrapper(
                modifier = Modifier.align(Alignment.BottomEnd),
                space = 32.dp,
                bottom = true,
                right = true,
            ) {
                Text(
                    text = brand,
                    style = TextStyle(
                        fontFamily = LatoFont,
                        fontWeight = FontWeight.W500,
                        fontStyle = FontStyle.Italic,
                        fontSize = 22.sp,
                        letterSpacing = 1.sp,
                        color = Color.White,
                    ),
                )
            }
        }
    }
}

@Composable
fun BankCardLabelAndText(label: String, text: String) {
    Column(
        modifier = Modifier
            .wrapContentSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label.uppercase(),
            style = TextStyle(
                fontFamily = LatoFont,
                fontWeight = FontWeight.W300,
                fontSize = 12.sp,
                letterSpacing = 1.sp,
                color = Color.White,
            ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            style = TextStyle(
                fontFamily = LatoFont,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                letterSpacing = 1.sp,
                color = Color.White,
            ),
        )
    }
}

@Composable
fun SpaceWrapper(
    modifier: Modifier = Modifier,
    space: Dp,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false,
    left: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .then(if (top) Modifier.padding(top = space) else Modifier)
            .then(if (right) Modifier.padding(end = space) else Modifier)
            .then(if (bottom) Modifier.padding(bottom = space) else Modifier)
            .then(if (left) Modifier.padding(start = space) else Modifier),
    ) {
        content()
    }
}

@Composable
fun BankCardBackground(baseColor: Color) {
    val colorSaturation75 = baseColor.setSaturation(0.75f)
    val colorSaturation50 = baseColor.setSaturation(0.5f)
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(baseColor),
    ) {
        drawCircle(
            color = colorSaturation75,
            center = Offset(x = size.width * 0.2f, y = size.height * 0.6f),
            radius = size.minDimension * 0.85f,
        )
        drawCircle(
            color = colorSaturation50,
            center = Offset(x = size.width * 0.1f, y = size.height * 0.3f),
            radius = size.minDimension * 0.75f,
        )
    }
}

@Composable
fun BankCardNumber(cardNumber: String) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(3) {
            BankCardDotGroup()
        }

        Text(
            text = cardNumber.takeLast(4),
            style = TextStyle(
                fontFamily = LatoFont,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = 1.sp,
                color = Color.White,
            ),
        )
    }
}

@Composable
fun BankCardDotGroup() {
    Canvas(
        modifier = Modifier.width(48.dp),
        onDraw = {
            val dotRadius = 4.dp.toPx()
            val spaceBetweenDots = 8.dp.toPx()
            for (i in 0 until 4) {
                drawCircle(
                    color = Color.White,
                    radius = dotRadius,
                    center = Offset(
                        x = i * (dotRadius * 2 + spaceBetweenDots) + dotRadius,
                        y = center.y,
                    ),
                )
            }
        },
    )
}


fun Color.toHsl(): FloatArray {
    val redComponent = red
    val greenComponent = green
    val blueComponent = blue

    val maxComponent = maxOf(redComponent, greenComponent, blueComponent)
    val minComponent = minOf(redComponent, greenComponent, blueComponent)
    val delta = maxComponent - minComponent
    val lightness = (maxComponent + minComponent) / 2

    val hue: Float
    val saturation: Float

    if (maxComponent == minComponent) {
        // Grayscale color, no saturation and hue is undefined
        hue = 0f
        saturation = 0f
    } else {
        // Calculating saturation
        saturation = if (lightness > 0.5) delta / (2 - maxComponent - minComponent) else delta / (maxComponent + minComponent)
        // Calculating hue
        hue = when (maxComponent) {
            redComponent -> 60 * ((greenComponent - blueComponent) / delta % 6)
            greenComponent -> 60 * ((blueComponent - redComponent) / delta + 2)
            else -> 60 * ((redComponent - greenComponent) / delta + 4)
        }
    }

    // Returning HSL values, ensuring hue is within 0-360 range
    return floatArrayOf(hue.coerceIn(0f, 360f), saturation, lightness)
}

fun hslToColor(hue: Float, saturation: Float, lightness: Float): Color {
    val chroma = (1 - kotlin.math.abs(2 * lightness - 1)) * saturation
    val secondaryColorComponent = chroma * (1 - kotlin.math.abs((hue / 60) % 2 - 1))
    val matchValue = lightness - chroma / 2

    var red = matchValue
    var green = matchValue
    var blue = matchValue

    when ((hue.toInt() / 60) % 6) {
        0 -> {
            red += chroma
            green += secondaryColorComponent
        }

        1 -> {
            red += secondaryColorComponent
            green += chroma
        }

        2 -> {
            green += chroma
            blue += secondaryColorComponent
        }

        3 -> {
            green += secondaryColorComponent
            blue += chroma
        }

        4 -> {
            red += secondaryColorComponent
            blue += chroma
        }

        5 -> {
            red += chroma
            blue += secondaryColorComponent
        }
    }

    // Creating a color from RGB components
    return Color(red = red, green = green, blue = blue)
}

fun Color.setSaturation(newSaturation: Float): Color {
    val hslValues = this.toHsl()
    // Adjusting the saturation while keeping hue and lightness the same
    return hslToColor(hslValues[0], newSaturation.coerceIn(0f, 1f), hslValues[2])
}

val LatoFont = FontFamily(
    Font(R.font.lato_black, FontWeight.Black),
    Font(R.font.lato_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.lato_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.lato_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_thin, FontWeight.Thin),
    Font(R.font.lato_thin_italic, FontWeight.Thin, FontStyle.Italic),
)


@Preview
@Composable
private fun PreviewBankCard() {
    M3TrainAppTheme {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp),
        ) {
            BankCard(
                baseColor = Color(0xFF1252c8),
                cardNumber = "1234567812345678",
                cardHolder = "YUKI KOBAYASHI",
                expires = "06/27",
                cvv = "123",
                brand = "VISA",
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}
