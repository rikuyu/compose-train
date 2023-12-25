package com.example.feature.museum.share

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.core.app.ShareCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException

fun sharePlainText(context: Context, text: String) {
    val intent = ShareCompat.IntentBuilder(context)
        .setType("text/plain")
        .setText(text)
        .createChooserIntent()
    context.startActivity(intent)
}

fun receiveText(context: Context, intent: Intent) {
    val intentReader = ShareCompat.IntentReader(context, intent)
    if (intentReader.isShareIntent) {
        val text = intentReader.text.toString()
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}

fun shareJpegImage(context: Context, uri: Uri) {
    val intent = ShareCompat.IntentBuilder(context)
        .setType("image/jpeg")
        .setStream(uri)
        .setText("sample image")
        .createChooserIntent()
    context.startActivity(intent)
}

fun shareMultipleImages(context: Context, uris: List<Uri>) {
    val intent = ShareCompat.IntentBuilder(context)
        .setType("image/*")
        .apply {
            for (uri in uris) {
                addStream(uri)
            }
        }
        .createChooserIntent()
    context.startActivity(intent)
}

@Composable
fun receiveJpegImage(context: Context, uri: Uri): Painter {
    val state = produceState<Painter>(
        initialValue = ColorPainter(Color.Blue),
        producer = {
            withContext(Dispatchers.IO) {
                try {
                    context.contentResolver.openInputStream(uri).use {
                        val bitmap = BitmapFactory.decodeStream(it)
                        value = BitmapPainter(image = bitmap.asImageBitmap())
                    }
                } catch (e: FileNotFoundException) {
                    value = ColorPainter(Color.Red)
                }
            }
        },
    )
    return state.value
}
