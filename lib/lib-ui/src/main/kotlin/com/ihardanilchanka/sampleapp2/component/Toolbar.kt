@file:OptIn(ExperimentalMaterial3Api::class)

package com.ihardanilchanka.sampleapp2.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


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
    Box(Modifier.shadow(4.dp)) {
        TopAppBar(
            title = { title?.let { Text(text = title) } },
            navigationIcon = { NavigationButton(navigateUp) },
        )
    }
}

@Composable
private fun NavigationButton(navigateUp: (() -> Unit)?) {
    if (navigateUp != null) {
        IconButton(onClick = { navigateUp() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Arrow back",
            )
        }
    }
}
