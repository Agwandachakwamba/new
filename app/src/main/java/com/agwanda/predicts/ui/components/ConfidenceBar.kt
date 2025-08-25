package com.agwanda.predicts.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas

@Composable
fun ConfidenceBar(percent: Int, modifier: Modifier = Modifier) {
    val p = percent.coerceIn(0, 100)
    Canvas(modifier = modifier.fillMaxWidth().height(8.dp)) {
        val w = size.width
        val h = size.height
        // background
        drawRoundRect(
            color = MaterialTheme.colorScheme.surfaceVariant,
            size = size,
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f, 12f)
        )
        // foreground
        drawRoundRect(
            color = if (p >= 60) Color(0xFF2E7D32) else if (p >= 40) MaterialTheme.colorScheme.primary else Color(0xFFC62828),
            size = androidx.compose.ui.geometry.Size(w * (p / 100f), h),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f, 12f)
        )
    }
}
