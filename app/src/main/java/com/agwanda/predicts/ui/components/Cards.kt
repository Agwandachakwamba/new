package com.agwanda.predicts.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agwanda.predicts.data.api.Outcome

@Composable
fun PredictionCard(
    match: String,
    tip: String,
    confident: Boolean,
    confidencePercent: Int,
    odds: List<Outcome>? // expecting H2H outcomes (Home, Draw, Away)
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(match, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Tip: " + tip, style = MaterialTheme.typography.bodyMedium)
                }
                if (confident) {
                    Icon(Icons.Default.ThumbUp, contentDescription = "Confident", tint = MaterialTheme.colorScheme.primary)
                } else {
                    Icon(Icons.Default.ThumbDown, contentDescription = "Low Confidence", tint = MaterialTheme.colorScheme.error)
                }
            }
            ConfidenceBar(confidencePercent, Modifier.fillMaxWidth())
            odds?.let {
                OddsRow(it)
            }
        }
    }
}

@Composable
fun OddsRow(outcomes: List<Outcome>) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        outcomes.take(3).forEach { o ->
            Text("${o.name ?: ""}: ${o.price ?: 0.0}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun ResultCard(
    score: String,
    market: String,
    won: Boolean,
    closingOdds: List<Outcome>?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(score, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(market, style = MaterialTheme.typography.bodyMedium)
                }
                if (won) {
                    Icon(Icons.Default.ThumbUp, contentDescription = "Win", tint = MaterialTheme.colorScheme.primary)
                } else {
                    Icon(Icons.Default.ThumbDown, contentDescription = "Loss", tint = MaterialTheme.colorScheme.error)
                }
            }
            closingOdds?.let { OddsRow(it) }
        }
    }
}
