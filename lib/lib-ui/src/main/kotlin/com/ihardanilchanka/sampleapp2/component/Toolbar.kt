package com.ihardanilchanka.sampleapp2.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


@Composable
fun Toolbar(
    titleResId: Int?,
    navigateUp: (() -> Unit)? = null,
) {
    Toolbar(titleResId?.let { stringResource(it) }, navigateUp)
}

@Composable
fun Toolbar(
    title: String?,
    navigateUp: (() -> Unit)? = null,
) {
    TopAppBar(
        title = { title?.let { Text(text = title) } },
        navigationIcon = { NavigationButton(navigateUp) },
    )
}

@Composable
private fun NavigationButton(navigateUp: (() -> Unit)?) {
    if (navigateUp != null) {
        IconButton(onClick = { navigateUp() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Arrow back"
            )
        }
    }
}
