package mx.familiasquesuman.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mx.familiasquesuman.app.ui.nav.AppNavHost
import mx.familiasquesuman.app.ui.theme.FamiliasQueSumanTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

            val esPantallaAncha =
                windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact

            FamiliasQueSumanApp(esPantallaAncha)
        }
    }
}

@Composable
fun FamiliasQueSumanApp(esPantallaAncha: Boolean) {
    FamiliasQueSumanTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppNavHost(esPantallaAncha = esPantallaAncha)
        }
    }
}
