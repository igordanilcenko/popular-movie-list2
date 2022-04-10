package com.ihardanilchanka.sampleapp2.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.ihardanilchanka.sampleapp2.resource.AppColor


@Composable
fun GradientDark(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .then(modifier)
            .background(
                Brush.verticalGradient(
                    listOf(Color.Transparent, AppColor.blackTransparent75),
                )
            )
    )
}
