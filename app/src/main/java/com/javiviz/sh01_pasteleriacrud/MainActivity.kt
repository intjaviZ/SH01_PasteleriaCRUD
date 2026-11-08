package com.javiviz.sh01_pasteleriacrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.javiviz.sh01_pasteleriacrud.features.main.presentation.navigation.AppNavHost
import com.javiviz.sh01_pasteleriacrud.ui.theme.SH01_PasteleriaCRUDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SH01_PasteleriaCRUDTheme {
                AppNavHost()
            }
        }
    }
}