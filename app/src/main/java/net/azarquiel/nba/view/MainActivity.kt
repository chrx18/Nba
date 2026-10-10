package net.azarquiel.nba.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import net.azarquiel.nba.screens.MainScreen
import net.azarquiel.nba.ui.theme.NbaTheme
import net.azarquiel.nba.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = remember { MainViewModel((this@MainActivity)) }
            NbaTheme {
                MainScreen(mainViewModel)
            }
        }
    }
}