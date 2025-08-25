package com.agwanda.predicts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agwanda.predicts.data.api.OddsEvent
import com.agwanda.predicts.data.api.Outcome
import com.agwanda.predicts.data.repo.Repository
import com.agwanda.predicts.ui.components.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                // Firebase initialization
        val auth = com.google.firebase.auth.FirebaseAuth.getInstance()

        setContent { AgwandaApp() }
    }
}

class MainViewModel : ViewModel() {
    private val repo = Repository(
        oddsApiKey = BuildConfig.ODDS_API_KEY,
        footballApiKey = BuildConfig.FOOTBALL_API_KEY,
        oddsBaseUrl = BuildConfig.ODDS_BASE_URL,
        footballBaseUrl = BuildConfig.FOOTBALL_BASE_URL
    )

    var odds by mutableStateOf<List<OddsEvent>>(emptyList())
        private set

    init {
        refreshOdds()
    }

    fun refreshOdds() = viewModelScope.launch {
        odds = repo.fetchSoccerOdds()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgwandaApp() {
    val vm = remember { MainViewModel() }
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Predictions", "Live", "Results", "My Picks")

    MaterialTheme(colorScheme = darkColorScheme()) {
        Scaffold(
            topBar = { TopAppBar(title = { Text("AGWANDA PREDICTS", fontWeight = FontWeight.ExtraBold) }) }
        ) { padding ->
            Column(Modifier.padding(padding)) {
                ProTabs(titles = tabs, selectedIndex = tabIndex, onSelected = { tabIndex = it })
                when (tabIndex) {
                    0 -> PredictionsScreen(vm)
                    1 -> LiveScreen(vm)
                    2 -> ResultsScreen(vm)
                    3 -> MyPicksScreen()
                }
            }
        }
    }
}

@Composable
fun PredictionsScreen(vm: MainViewModel) {
    val odds = vm.odds
    val samplePredictions = listOf(
        Triple("Benfica vs Porto", "Benfica to Win", 78),
        Triple("Arsenal vs Spurs", "Both Teams to Score", 55),
        Triple("Bayern vs Dortmund", "Over 2.5 Goals", 70)
    )

    LazyColumn(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(samplePredictions) { (match, tip, confidence) ->
            val matchOdds = odds.firstOrNull()?.bookmakers?.firstOrNull()?.markets?.firstOrNull()?.outcomes
            PredictionCard(
                match = match,
                tip = tip,
                confident = confidence >= 60,
                confidencePercent = confidence,
                odds = matchOdds
            )
        }
    }
}

@Composable
fun ResultsScreen(vm: MainViewModel) {
    val odds = vm.odds
    val results = listOf(
        Triple("Benfica 2 - 1 Porto", "Home Win", true),
        Triple("Arsenal 1 - 1 Spurs", "BTTS", true),
        Triple("Bayern 0 - 1 Dortmund", "Over 2.5", false),
    )
    LazyColumn(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(results) { (score, market, won) ->
            val closing = odds.firstOrNull()?.bookmakers?.firstOrNull()?.markets?.firstOrNull()?.outcomes
            ResultCard(score = score, market = market, won = won, closingOdds = closing)
        }
    }
}

@Composable
fun LiveScreen(vm: MainViewModel) {
    // Auto refresh odds every 30s as a demo for live updates
    LaunchedEffect(Unit) {
        while (true) {
            vm.refreshOdds()
            delay(30_000L)
        }
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Live tracker coming soon — auto-refreshing data in the background.")
    }
}

@Composable
fun MyPicksScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Save favorites here (coming soon).")
    }
}
