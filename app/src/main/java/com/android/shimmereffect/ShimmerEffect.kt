package com.android.shimmereffect

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerEffect(isLoading: Boolean, contentAfterLoading: @Composable () -> Unit, modifier: Modifier = Modifier) {
    if (isLoading) {
        Row(modifier = modifier) {
            Box(modifier = Modifier.size(100.dp).clip(CircleShape).shimmerEffect())

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(text = "", modifier = Modifier.fillMaxSize().height(height = 20.dp).shimmerEffect())

              Spacer(modifier = Modifier.width(16.dp))

              Box(modifier = Modifier.fillMaxWidth(fraction = 0.7f).height(height = 20.dp).shimmerEffect())
            }
        }
    }
    else contentAfterLoading()
}

fun Modifier.shimmerEffect() : Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "Shimmer Effect")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 1000)), label = ""
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(Color(color = 0xFFB8B5B5), Color(color = 0xFF8F8B8B), Color(color = 0xFFB8B5B5)),
            start = Offset.Zero,
            end = Offset(x = startOffsetX, y = startOffsetX)
        )
    ).onGloballyPositioned {
        size  = it.size
    }
}