package com.agwanda.predicts.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProTabs(
    titles: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 12.dp,
        containerColor = MaterialTheme.colorScheme.surface,
        indicator = { /* pill handled per tab */ },
        divider = {}
    ) {
        titles.forEachIndexed { index, title ->
            val selected = index == selectedIndex
            val bg by animateColorAsState(
                if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                else MaterialTheme.colorScheme.surface
            )
            val textColor by animateColorAsState(
                if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Tab(
                selected = selected,
                onClick = { onSelected(index) },
                text = {
                    Text(
                        title,
                        color = textColor,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 6.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(bg)
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            )
        }
    }
}
