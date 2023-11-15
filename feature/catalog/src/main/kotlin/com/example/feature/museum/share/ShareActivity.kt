package com.example.feature.museum.share

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import debounceClickable
import theme.M3TrainAppTheme

class ShareActivity : ComponentActivity() {

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, ShareActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M3TrainAppTheme {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    shareItem("plain/text") { sharePlainText(context = this@ShareActivity, text = "Hello World") }
                    //shareItem("image/jpeg") { shareJpegImage(context = this@ShareActivity, uri = "") }
                }
            }
        }

        receiveText(this@ShareActivity, intent)
    }
}

fun LazyListScope.shareItem(
    label: String,
    onClick: () -> Unit,
) {
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.background)
                .debounceClickable { onClick() },
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = label,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        Divider(color = MaterialTheme.colorScheme.onBackground)
    }
}
