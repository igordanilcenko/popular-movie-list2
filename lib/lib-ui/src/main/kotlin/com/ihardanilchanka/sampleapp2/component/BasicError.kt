package com.ihardanilchanka.sampleapp2.component

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleapp2.lib.ui.R
import retrofit2.HttpException
import java.net.UnknownHostException

@Composable
fun BasicError(
    modifier: Modifier = Modifier,
    error: Throwable,
    onReloadClicked: () -> Unit,
) {
    BasicError(
        modifier = modifier,
        title = error.getErrorDialogTitle(LocalContext.current),
        message = error.getErrorDialogMessage(LocalContext.current),
        onReloadClicked = onReloadClicked
    )
}

@Composable
fun BasicError(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    onReloadClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onReloadClicked) {
            Text(stringResource(R.string.state_view_reload))
        }
    }
}

private fun Throwable.getErrorDialogTitle(context: Context): String {
    return when {
        this is HttpException && this.code() == 401 ->
            context.getString(R.string.state_view_error_401_title)
        this is HttpException && this.code() == 404 ->
            context.getString(R.string.state_view_error_404_title)
        this is UnknownHostException ->
            context.getString(R.string.state_view_error_no_internet_title)
        else -> context.getString(R.string.state_view_error_default_title)
    }
}

private fun Throwable.getErrorDialogMessage(context: Context): String {
    return when {
        this is HttpException && this.code() == 401 -> ""
        this is HttpException && this.code() == 404 -> ""
        this is UnknownHostException ->
            context.getString(R.string.state_view_error_no_internet_message)
        else -> context.getString(R.string.state_view_error_default_message)
    }
}
