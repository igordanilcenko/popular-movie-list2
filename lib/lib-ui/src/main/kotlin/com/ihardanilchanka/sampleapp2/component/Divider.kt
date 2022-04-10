package com.ihardanilchanka.sampleapp2.component

import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ihardanilchanka.sampleapp2.resource.AppColor

@Composable
fun DividerDark(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        color = AppColor.lightGrey200,
        thickness = 1.dp,
    )
}